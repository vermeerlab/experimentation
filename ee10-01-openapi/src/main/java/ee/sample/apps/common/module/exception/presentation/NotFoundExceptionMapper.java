package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** NotFoundExceptionMapper. */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(NotFoundException e) {
    var message = messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_NOT_FOUND);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(ErrorResponse.of(ErrorMessageCode.HTTP_STATUS_NOT_FOUND.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.NOT_FOUND.getStatusCode());
  }
}
