package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** ServiceUnavailableExceptionMapper. */
@Provider
public class ServiceUnavailableExceptionMapper
    implements ExceptionMapper<ServiceUnavailableException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(ServiceUnavailableException e) {
    var message =
        messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_SERVICE_UNAVAILABLE);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(
                ErrorResponse.of(
                    ErrorMessageCode.HTTP_STATUS_SERVICE_UNAVAILABLE.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.SERVICE_UNAVAILABLE.getStatusCode());
  }
}
