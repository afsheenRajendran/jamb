package com.ithellam.jamb.rest.client;

import static com.ithellam.jamb.rest.PropertyNameUtils.MAIN_SERVICE_URLS_PROPERTY_NAME;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.ClientRequestFilter;

import org.apache.http.client.HttpClient;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.ithellam.common.utils.IConfigProvider;
import com.ithellam.common.utils.ServiceClientExecutorService;
import com.ithellam.jamb.api.IContainerResource;
import com.ithellam.jamb.api.IProductResource;
import com.ithellam.jamb.rest.ServiceClient;

@Singleton
public class MainServiceClient extends ServiceClient implements IMainServiceClient {

    private final ListeningExecutorService executorService;
    private final IConfigProvider configProvider;

    @Inject
    public MainServiceClient(final HttpClient httpClient, final IConfigProvider configProvider,
    final @ServiceClientExecutorService ListeningExecutorService executorService) {
        super(httpClient, configProvider);
        this.executorService = executorService;
        this.configProvider = configProvider;
    }

    @Override
    protected ClientRequestFilter getAuthRequestFilter() {
        return null;
    }

    @Override
    protected String getServiceUrlsPropertyName() {
        return MAIN_SERVICE_URLS_PROPERTY_NAME;
    }

    @Override
    public IContainerClientResource getContainerResource() {
        return new ContainerClientResource(createProxy(IContainerResource.class), executorService);
    }

    @Override
    public IProductClientResource getProductResource() {
        return new ProductClientResource(createProxy(IProductResource.class), executorService);
    }
}
