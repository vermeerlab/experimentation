package ee.sample.apps.common.domain.part.text;

import java.util.Optional;
import org.vermeerlab.base.domain.type.text.NullableTextType;

/**
 * 汎用文字列.
 *
 * @author Yamashita Takahiro
 */
@lombok.AllArgsConstructor
public class Text implements NullableTextType<Text> {

  private final String value;

  public static Text of(String value) {
    return new Text(value);
  }

  @Override
  public Optional<String> getNullableValue() {
    return Optional.ofNullable(value);
  }
}
