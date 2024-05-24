package ee.sample.apps.common.module.exception.presentation;

import ee.sample.apps.common.module.message.application.MessageService;
import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

/** InternalServerErrorExceptionMapper. */
@Provider
public class InternalServerErrorExceptionMapper
    implements ExceptionMapper<InternalServerErrorException> {

  @Inject MessageService messageService;

  @Override
  public Response toResponse(InternalServerErrorException e) {
    var message =
        messageService.findByErrorMessageCode(ErrorMessageCode.HTTP_STATUS_INTERNAL_SERVER_ERROR);
    var errors =
        new ErrorResponse.ErrorResponses(
            List.of(
                ErrorResponse.of(
                    ErrorMessageCode.HTTP_STATUS_INTERNAL_SERVER_ERROR.name(), message)));
    return ResponseFactory.fail(errors, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }
}
