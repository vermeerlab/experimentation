package org.vermeerlab.base.domain.type.numeric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullableNumberTypeTest {

  @Test
  public void testGetOrZero() {
    var actual = new NullableNumberTypeImpl(null);
    Assertions.assertEquals(BigDecimal.ZERO, actual.getOrZero());
  }

  @Test
  public void testIsEmpty() {
    var actual = new NullableNumberTypeImpl(null);
    Assertions.assertTrue(actual.isEmpty());
  }

  @Test
  public void testGetScale() {
    var actual = new NullableNumberTypeImpl(null);
    Assertions.assertEquals(0, actual.getScale());
  }

  @Test
  public void testGetRoundingMode() {
    var actual = new NullableNumberTypeImpl(null);
    Assertions.assertEquals(RoundingMode.UNNECESSARY, actual.getRoundingMode());
  }

  public class NullableNumberTypeImpl implements NullableNumberType<BigDecimal> {

    private BigDecimal value;

    public NullableNumberTypeImpl(BigDecimal value) {
      this.value = value;
    }

    @Override
    public Optional<BigDecimal> getNullableValue() {
      return Optional.ofNullable(value);
    }
  }
}