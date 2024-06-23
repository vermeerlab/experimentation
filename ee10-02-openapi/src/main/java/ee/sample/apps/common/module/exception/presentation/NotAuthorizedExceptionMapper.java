package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** NotAuthorizedExceptionMapper. */
@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(NotAuthorizedException e) {
    var message = messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_UNAUTHORIZED);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(ErrorResponse.of(ErrorMessageCode.HTTP_STATUS_UNAUTHORIZED.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.UNAUTHORIZED.getStatusCode());
  }
}
