package com.ithellam.jamb.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.client.ClientRequestFilter;

import org.apache.http.client.HttpClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ithellam.common.exceptions.MissingConfigurationException;
import com.ithellam.common.utils.IConfigProvider;

public abstract class ServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);

    private ResteasyWebTarget webTarget;
    private final HttpClient httpClient;
    private final IConfigProvider configProvider;

    public ServiceClient(final HttpClient httpClient, final IConfigProvider configProvider) {
        this.httpClient = httpClient;
        this.configProvider = configProvider;
    }

    @PostConstruct
    public void initialize() {
        final ResteasyClientBuilder builder = new ResteasyClientBuilder().httpEngine(new ApacheHttpClient4Engine(httpClient));
        final ClientRequestFilter authFilter = getAuthRequestFilter();
        if (authFilter != null) {
            builder.register(authFilter);
        }
        final ResteasyClient resteasyClient = builder.build();

        final String urlsPropertyName = getServiceUrlsPropertyName();

        logger.debug("Found urlsPropertyName: {}", urlsPropertyName);

        final List<String> urls = new ArrayList<>(configProvider.getSplitStringValues(urlsPropertyName));

        logger.debug("Found urlsPropertyName: {} urls size {}", urlsPropertyName, urls.size());

        if (urls.size() == 0) {
            throw new MissingConfigurationException(
            "No service URLs have been configured. At least 1 must be specified via an environment or system property: " + urlsPropertyName);
        } else if (urls.size() == 1) {
            webTarget = resteasyClient.target(urls.get(0));
        } else {
            webTarget = resteasyClient.target(urls.get(new Random().nextInt() % urls.size()));
        }
    }


    @PreDestroy
    public void close() {
        if (webTarget != null && webTarget.getResteasyClient() != null) {
            webTarget.getResteasyClient().close();
        }
    }

    public <T extends ClientResource> T getClientResource(final Class<T> clazz, final Supplier<T> clientResourceSupplier) {
        return clientResourceSupplier.get();
    }

    /**
     * @return The property name to get the list of URLs (comma separated) via the config provider.
     */
    protected abstract String getServiceUrlsPropertyName();

    /**
     * @return The client request filter that will add authorization related info to the HTTP requests. Return null if no authentication is required.
     */
    protected abstract ClientRequestFilter getAuthRequestFilter();

    protected <T> T createProxy(final Class<T> serviceResourceClass) {
        return webTarget.proxy(serviceResourceClass);
    }

}
