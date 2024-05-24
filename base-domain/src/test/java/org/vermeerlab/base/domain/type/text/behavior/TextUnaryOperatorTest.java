package org.vermeerlab.base.domain.type.text.behavior;

import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TextUnaryOperatorTest {

  @Test
  public void testApply() {

    var text = TextUnaryOperatorImpl.of("aaa");
    var callbacked1 = text.apply((value) -> value + "test");
    assertEquals("aaatest", callbacked1.getOrDefault());

    var textEmpty = TextUnaryOperatorImpl.of(null);
    var callbacked2 = textEmpty.apply((value) -> (Objects.isNull(value) ? "bb" : "cc") + "test");
    assertEquals("bbtest", callbacked2.getOrDefault());
  }

  static class TextUnaryOperatorImpl implements TextUnaryOperator<TextUnaryOperatorImpl> {

    private final String value;

    private TextUnaryOperatorImpl(String value) {
      this.value = value;
    }

    public static TextUnaryOperatorImpl of(String value) {
      return new TextUnaryOperatorImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }

    @Override
    public TextUnaryOperatorImpl newInstance(String value) {
      return new TextUnaryOperatorImpl(value);
    }
  }
}
