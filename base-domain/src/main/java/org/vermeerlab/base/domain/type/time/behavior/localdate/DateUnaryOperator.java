package org.vermeerlab.base.domain.type.time.behavior.localdate;

import java.time.LocalDate;
import java.util.function.UnaryOperator;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;
import org.vermeerlab.base.domain.type.time.NullableDateType;

/**
 * 保持しているLocalDateを編集.
 *
 * @author Yamashita.Takahiro
 * @param <T> 本インターフェースを実装した具象クラスの型
 */
public interface DateUnaryOperator<T>
    extends NullableDateType<T>, SingleArgumentNewInstance<LocalDate, T> {

  /**
   * callbackを用いて保持している値を編集して新しいインスタンスを生成します.
   *
   * @param callback コールバック関数
   * @return 編集後の新しいインスタンス.
   */
  default T apply(UnaryOperator<LocalDate> callback) {
    LocalDate updated = callback.apply(this.getNullableValue().orElse(null));
    return this.newInstance(updated);
  }
}
