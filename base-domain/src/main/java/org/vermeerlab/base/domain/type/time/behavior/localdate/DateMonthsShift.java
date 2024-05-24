package org.vermeerlab.base.domain.type.time.behavior.localdate;

import java.time.LocalDate;
import org.vermeerlab.base.domain.type.object.NoArgumentNewInstance;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;
import org.vermeerlab.base.domain.type.time.NullableDateType;

/**
 * 日付（月）の補正.
 *
 * @author Yamashita.Takahiro
 * @param <T> 本インターフェースを実装した具象クラスの型
 */
public interface DateMonthsShift<T extends NullableDateType<T>>
    extends NullableDateType<T>, NoArgumentNewInstance<T>, SingleArgumentNewInstance<LocalDate, T> {

  /**
   * 月初日補正をしたインスタンスを返却します.
   *
   * @return 月初日補正をしたインスタンス
   */
  default T beginMonth() {
    if (this.isEmpty()) {
      return this.newInstance();
    }
    LocalDate updated = this.getNullableValue().get().withDayOfMonth(1);
    return this.newInstance(updated);
  }

  /**
   * 月末日補正をしたインスタンスを返却します.
   *
   * @return 月末日補正をしたインスタンス
   */
  default T endMonth() {
    if (this.isEmpty()) {
      return this.newInstance();
    }
    var date = this.getNullableValue().get();
    var updated = this.getNullableValue().get().withDayOfMonth(date.lengthOfMonth());
    return this.newInstance(updated);
  }
}
