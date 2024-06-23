package ee.sample.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/** JsonUtil. */
public class JsonUtil {

  private static final Logger logger = Logger.getLogger(JsonUtil.class.getName());

  /**
   * Json文字列をクラスにマッピングします.
   *
   * @param <T> マッピングするクラスの型
   * @param json json文字列
   * @param classType マッピングするクラスの型
   * @return マッピングしたインスタンス.例外があった場合は {@code Optional.empty()}
   */
  public static <T> Optional<T> read(String json, Class<T> classType) {

    ObjectMapper mapper = new ObjectMapper();
    try {
      T object = mapper.readValue(json, classType);
      return Optional.of(object);
    } catch (JsonProcessingException ex) {
      logger.log(Level.SEVERE, "Json could not parse.", ex);
      return Optional.empty();
    }
  }

  /**
   * Jsonリソースをクラスにマッピングします.
   *
   * @param <T> マッピングするクラスの型
   * @param resourcePath リソースパス
   * @param classType マッピングするクラスの型
   * @return マッピングしたインスタンス.例外があった場合は {@code Optional.empty()}
   */
  public static <T> Optional<T> readFromResource(String resourcePath, Class<T> classType) {

    ClassLoader loader = Thread.currentThread().getContextClassLoader();

    try (var inputStream = loader.getResourceAsStream(resourcePath)) {
      if (Objects.isNull(inputStream)) {
        throw new FileNotFoundException(
            "resourcePath =[" + resourcePath + "] cloud not find resource path.");
      }

      String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      return read(json, classType);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "resourcePath =[" + resourcePath + "] ioexception.", ex);
    }

    return Optional.empty();
  }
}
