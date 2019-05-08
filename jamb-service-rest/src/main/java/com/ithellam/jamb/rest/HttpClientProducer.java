package com.ithellam.jamb.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Produces a thread-safe http client. See {@link HttpClientBuilder} for available system properties.
 */
public class HttpClientProducer {

    @Produces
    @ApplicationScoped
    public HttpClient getHttpClient() {
        return HttpClientBuilder.create().useSystemProperties().build();
    }
}
