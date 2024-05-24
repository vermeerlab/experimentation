package org.vermeerlab.base.domain.type.collection.behavior;

import java.util.function.Consumer;
import org.vermeerlab.base.domain.type.collection.FirstClassCollectionType;

/**
 * リスト要素を使用した副作用.
 *
 * @author Yamashita.Takahiro
 */
public interface FirstClassCollectionConsumer<T> extends FirstClassCollectionType<T> {

  /**
   * リスト要素毎に副作用の伴う操作を行います.
   *
   * @param consumer 副作用を行う関数
   */
  default void accept(Consumer<T> consumer) {
    this.getValues()
        .forEach(
            item -> {
              consumer.accept(item);
            });
  }
}
