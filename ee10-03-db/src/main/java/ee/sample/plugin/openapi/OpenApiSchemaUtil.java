package ee.sample.plugin.openapi;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.media.Schema;

/** Enumを複数選択するスキーマを作成するUtil. */
public class OpenApiSchemaUtil {

  /**
   * Enumを元にEnumをリスト定義したスキーマを作成します.
   *
   * @param enumClass スキーマのベースとなるEnumクラス
   * @return Enumを複数選択するスキーマ
   */
  public static Schema createEnumSchema(Class<? extends Enum<?>> enumClass) {
    var enums = enumClass.getEnumConstants();
    return OASFactory.createSchema()
        .description(enumClass.getSimpleName() + "のスキーマです.")
        .example(enums[0].name())
        .type(Schema.SchemaType.STRING)
        .enumeration(Stream.of(enums).map(Enum::name).collect(Collectors.toList()));
  }

  /**
   * Enumを元にEnumをリスト定義したスキーマを作成します.
   *
   * @param enumClass スキーマのベースとなるEnumクラス
   * @return Enumを複数選択するスキーマ
   */
  public static Schema createEnumListSchema(Class<? extends Enum<?>> enumClass) {
    var enums = enumClass.getEnumConstants();
    return OASFactory.createSchema()
        .description(enumClass.getSimpleName() + "を複数指定するためのスキーマです.")
        .example(enums[0].name())
        .type(Schema.SchemaType.ARRAY)
        .items(
            OASFactory.createSchema()
                .type(Schema.SchemaType.STRING)
                .enumeration(Stream.of(enums).map(Enum::name).collect(Collectors.toList())));
  }

  /**
   * 複数ファイルのアップロードを定義したスキーマを作成します.
   *
   * <p>プロパティ名のデフォルトは「file」です.
   *
   * <p>アノテーションでも指定できますが記述内容は固定となるためスキーマ定義として登録をしています.
   *
   * <pre>{@code
   * @RequestBody(
   *   description = "アップロードファイルを選択してください",
   *   content =
   *       @Content(
   *           mediaType = MediaType.MULTIPART_FORM_DATA,
   *           schema =
   *               @Schema(
   *                   type = SchemaType.OBJECT,
   *                   properties = {
   *                     @SchemaProperty(name = "file", type = SchemaType.STRING, format = "binary"),
   *                   })))
   * }</pre>
   *
   * @return 複数ファイルのアップロードするスキーマ
   */
  public static Schema createUploadFileSchema() {

    return OpenApiSchemaUtil.createUploadFileListSchema("file");
  }

  /**
   * 複数ファイルのアップロードを定義したスキーマを作成します.
   *
   * @param propertyName スキーマのプロパティ名
   * @return 複数ファイルのアップロードするスキーマ
   */
  public static Schema createUploadFileSchema(String propertyName) {

    return OASFactory.createSchema()
        .description("アップロードを指定するためのスキーマです.")
        .type(Schema.SchemaType.OBJECT)
        .properties(
            Map.of(
                propertyName,
                OASFactory.createSchema().type(Schema.SchemaType.STRING).format("binary")));
  }

  /**
   * 複数ファイルのアップロードを定義したスキーマを作成します.
   *
   * <p>プロパティ名のデフォルトは「files」です.
   *
   * @return 複数ファイルのアップロードするスキーマ
   */
  public static Schema createUploadFileListSchema() {

    return OpenApiSchemaUtil.createUploadFileListSchema("files");
  }

  /**
   * 複数ファイルのアップロードを定義したスキーマを作成します.
   *
   * @param propertyName スキーマのプロパティ名
   * @return 複数ファイルのアップロードするスキーマ
   */
  public static Schema createUploadFileListSchema(String propertyName) {

    return OASFactory.createSchema()
        .description("アップロードを複数指定するためのスキーマです.")
        .type(Schema.SchemaType.OBJECT)
        .properties(
            Map.of(
                propertyName,
                OASFactory.createSchema()
                    .type(Schema.SchemaType.ARRAY)
                    .items(
                        OASFactory.createSchema()
                            .type(Schema.SchemaType.STRING)
                            .format("binary"))));
  }
}
