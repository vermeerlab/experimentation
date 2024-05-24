package org.vermeerlab.base.domain.type.collection;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FirstClassCollectionTypeTest {

  @Test
  public void testGetValues() {

    var fstClassCollection = FirstClassCollectionTypeImpl.of(List.of(new Item("1"), new Item("2")));
    List<Item> items = fstClassCollection.getValues();

    assertEquals(new Item("1"), items.get(0));
    assertEquals(new Item("2"), items.get(1));
    assertEquals(2, items.size());
  }

  @Test
  public void testIsEmpty() {
    var fstClassCollection = FirstClassCollectionTypeImpl.of(null);
    assertTrue(fstClassCollection.isEmpty());
  }

  @Test
  public void testSize() {
    var fstClassCollection = FirstClassCollectionTypeImpl.of(List.of(new Item("1"), new Item("2")));
    assertEquals(2, fstClassCollection.size());
  }

  @Test
  public void testApply() {
    var fstClass = FirstClassCollectionTypeImpl.of(List.of(new Item("1"), new Item("2")));
    List<Item2> convertedItemList = fstClass.apply(item -> new Item2(item.getValue()));
    assertEquals(2, convertedItemList.size());
    assertEquals("1", convertedItemList.get(0).getValue());
    assertEquals("2", convertedItemList.get(1).getValue());
  }

  static class FirstClassCollectionTypeImpl implements FirstClassCollectionType<Item> {

    private final List<Item> values;

    private FirstClassCollectionTypeImpl(List<Item> values) {
      this.values = List.copyOf(values);
    }

    static FirstClassCollectionTypeImpl of(List<Item> values) {
      List<Item> items = Objects.nonNull(values) ? List.copyOf(values) : Collections.emptyList();
      return new FirstClassCollectionTypeImpl(items);
    }

    @Override
    public List<Item> getValues() {
      return this.values;
    }
  }

  static class Item {

    // 不変となる実装をしておくこと
    private final String value;

    public Item(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 59 * hash + Objects.hashCode(this.value);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Item other = (Item) obj;
      if (!Objects.equals(this.value, other.value)) {
        return false;
      }
      return true;
    }
  }

  static class Item2 {

    // 不変となる実装をしておくこと
    private final String value;

    public Item2(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 59 * hash + Objects.hashCode(this.value);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Item other = (Item) obj;
      if (!Objects.equals(this.value, other.value)) {
        return false;
      }
      return true;
    }
  }
}
