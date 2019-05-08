package com.ithellam.jamb.api;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ithellam.common.siren.ISirenResource;
import com.ithellam.jamb.api.dto.ContainerMutationDto;

@Path("containers")
@Produces(ISirenResource.SIREN_MEDIA_TYPE)
@Consumes(MediaType.APPLICATION_JSON)
public interface IContainerResource {
    @GET
    Response getContainers();

    @POST
    Response createContainer(final ContainerMutationDto mutation);

    @GET
    @Path("/{name}")
    Response getContainer(@PathParam("name") final String name);

    @GET
    @Path("{id}")
    Response getContainer(@PathParam("id") final UUID containerId);
}
