package com.ithellam.jamb.api;

import java.net.URI;
import java.security.Principal;
import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class with helper methods for JAX-RS resources
 */
public abstract class AbstractResource {
    private final Logger logger;

    @Context
    private UriInfo uriInfo;
    @Context
    private SecurityContext securityContext;
    @Context
    private HttpServletRequest request;
    private String principalUserName;

    protected AbstractResource() {
        logger = LoggerFactory.getLogger(getClass());
    }

    protected AbstractResource(final Logger logger) {
        this.logger = logger;
    }

    protected Logger getLogger() {
        return logger;
    }

    protected SecurityContext getSecurityContext() {
        return securityContext == null ? new DummySecurityContext() : securityContext;
    }

    protected HttpServletRequest getHttpServletRequest() {
        return request;
    }

    protected String getPrincipalUserName() {
        return principalUserName;
    }

    protected UriBuilder getUriBuilder() {
        return UriBuilder.fromPath(getHttpServletRequest().getContextPath());
    }

    @PostConstruct
    protected void initialize() {
        if (securityContext != null) {
            principalUserName = securityContext.getUserPrincipal() != null ? securityContext.getUserPrincipal().getName() : null;
            logger.trace("Security context ({}): authScheme={}, userPrincipal={} (class={}), userPrincipalName={}",
            securityContext.getClass().getName(),
            securityContext.getAuthenticationScheme(),
            securityContext.getUserPrincipal(),
            securityContext.getUserPrincipal() == null ? "n/a" : securityContext.getUserPrincipal().getClass().getName(),
            principalUserName);
        }

        if (logger != null && logger.isTraceEnabled()) {
            if (request != null) {
                final StringBuilder output = new StringBuilder();
                output.append("\n Request details: \n")
                .append("  authType           = ").append(request.getAuthType()).append("\n")
                .append("  contentType        = ").append(request.getContentType()).append("\n")
                .append("  contentLength      = ").append(request.getContentLength()).append("\n")
                .append("  contextPath        = ").append(request.getContextPath()).append("\n")
                .append("  method             = ").append(request.getMethod()).append("\n")
                .append("  localAddr          = ").append(request.getLocalAddr()).append("\n")
                .append("  localName          = ").append(request.getLocalName()).append("\n")
                .append("  localPort          = ").append(request.getLocalPort()).append("\n")
                .append("  protocol           = ").append(request.getProtocol()).append("\n")
                .append("  remoteAddr         = ").append(request.getRemoteAddr()).append("\n")
                .append("  remoteHost         = ").append(request.getRemoteHost()).append("\n")
                .append("  remotePort         = ").append(request.getRemotePort()).append("\n")
                .append("  remoteUser         = ").append(request.getRemoteUser()).append("\n")
                .append("  requestedSessionId = ").append(request.getRequestedSessionId()).append("\n")
                .append("  requestUri         = ").append(request.getRequestURI()).append("\n")
                .append("  scheme             = ").append(request.getScheme()).append("\n")
                .append("  serverName         = ").append(request.getServerName()).append("\n")
                .append("  serverPort         = ").append(request.getServerPort()).append("\n")
                .append("  sessionId          = ").append(request.getSession(false) == null ? "n/a" : request.getSession(false).getId())
                .append("\n");
                output.append(" Request headers: \n");
                final Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    final String headerName = headerNames.nextElement();
                    final Enumeration<String> headerValues = request.getHeaders(headerName);
                    output.append("  ").append(headerName).append(" = ");
                    while (headerValues.hasMoreElements()) {
                        output.append(headerValues.nextElement());
                        if (headerValues.hasMoreElements()) {
                            output.append(", ");
                        }
                    }
                    output.append("\n");
                }
                if (request.getCookies() != null) {
                    output.append(" Request cookies: \n");
                    for (final Cookie cookie : request.getCookies()) {
                        output.append("  ").append(cookie.getName()).append(" = ").append(cookie.getValue())
                        .append("; path=").append(cookie.getPath())
                        .append(cookie.getSecure() ? "; secure" : "")
                        .append(cookie.isHttpOnly() ? "; httpOnly" : "")
                        .append(" (domain=").append(cookie.getDomain())
                        .append(", maxAge=").append(cookie.getMaxAge())
                        .append(")\n");
                    }
                }

                logger.trace(output.toString());
            }
        }
    }

    /**
     * Returns an HTTP 200 OK
     *
     * @return
     */
    protected Response ok() {
        return Response.ok().build();
    }

    /**
     * Returns an HTTP 200 OK with the entity
     *
     * @param entity
     * @return
     */
    protected Response ok(final Object entity) {
        return Response.ok(entity).build();
    }

    /**
     * Returns an HTTP 201 Created response with the entity. The created URI assumes the URI context was "collection" and the URI will add the id to
     * form "collection/{id}".
     *
     * @param id
     * @param entity
     * @return
     */
    protected Response created(final Object id, final Object entity) {
        final URI uri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
        return Response.created(uri).entity(entity).build();
    }

    /**
     * Returns an HTTP 404 Not Found
     *
     * @return
     */
    protected Response notFound() {
        return Response.status(Status.NOT_FOUND).build();
    }

    protected Response badRequest(final String message) {
        return Response.status(Status.BAD_REQUEST).entity(message == null ? "" : message)
        .build();
    }

    protected Response badRequest(final String message, final Object entity) {
        return Response.status(Status.BAD_REQUEST).entity(entity != null ? entity : message == null ? "" : message).build();
    }

    protected Response accepted() {
        return Response.status(Status.ACCEPTED).build();
    }

    protected Response internalServerError() {
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    protected Response internalServerError(final String message) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message == null ? "" : message)
        .build();
    }

    protected Response internalServerError(final Object entity) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(entity).build();
    }

    protected Response unauthorized() {
        return Response.status(Status.UNAUTHORIZED).build();
    }

    private class DummySecurityContext implements SecurityContext {
        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public boolean isUserInRole(final String role) {
            return false;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public String getAuthenticationScheme() {
            return null;
        }
    }
}
