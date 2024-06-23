package ee.sample.apps.context.user.presentation;

import ee.sample.apps.common.presentation.openapi.BaseResponseBody;
import ee.sample.apps.common.presentation.openapi.OpenApiSchema;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import ee.sample.apps.context.user.application.SearchUser;
import ee.sample.apps.context.user.domain.Gender;
import ee.sample.apps.context.user.domain.UserSearchCondition;
import ee.sample.apps.context.user.domain.UserSearchMultiGenderCondition;
import ee.sample.spec.layer.presentation.EntryPoint;
import ee.sample.utils.json.JsonUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/** UserBeanParamController. */
@Path("/open-api-custom")
@EntryPoint
@Tag(ref = "UserOpenApiCustomController")
@SuppressWarnings("checkstyle:MissingJavadocMethod")
public class UserOpenApiCustomController {
  @Inject SearchUser searchUser;

  @GET
  @Path("beamparam")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "ユーザー情報を検索します（BeanParamを使用）",
      description = "クエリーで指定した条件を絞り込み条件として使用します。<br>" + "条件を指定しない場合は全レコードが取得対象となります。<br>")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = UserResponse.UserResponseListBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  externalValue = "openapi/user/get_response_default.json"),
              @ExampleObject(
                  name = "return 2 record",
                  externalValue = "openapi/user/get_response_2_record.json")
            })
      },
      responseCode = "200")
  @Parameters({
    @Parameter(
        name = "gender",
        in = ParameterIn.QUERY,
        description = "性別",
        schema = @Schema(ref = OpenApiSchema.Gender)),
    @Parameter(
        name = "name",
        in = ParameterIn.QUERY,
        description = "ユーザー名",
        schema = @Schema(example = "User Name"))
  })
  public Response getUsersByBeanParam(@BeanParam UserQueryParam userQueryParam) {

    var condition = UserSearchCondition.of(userQueryParam.getGender(), userQueryParam.getName());
    var models = searchUser.findByCondition(condition);
    return ResponseFactory.success(UserResponse.from(models));
  }

  @GET
  @Path("enumlist")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "ユーザー情報を検索します",
      description = "クエリーで指定した条件を絞り込み条件として使用します。<br>" + "条件を指定しない場合は全レコードが取得対象となります")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = UserResponse.UserResponseListBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  externalValue = "openapi/user/get_response_default.json"),
              @ExampleObject(
                  name = "return 2 record",
                  externalValue = "openapi/user/get_response_2_record.json")
            })
      },
      responseCode = "200")
  @Parameters({
    @Parameter(
        name = "genders",
        description = "性別（複数指定）",
        schema = @Schema(ref = OpenApiSchema.Genders)),
    @Parameter(name = "gender", description = "性別", schema = @Schema(ref = OpenApiSchema.Gender)),
    @Parameter(name = "name", description = "ユーザー名", example = "user name")
  })
  public Response getUsersByQueryWithListEnum(
      @QueryParam("genders") List<Gender> genders,
      @QueryParam("gender") Gender gender,
      @QueryParam("name") String name) {

    var condition = UserSearchMultiGenderCondition.of(genders, gender, name);
    var models = searchUser.findByCondition(condition);
    return ResponseFactory.success(UserResponse.from(models));
  }

  @POST
  @Path("{id}/file")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "ユーザーに関連するファイルをアップロードします")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = BaseResponseBody.class),
            examples = @ExampleObject(name = "default", externalValue = "openapi/response_ok.json"))
      },
      responseCode = "200")
  @Parameters(@Parameter(name = "id", description = "ユーザーID", required = true))
  @RequestBody(
      name = "file",
      description = "アップロードファイルを選択してください",
      required = true,
      content =
          @Content(
              mediaType = MediaType.MULTIPART_FORM_DATA,
              schema = @Schema(ref = OpenApiSchema.UploadFile)))
  public Response postCustomUploadUserFile(@PathParam("id") String id, EntityPart file) {

    String name = file.getName();
    Optional<String> fileName = file.getFileName();
    //      InputStream is = part.getContent();
    MediaType mediaType = file.getMediaType();

    System.out.println("name:" + name);
    System.out.println("filename:" + fileName);
    System.out.println("mediaType:" + mediaType.getType());

    return ResponseFactory.success();
  }

  @POST
  @Path("{id}/files")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "ユーザーに関連するファイルを複数アップロードします")
  @Parameters(@Parameter(name = "id", description = "ユーザーID", required = true))
  @RequestBody(
      name = "files",
      description = "アップロードファイルを選択してください",
      required = true,
      content =
          @Content(
              mediaType = MediaType.MULTIPART_FORM_DATA,
              schema = @Schema(ref = OpenApiSchema.UploadFiles)))
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = BaseResponseBody.class),
            examples = @ExampleObject(name = "default", externalValue = "openapi/response_ok.json"))
      },
      responseCode = "200")
  public Response postCustomUploadUserFiles(@PathParam("id") String id, List<EntityPart> files) {

    for (EntityPart part : files) {
      String name = part.getName();
      Optional<String> fileName = part.getFileName();
      //      InputStream is = part.getContent();
      MediaType mediaType = part.getMediaType();

      System.out.println("name:" + name);
      System.out.println("filename:" + fileName);
      System.out.println("mediaType:" + mediaType.getType());
    }
    return ResponseFactory.success();
  }

  @GET
  @Path("json/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "ユーザー情報を検索します(JsonMock)",
      description = "ExampleObjectで指定したJsonファイルをモックとしてレスポンスとして返却します.")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = UserResponse.UserResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  externalValue = "openapi/user/get_response_default.json")
            })
      },
      responseCode = "200")
  @Parameters({@Parameter(name = "id", description = "ユーザーID", required = true)})
  public UserResponse.UserResponseBody getJsonUserById(@PathParam("id") String id) {
    var response =
        JsonUtil.readFromResource(
            "openapi/user/get_response_default.json", UserResponse.UserResponseBody.class);
    return response.get();
  }
}
