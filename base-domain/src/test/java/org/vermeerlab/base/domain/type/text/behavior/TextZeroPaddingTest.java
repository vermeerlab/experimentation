package org.vermeerlab.base.domain.type.text.behavior;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TextZeroPaddingTest {

  @Test
  public void testPadZero() {
    var text1 = TextZeroPaddingImpl.of("aa").zeroPadding(5);
    assertEquals("000aa", text1.getOrDefault());

    var text2 = TextZeroPaddingImpl.of(null).zeroPadding(5);
    assertEquals("00000", text2.getOrDefault());
  }

  static class TextZeroPaddingImpl implements TextZeroPadding<TextZeroPaddingImpl> {

    private final String value;

    private TextZeroPaddingImpl(String value) {
      this.value = value;
    }

    public static TextZeroPaddingImpl of(String value) {
      return new TextZeroPaddingImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }

    @Override
    public TextZeroPaddingImpl newInstance(String value) {
      return new TextZeroPaddingImpl(value);
    }
  }
}
