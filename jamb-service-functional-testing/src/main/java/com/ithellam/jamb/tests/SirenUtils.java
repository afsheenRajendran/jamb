package com.ithellam.jamb.tests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListenableFuture;
import com.ithellam.jamb.rest.IResult;

public class SirenUtils {
    public static final Logger logger = LoggerFactory.getLogger(SirenUtils.class);
    private static final int DEFAULT_WAIT_TIME_IN_MINUTES = 3;

    public static <T> T getSirenDto(final ListenableFuture<IResult<T>> listenableFuture, final int minutes)
    throws Exception {
        return retrieveSirenDto(listenableFuture, minutes);
    }

    public static <T> T getSirenDto(final ListenableFuture<IResult<T>> listenableFuture) throws Exception {
        return retrieveSirenDto(listenableFuture, DEFAULT_WAIT_TIME_IN_MINUTES);
    }

    private static <T> T retrieveSirenDto(final ListenableFuture<IResult<T>> listenableFuture, final int minutes)
    throws Exception {
        IResult<T> result = null;

        try {
            result = listenableFuture.get(minutes, TimeUnit.MINUTES);
        } catch (final InterruptedException | ExecutionException e) {
            logger.error("Encountered exception while trying to get siren dto: {}", e);
        }

        logger.debug("gds 11");

        if (result == null) {
            logger.info("Attempt to get dto failed");
            throw new RuntimeException("Result was null. Entity will not be returned");

        } else if (result.getResponse() == null) {
            logger.info("Attempt to get dto failed");
            throw new RuntimeException("Response was null. Entity will not be returned");

        } else if (result.getResponse().getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            logger.info("Attempt to get dto failed. status: {} code: {} phrase: {} family: {}",
            result.getResponse().getStatusInfo(),
            result.getResponse().getStatusInfo().getStatusCode(),
            result.getResponse().getStatusInfo().getReasonPhrase(),
            result.getResponse().getStatusInfo().getFamily());
            throw new RuntimeException("Response was not success. Entity will not be returned");
        }

        logger.debug("gds 44");

        logger.debug("Got siren dto with class: {}", result.getEntity().getClass().getSimpleName());

        return result.getEntity();
    }
}
