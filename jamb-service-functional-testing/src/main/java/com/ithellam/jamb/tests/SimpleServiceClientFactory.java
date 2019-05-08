package com.ithellam.jamb.tests;

import java.util.concurrent.Executors;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ithellam.common.utils.IConfigProvider;
import com.ithellam.jamb.rest.client.IMainServiceClient;
import com.ithellam.jamb.rest.client.MainServiceClient;


public class SimpleServiceClientFactory {
    private final IConfigProvider configProvider;

    private final ListeningExecutorService executorService;

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    public SimpleServiceClientFactory() {
        configProvider = new SimpleConfigProvider();

        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    public IMainServiceClient getMainServiceClient() {
        final MainServiceClient client = new MainServiceClient(httpClient, configProvider, executorService);
        client.initialize();
        return client;
    }
}
