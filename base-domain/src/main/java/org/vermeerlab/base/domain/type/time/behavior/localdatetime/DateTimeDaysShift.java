package org.vermeerlab.base.domain.type.time.behavior.localdatetime;

import java.time.LocalDateTime;
import org.vermeerlab.base.domain.type.object.NoArgumentNewInstance;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;
import org.vermeerlab.base.domain.type.time.NullableDateTimeType;

/**
 * 日時（日）の補正.
 *
 * @author Yamashita.Takahiro
 * @param <T> 本インターフェースを実装した具象クラスの型
 */
public interface DateTimeDaysShift<T extends NullableDateTimeType<T>>
    extends NullableDateTimeType<T>,
        NoArgumentNewInstance<T>,
        SingleArgumentNewInstance<LocalDateTime, T> {

  /**
   * １日の開始時刻補正をしたインスタンスを返却します.
   *
   * @return 開始時刻補正をしたインスタンス
   */
  default T beginDay() {
    if (this.isEmpty()) {
      return this.newInstance();
    }
    LocalDateTime updated = this.getNullableValue().get().toLocalDate().atStartOfDay();
    return this.newInstance(updated);
  }

  /**
   * １日の終了時刻補正をしたインスタンスを返却します.
   *
   * @return 終了時刻補正をしたインスタンス
   */
  default T endDay() {
    if (this.isEmpty()) {
      return this.newInstance();
    }
    var updated =
        this.getNullableValue().get().toLocalDate().plusDays(1).atStartOfDay().minusNanos(1);
    return this.newInstance(updated);
  }
}
