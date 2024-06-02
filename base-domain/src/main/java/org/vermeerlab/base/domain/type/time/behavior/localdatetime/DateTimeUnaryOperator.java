package org.vermeerlab.base.domain.type.time.behavior.localdatetime;

import java.time.LocalDateTime;
import java.util.function.UnaryOperator;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;
import org.vermeerlab.base.domain.type.time.NullableDateTimeType;

/**
 * 保持しているLocalDateTimeを編集.
 *
 * @author Yamashita.Takahiro
 * @param <T> 本インターフェースを実装した具象クラスの型
 */
public interface DateTimeUnaryOperator<T>
    extends NullableDateTimeType<T>, SingleArgumentNewInstance<LocalDateTime, T> {

  /**
   * callbackを用いて保持している値を編集して新しいインスタンスを生成します.
   *
   * @param callback コールバック関数
   * @return 編集後の新しいインスタンス.
   */
  default T apply(UnaryOperator<LocalDateTime> callback) {
    LocalDateTime updated = callback.apply(this.getNullableValue().orElse(null));
    return this.newInstance(updated);
  }
}
