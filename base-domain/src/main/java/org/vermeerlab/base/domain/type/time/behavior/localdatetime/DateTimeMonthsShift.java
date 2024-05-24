package org.vermeerlab.base.domain.type.time.behavior.localdatetime;

import java.time.LocalDateTime;
import org.vermeerlab.base.domain.type.object.NoArgumentNewInstance;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;
import org.vermeerlab.base.domain.type.time.NullableDateTimeType;

/**
 * 日時（月）の補正.
 *
 * @author Yamashita.Takahiro
 * @param <T> 本インターフェースを実装した具象クラスの型
 */
public interface DateTimeMonthsShift<T extends NullableDateTimeType<T>>
    extends NullableDateTimeType<T>,
        NoArgumentNewInstance<T>,
        SingleArgumentNewInstance<LocalDateTime, T> {

  /**
   * 月初日補正をしたインスタンスを返却します.
   *
   * @return 月初日補正をしたインスタンス
   */
  default T beginMonth() {
    if (this.isEmpty()) {
      return this.newInstance();
    }
    LocalDateTime updated =
        this.getNullableValue().get().toLocalDate().atStartOfDay().withDayOfMonth(1);
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
    var date =
        this.getNullableValue()
            .get()
            .toLocalDate()
            .plusMonths(1L)
            .atStartOfDay()
            .withDayOfMonth(1)
            .minusNanos(1L);
    return this.newInstance(date);
  }
}
