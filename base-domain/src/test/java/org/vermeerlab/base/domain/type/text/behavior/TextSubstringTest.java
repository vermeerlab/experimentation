package org.vermeerlab.base.domain.type.text.behavior;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TextSubstringTest {

  @Test
  public void testSubstringBegin() {
    var text1 = TextSubstringImpl.of("123456789");
    assertEquals("123456789", text1.substring(0).getOrDefault());
    assertEquals("123456789", "123456789".substring(0));

    var text2 = TextSubstringImpl.of("🍀𩸽飴\uDB40\uDD01");
    assertEquals("🍀𩸽飴\uDB40\uDD01", text2.substring(0).getOrDefault());
    assertEquals("𩸽飴\uDB40\uDD01", text2.substring(1).getOrDefault());

    assertEquals("", text2.substring(100).getOrDefault());
    assertEquals("", "123456789".substring(9));
  }

  @Test
  public void testSubstring() {
    var text1 = TextSubstringImpl.of("123456789");

    assertEquals("", text1.substring(-1, 1).getOrDefault());

    assertEquals("1", text1.substring(0, 1).getOrDefault());
    assertEquals("1", "123456789".substring(0, 1));

    var text2 = TextSubstringImpl.of("🍀𩸽飴\uDB40\uDD01");
    assertEquals("🍀", text2.substring(0, 1).getOrDefault());
    assertEquals("𩸽", text2.substring(1, 2).getOrDefault());

    assertEquals("", text2.substring(1, 1).getOrDefault());
    assertEquals("", "123456789".substring(1, 1));

    assertEquals("🍀𩸽飴\uDB40\uDD01", text2.substring(0, 100).getOrDefault());
  }

  static class TextSubstringImpl implements TextSubstring<TextSubstringImpl> {

    private final String value;

    private TextSubstringImpl(String value) {
      this.value = value;
    }

    public static TextSubstringImpl of(String value) {
      return new TextSubstringImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }

    @Override
    public TextSubstringImpl newInstance() {
      return new TextSubstringImpl(null);
    }

    @Override
    public TextSubstringImpl newInstance(String value) {
      return new TextSubstringImpl(value);
    }
  }
}
