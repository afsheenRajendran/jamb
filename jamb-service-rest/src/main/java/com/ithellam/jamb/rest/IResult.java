package com.ithellam.jamb.rest;

import javax.ws.rs.core.Response;

public interface IResult<T> {
    Response getResponse();

    T getEntity();
}

