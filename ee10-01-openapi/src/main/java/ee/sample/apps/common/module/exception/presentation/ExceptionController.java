package ee.sample.apps.common.module.exception.presentation;

import ee.sample.spec.layer.presentation.EntryPoint;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/** Http Status 4xx,5xx のレスポンスのOpenAPI用のURL. */
@Path("/openapi-http-ng-status")
@Tag(ref = "ExceptionController")
@EntryPoint
@SuppressWarnings("checkstyle:MissingJavadocMethod")
public class ExceptionController {

  @SuppressWarnings("resource")
  @GET
  @Path("{httpStatus}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "HttpのNGステータスのレスポンス仕様",
      operationId = "getHttpNgStatus",
      description =
          ""
              + "多くのケースでは2xx以外のステータスはExceptionHandlerなどで一括して操作を行います.<br>\n"
              + "各API毎に4xxのステータス仕様を記載したり、共通して出力するような実装を追加することもできますが<br>\n"
              + "冗長な記載を減らすため本URLに4xxおよび5xxのAPI仕様は本URLへ集約します.\n"
              + "クライアントにて4xx,5xxのHttpステータス毎の制御確認での利用も想定しています.")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"構文が無効です\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_BAD_REQUEST\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "400")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"未認証です\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_UNAUTHORIZED\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "401")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"アクセス権がありません\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_FORBIDDEN\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "403")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"対象が存在しません\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_NOT_FOUND\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "404")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"Httpメソッドが無効です\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_METHOD_NOT_ALLOWED\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "405")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"指定のデータ形式は提供していません\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_NOT_ACCEPTABLE\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "406")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"指定のメディア形式は提供していません\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_UNSUPPORTED_MEDIA_TYPE\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "415")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"システム内部エラー\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_INTERNAL_SERVER_ERROR\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "500")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.ErrorResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"ok\": false,\n"
                          + "  \"body\": {\n"
                          + "    \"errors\": [\n"
                          + "      {\n"
                          + "        \"message\": \"一時的に利用できません\",\n"
                          + "        \"messageCode\": \"HTTP_STATUS_SERVICE_UNAVAILABLE\"\n"
                          + "      }\n"
                          + "    ]\n"
                          + "  }\n"
                          + "}")
            })
      },
      responseCode = "503")
  public void getHttpNgStatus(
      @Parameter(
              description = "確認したいHTTPステータス",
              required = true,
              schema =
                  @Schema(
                      enumeration = {
                        "400", "401", "403", "404", "405", "406", "415", "500", "503"
                      }))
          @PathParam("httpStatus")
          int httpStatus) {

    switch (httpStatus) {
      case 400:
        throw new BadRequestException();
      case 401:
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
      case 403:
        throw new ForbiddenException();
      case 404:
        throw new NotFoundException();
      case 405:
        throw new NotAllowedException(Response.status(Response.Status.METHOD_NOT_ALLOWED).build());
      case 406:
        throw new NotAcceptableException();
      case 415:
        throw new NotSupportedException();
      case 500:
        throw new InternalServerErrorException();
      case 503:
        throw new ServiceUnavailableException();
      default:
        throw new InternalServerErrorException();
    }
  }
}
