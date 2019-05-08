package com.ithellam.jamb.rest;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.ws.rs.core.Response;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

/**
 * Base class for all client side proxy implementations that provide the means to call service resources.
 */
public abstract class ClientResource {
    protected ListeningExecutorService getExecutorService() {
        return executorService;
    }

    private final ListeningExecutorService executorService;

    public ClientResource(final ListeningExecutorService executorService) {
        this.executorService = executorService;
    }

    protected <T> ListenableFuture<IResult<T>> execute(final Supplier<Response> sendRequest, final Function<Response, T> resultExtractor) {
        return executorService.submit(() -> {
            Response resp = null;
            try {
                resp = sendRequest.get();
                if (resp.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                    return new Result<>(resultExtractor.apply(resp), resp);
                } else {
                    return new Result<>(resp);
                }
            } finally {
                if (resp != null) {
                    resp.close();
                }
            }
        });
    }

    protected <T> ListenableFuture<IResult<T>> execute(final Supplier<Response> sendRequest, final Class<T> clazz) {
        return execute(sendRequest, resp -> {
            if (resp.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                return resp.readEntity(clazz);
            } else {
                return null;
            }
        });
    }

    protected ListenableFuture<Response> execute(final Supplier<Response> sendRequest) {
        return executorService.submit(() -> {
            return sendRequest.get();
        });
    }
}
