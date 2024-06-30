package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** NotAllowedExceptionMapper. */
@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(NotAllowedException e) {
    var message =
        messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_METHOD_NOT_ALLOWED);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(
                ErrorResponse.of(ErrorMessageCode.HTTP_STATUS_METHOD_NOT_ALLOWED.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.METHOD_NOT_ALLOWED.getStatusCode());
  }
}
