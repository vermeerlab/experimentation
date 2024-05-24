package org.vermeerlab.base.domain.type.text.behavior;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TextRemoveReturnTest {

  @Test
  public void testRemoveReturn() {
    var text1 = TextRemoveReturnImpl.of("aa\n").removeReturn();
    assertEquals("aa", text1.getOrDefault());

    var text2 = TextRemoveReturnImpl.of(null).removeReturn();
    assertEquals("", text2.getOrDefault());
  }

  static class TextRemoveReturnImpl implements TextRemoveReturn<TextRemoveReturnImpl> {

    private final String value;

    private TextRemoveReturnImpl(String value) {
      this.value = value;
    }

    public static TextRemoveReturnImpl of(String value) {
      return new TextRemoveReturnImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }

    @Override
    public TextRemoveReturnImpl newInstance() {
      return new TextRemoveReturnImpl(null);
    }

    @Override
    public TextRemoveReturnImpl newInstance(String value) {
      return new TextRemoveReturnImpl(value);
    }
  }
}
