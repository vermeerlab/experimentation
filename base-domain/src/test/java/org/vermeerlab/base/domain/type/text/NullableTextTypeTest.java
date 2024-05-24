package org.vermeerlab.base.domain.type.text;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NullableTextTypeTest {

  public NullableTextTypeTest() {}

  @Test
  public void testOf_Optional() {
    var text1 = NullableTextTypeImpl.of(null);
    assertTrue(text1.isEmpty());

    var text2 = NullableTextTypeImpl.of("testStr");
    assertEquals("testStr", text2.getOrDefault());
  }

  @Test
  public void testIsEmpty() {
    var text1 = NullableTextTypeImpl.of("aa");
    assertFalse(text1.isEmpty());

    var text2 = NullableTextTypeImpl.of(null);
    assertTrue(text2.isEmpty());
  }

  @Test
  public void testHasText() {
    var text1 = NullableTextTypeImpl.of("aa");
    assertTrue(text1.hasText());

    var text2 = NullableTextTypeImpl.of(null);
    assertFalse(text2.hasText());
  }

  @Test
  public void testContains() {
    var text1 = NullableTextTypeImpl.of("aa");
    assertTrue(text1.contains(NullableTextTypeImpl.of("a")));
    assertFalse(text1.contains(NullableTextTypeImpl.of("b")));
    assertFalse(NullableTextTypeImpl.of(null).contains(NullableTextTypeImpl.of("b")));
    assertFalse(text1.contains(NullableTextTypeImpl.of(null)));
    assertFalse(NullableTextTypeImpl.of(null).contains(NullableTextTypeImpl.of(null)));
  }

  static class NullableTextTypeImpl implements NullableTextType<NullableTextTypeImpl> {

    private final String value;

    private NullableTextTypeImpl() {
      this.value = null;
    }

    private NullableTextTypeImpl(String value) {
      this.value = value;
    }

    public static NullableTextTypeImpl of(String value) {
      return new NullableTextTypeImpl(value);
    }

    @Override
    public Optional<String> getNullableValue() {
      return Optional.ofNullable(this.value);
    }
  }
}
