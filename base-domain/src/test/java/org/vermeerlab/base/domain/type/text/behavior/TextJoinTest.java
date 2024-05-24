package org.vermeerlab.base.domain.type.text.behavior;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TextJoinTest {

  @Test
  public void testConcat() {
    var text1 = TextJoinImpl.of("aa").concat(TextJoinImpl.of("bb"), TextJoinImpl.of("cc"));
    assertEquals("aabbcc", text1.getOrDefault());
  }

  @Test
  public void testJoin() {
    var text1 =
        TextJoinImpl.of("aa")
            .join(TextJoinImpl.of("-"), TextJoinImpl.of("bb"), TextJoinImpl.of("cc"));
    assertEquals("aa-bb-cc", text1.getOrDefault());
  }

  static class TextJoinImpl implements TextJoin<TextJoinImpl> {

    private final String value;

    private TextJoinImpl(String value) {
      this.value = value;
    }

    public static TextJoinImpl of(String value) {
      return new TextJoinImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }

    @Override
    public TextJoinImpl newInstance(String value) {
      return new TextJoinImpl(value);
    }
  }
}
