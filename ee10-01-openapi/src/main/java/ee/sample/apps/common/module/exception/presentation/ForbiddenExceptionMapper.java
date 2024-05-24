package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** ForbiddenExceptionMapper. */
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(ForbiddenException e) {
    var message = messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_FORBIDDEN);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(ErrorResponse.of(ErrorMessageCode.HTTP_STATUS_FORBIDDEN.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.FORBIDDEN.getStatusCode());
  }
}
