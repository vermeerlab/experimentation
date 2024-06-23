package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** NotAcceptableExceptionMapper. */
@Provider
public class NotAcceptableExceptionMapper implements ExceptionMapper<NotAcceptableException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(NotAcceptableException e) {
    var message =
        messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_NOT_ACCEPTABLE);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(ErrorResponse.of(ErrorMessageCode.HTTP_STATUS_NOT_ACCEPTABLE.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.NOT_ACCEPTABLE.getStatusCode());
  }
}
