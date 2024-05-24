package org.vermeerlab.base.domain.type.collection.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FirstClassCollectionConsumerTest {

  @Test
  public void testAccept() {
    var fstClass = FirstClassCollectionConsumerImpl.of(List.of(new Item("1"), new Item("2")));

    List<Item> items = new ArrayList<>();

    // 本来は標準出力などの副作用のあるロジックの実行を想定
    fstClass.accept(item -> items.add(item));
    assertEquals(2, items.size());
    assertEquals("1", items.get(0).getValue());
    assertEquals("2", items.get(1).getValue());
  }

  public static class FirstClassCollectionConsumerImpl
      implements FirstClassCollectionConsumer<Item> {

    private List<Item> values;

    private FirstClassCollectionConsumerImpl(List<Item> values) {
      this.values = values;
    }

    static FirstClassCollectionConsumerImpl of(List<Item> values) {
      List<Item> items = Objects.nonNull(values) ? List.copyOf(values) : Collections.emptyList();
      return new FirstClassCollectionConsumerImpl(items);
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
}
