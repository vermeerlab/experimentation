package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** NotSupportedExceptionMapper. */
@Provider
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(NotSupportedException e) {
    var message =
        messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_UNSUPPORTED_MEDIA_TYPE);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(
                ErrorResponse.of(
                    ErrorMessageCode.HTTP_STATUS_UNSUPPORTED_MEDIA_TYPE.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
  }
}
