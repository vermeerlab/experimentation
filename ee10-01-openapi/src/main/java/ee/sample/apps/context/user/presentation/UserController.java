package ee.sample.apps.context.user.presentation;

import ee.sample.apps.common.presentation.openapi.BaseResponseBody;
import ee.sample.apps.common.presentation.openapi.ResponseFactory;
import ee.sample.apps.context.user.application.DeleteUser;
import ee.sample.apps.context.user.application.PersistUser;
import ee.sample.apps.context.user.application.SearchUser;
import ee.sample.apps.context.user.application.UpdateUser;
import ee.sample.apps.context.user.domain.Gender;
import ee.sample.apps.context.user.domain.UserId;
import ee.sample.apps.context.user.domain.UserSearchCondition;
import ee.sample.spec.layer.presentation.EntryPoint;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.OutputStream;
import java.util.Optional;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/** UserController. */
@Path("/users")
@EntryPoint
@Tag(ref = "UserController")
@SuppressWarnings("checkstyle:MissingJavadocMethod")
public class UserController {

  @Inject SearchUser searchUser;
  @Inject PersistUser persistUser;
  @Inject UpdateUser updateUser;
  @Inject DeleteUser deleteUser;

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "ユーザー情報を検索します")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = UserResponse.UserResponseBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      "{\n"
                          + "  \"body\": {\n"
                          + "    \"gender\": \"OTHER\",\n"
                          + "    \"name\": \"Name:example\",\n"
                          + "    \"id\": \"100\"\n"
                          + "  },\n"
                          + "  \"ok\": true\n"
                          + "}")
            })
      },
      responseCode = "200")
  @Parameters({@Parameter(name = "id", description = "ユーザーID")})
  public Response getUserById(@PathParam("id") String id) {
    var model = searchUser.findById(UserId.of(id)).orElseThrow(() -> new NotFoundException());
    return ResponseFactory.success(UserResponse.from(model));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "ユーザー情報を検索します",
      operationId = "getUsersByQuery",
      description = "クエリーで指定した条件を絞り込み条件として使用します。<br>" + "条件を指定しない場合は全レコードが取得対象となります")
  @APIResponse(
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = UserResponse.UserResponseListBody.class),
            examples = {
              @ExampleObject(
                  name = "default",
                  value =
                      ""
                          + "{\n"
                          + "  \"body\": [\n"
                          + "    {\n"
                          + "      \"gender\": \"OTHER\",\n"
                          + "      \"name\": \"Name:example\",\n"
                          + "      \"id\": \"100\"\n"
                          + "    }\n"
                          + "  ],\n"
                          + "  \"ok\": true\n"
                          + "}"),
              @ExampleObject(
                  name = "return 2 record",
                  value =
                      ""
                          + "{\n"
                          + "  \"body\": [\n"
                          + "    {\n"
                          + "      \"gender\": \"MALE\",\n"
                          + "      \"name\": \"Name:user name1\",\n"
                          + "      \"id\": \"1\"\n"
                          + "    },\n"
                          + "    {\n"
                          + "      \"gender\": \"OTHER\",\n"
                          + "      \"name\": \"Name:user name2\",\n"
                          + "      \"id\": \"2\"\n"
                          + "    }\n"
                          + "  ],\n"
                          + "  \"ok\": true\n"
                          + "}")
            })
      },
      responseCode = "200")
  @Parameters({
    @Parameter(
        name = "gender",
        description = "性別",
        schema =
            @Schema(
                enumeration = {"MALE", "FEMALE", "OTHER"},
                implementation = String.class)),
    @Parameter(name = "name", description = "ユーザー名", example = "user name")
  })
  public Response getUsersByQuery(
      @QueryParam("gender") Gender gender, @QueryParam("name") String name) {

    var condition = UserSearchCondition.of(gender, name);
    var models = searchUser.findByCondition(condition);
    return ResponseFactory.success(UserResponse.from(models));
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "ユーザー情報を登録します")
  @APIResponse(
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = UserResourceId.UserResponseIdBody.class),
              examples = {
                @ExampleObject(
                    name = "default",
                    value =
                        ""
                            + "{\n"
                            + "  \"body\": {\n"
                            + "    \"id\": \"57d1a3b9-bb09-42f4-9913-941de0a7d4cb\"\n"
                            + "  },\n"
                            + "  \"ok\": true\n"
                            + "}")
              }),
      responseCode = "201")
  @RequestBody(
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              examples = {
                @ExampleObject(
                    name = "default",
                    value =
                        ""
                            + "{\n"
                            + "    \"gender\": \"MALE\",\n"
                            + "    \"name\": \"Name:name-1\"\n"
                            + "}")
              }))
  public Response postUser(UserRequest userRequest) {

    var persistedUser = persistUser.invoke(userRequest.toModel());
    return ResponseFactory.created(UserResourceId.from(persistedUser.getUserId()));
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "ユーザー情報を更新します")
  @APIResponse(responseCode = "204")
  @Parameters({
    @Parameter(
        name = "id",
        description = "ユーザーID",
        example = "57d1a3b9-bb09-42f4-9913-941de0a7d4cb")
  })
  @RequestBody(
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              examples = {
                @ExampleObject(
                    name = "default",
                    value =
                        ""
                            + "{\n"
                            + "    \"gender\": \"MALE\",\n"
                            + "    \"name\": \"Name:name-1\"\n"
                            + "}")
              }))
  public void putUser(@PathParam("id") String id, UserRequest userRequest) {
    updateUser.invoke(userRequest.toModel(id));
  }

  @DELETE
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "ユーザー情報を削除します")
  @APIResponse(responseCode = "204")
  @Parameters({
    @Parameter(
        name = "id",
        description = "ユーザーID",
        example = "57d1a3b9-bb09-42f4-9913-941de0a7d4cb")
  })
  public void deleteUser(@PathParam("id") String id) {
    deleteUser.invoke(UserId.of(id));
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
            examples = @ExampleObject(name = "default", value = "{\"ok\": true}"))
      },
      responseCode = "200")
  @Parameters(@Parameter(name = "id", description = "ユーザーID"))
  @RequestBody(
      description = "アップロードファイルを選択してください",
      content =
          @Content(
              mediaType = MediaType.MULTIPART_FORM_DATA,
              schema =
                  @Schema(
                      type = SchemaType.OBJECT,
                      properties = {
                        @SchemaProperty(name = "file", type = SchemaType.STRING, format = "binary"),
                      })))
  public Response postUploadUserFile(@PathParam("id") String id, EntityPart file) {

    String name = file.getName();
    Optional<String> fileName = file.getFileName();
    //      InputStream is = part.getContent();
    MediaType mediaType = file.getMediaType();

    System.out.println("name:" + name);
    System.out.println("filename:" + fileName);
    System.out.println("mediaType:" + mediaType.getType());
    return ResponseFactory.success();
  }

  @GET
  @Path("{id}/file")
  @Produces(value = MediaType.TEXT_PLAIN)
  @Operation(summary = "ユーザー情報のファイルをダウンロードします")
  @APIResponse(responseCode = "200")
  @Parameters({@Parameter(name = "id", description = "ユーザーID")})
  public Response getDownloadUserFile(@PathParam("id") String id) {

    StreamingOutput stream =
        (OutputStream out) -> {
          out.write("download file".getBytes("UTF-8"));
        };

    return ResponseFactory.download(stream, "download.txt");
  }
}
