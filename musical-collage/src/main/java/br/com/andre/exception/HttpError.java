package br.com.andre.exception;

import jakarta.ws.rs.core.Response;

public class HttpError extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Response.Status status;


    public HttpError(String message, Response.Status status) {
        super(message);
        this.status = status;
    }
    
    public HttpError(String message ) {
        super(message);
        this.status = Response.Status.BAD_REQUEST;
    }

    public Response.Status getStatus() {
        return status;
    }
}
