package ee.sample.plugin.openapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.eclipse.microprofile.openapi.models.media.Content;

/** OpenApiExampleObjectUtil. */
public class OpenApiExampleObjectUtil {

  /**
   * ExampleObjectのExternalValueで指定したJsonをValueとして展開します.
   *
   * <p>Contentを直接上書きします.
   *
   * <p>上書きに使用した externalValue は消去します.
   *
   * <p>valueに記述がある場合はvalueの記述を優先します.
   *
   * @param content openApiの@Content
   * @throws RuntimeException IOExceptionが発生したら処理を中断します.
   */
  public static void convertExternalValueToValue(Content content) {

    content.getMediaTypes().entrySet().stream()
        .filter(e -> Objects.nonNull(e))
        .forEach(
            e1 -> {
              e1.getValue().getExamples().entrySet().stream()
                  .filter(
                      e2 ->
                          Objects.isNull(e2.getValue().getValue())
                              || e2.getValue().getValue().equals(""))
                  .filter(e2 -> Objects.nonNull(e2.getValue().getExternalValue()))
                  .forEach(
                      example -> {
                        var externalValue = example.getValue().getExternalValue();

                        ClassLoader loader = OpenApiExampleObjectUtil.class.getClassLoader();

                        try (var inputStream = loader.getResourceAsStream(externalValue)) {
                          if (Objects.isNull(inputStream)) {
                            throw new FileNotFoundException(
                                "externalValue =["
                                    + externalValue
                                    + "] cloud not find resource path.");
                          }
                          String json =
                              new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                          example.getValue().setValue(json);
                          example.getValue().setExternalValue("");
                        } catch (IOException ex) {
                          throw new UncheckedIOException(
                              "externalValue =["
                                  + externalValue
                                  + "] could not find resource path or could not read resource file.",
                              ex);
                        }
                      });
            });
  }
}
