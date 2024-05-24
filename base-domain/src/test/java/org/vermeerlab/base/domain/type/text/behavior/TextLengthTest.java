package org.vermeerlab.base.domain.type.text.behavior;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TextLengthTest {

  @Test
  public void testLength() {
    var text1 = TextLengthImpl.of("aa");
    assertEquals(2, text1.length());

    var text2 = TextLengthImpl.of("");
    assertEquals(0, text2.length());

    var text3 = TextLengthImpl.of(null);
    assertEquals(0, text3.length());

    var text4 = TextLengthImpl.of("🍀");
    assertEquals(1, text4.length());

    var text5 = TextLengthImpl.of("👪");
    assertEquals(1, text5.length());

    var text6 = TextLengthImpl.of("𩸽");
    assertEquals(1, text6.length());

    var text7 = TextLengthImpl.of("飴󠄁");
    assertEquals(1, text7.length());

    var text8 = TextLengthImpl.of("\uD867\uDE3D"); // U+29E3D
    assertEquals(1, text8.length());

    var text9 = TextLengthImpl.of("飴\uDB40\uDD01"); // U+98F4 U+E0101
    assertEquals(1, text9.length());
  }

  static class TextLengthImpl implements TextLength<TextLengthImpl> {

    private final String value;

    private TextLengthImpl() {
      this.value = null;
    }

    private TextLengthImpl(String value) {
      this.value = value;
    }

    public static TextLengthImpl of(String value) {
      return new TextLengthImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }
  }
}
