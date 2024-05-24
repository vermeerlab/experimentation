package ee.sample.apps.common.module.exception.presentation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import ee.sample.apps.common.presentation.openapi.ResponseBody;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema
@lombok.AllArgsConstructor(staticName = "of")
@lombok.Data
@SuppressWarnings("checkstyle:MissingJavadocType")
public class ErrorResponse {

  @Schema(title = "メッセージコード", example = "NOT_FOUND")
  private String messageCode;

  @Schema(title = "メッセージ", example = "Resource Not Found")
  private String message;

  @SuppressFBWarnings(value = {"EI_EXPOSE_REP"})
  @lombok.AllArgsConstructor
  @lombok.Data
  public static class ErrorResponses {
    private List<ErrorResponse> errors;
  }

  public static class ErrorResponseBody extends ResponseBody<ErrorResponses> {}
}
