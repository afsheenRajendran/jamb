package com.ithellam.jamb.rest;

import javax.ws.rs.core.Response;

public class Result<T> implements IResult<T> {
    private final T entity;
    private final Response response;

    public Result(final Response response) {
        this(null, response);
    }

    public Result(final T entity, final Response response) {
        this.entity = entity;
        this.response = response;
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public T getEntity() {
        return entity;
    }

}
