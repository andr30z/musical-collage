package br.com.andre.exception;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionHandler implements ExceptionMapper<HttpError> {

  @Override
  public Response toResponse(HttpError exception) {
    return buildResponseEntity(exception.getStatus(), exception.getMessage(), List.of(exception.getMessage()));
  }

  public Response buildResponseEntity(
      Response.Status httpStatus,
      String message,
      List<String> errors) {
    ApiError apiError = ApiError
        .builder()
        .code(httpStatus.getStatusCode())
        .status(httpStatus.getReasonPhrase())
        .message(message)
        .errors(errors)
        .timestamp(LocalDateTime.now())
        .build();
    return Response.status(httpStatus).entity(apiError).build();
  }
}
