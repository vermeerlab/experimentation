package ee.sample.apps.common.presentation.openapi;

import static java.util.Map.entry;

import ee.sample.apps.context.user.domain.Gender;
import ee.sample.plugin.openapi.OpenApiSchemaUtil;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.microprofile.openapi.models.Components;
import org.eclipse.microprofile.openapi.models.media.Schema;

/** OpenApiSchemaの変換およびSchemaにrefに指定する定数を管理します. */
public class OpenApiSchema {

  private static final String prifixPath = "#/components/schemas/";

  public static final String Gender = prifixPath + "Gender";
  public static final String Genders = prifixPath + "Genders";
  public static final String UploadFile = prifixPath + "UploadFile";
  public static final String UploadFiles = prifixPath + "UploadFiles";

  // openApiのUtilは実装依存となるため実行時に決定するように関数で指定します.
  private static final Map<String, Supplier<Schema>> schemaMap =
      Map.ofEntries(
          entry(Gender, () -> OpenApiSchemaUtil.createEnumSchema(Gender.class)),
          entry(Genders, () -> OpenApiSchemaUtil.createEnumListSchema(Gender.class)),
          entry(UploadFile, () -> OpenApiSchemaUtil.createUploadFileSchema()),
          entry(UploadFiles, () -> OpenApiSchemaUtil.createUploadFileListSchema()));

  /**
   * プロパティで指定したクラスをSchemaへ変換してOpenAPIのコンポーネントへ追記します.
   *
   * @param components OpenAPIのcomponents
   */
  public static void appendSchema(Components components) {

    validate();
    int startIndex = prifixPath.length();

    schemaMap.entrySet().stream()
        .forEach(
            entrySet -> {
              var key = entrySet.getKey().substring(startIndex);
              components.addSchema(key, entrySet.getValue().get());
            });
  }

  /** Publicフィールドとスキーマの設定をするMapの整合性が取れていることを検証します. */
  private static void validate() {

    var fieldList =
        Stream.of(OpenApiSchema.class.getFields())
            .filter(f -> f.getType().isPrimitive() == false)
            .filter(f -> f.getType().isInstance(""))
            .map(f -> f.getName())
            .collect(Collectors.toSet());

    if (schemaMap.entrySet().size() != fieldList.size()) {
      throw new IllegalArgumentException(
          "public static field is not match schemaMap. append Schema must match.");
    }
  }
}
