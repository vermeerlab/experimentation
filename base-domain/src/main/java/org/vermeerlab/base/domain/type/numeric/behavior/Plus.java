package org.vermeerlab.base.domain.type.numeric.behavior;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Stream;
import org.vermeerlab.base.domain.type.numeric.NullableNumberType;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;

/**
 * 加算計算.
 *
 * <p>加算の型を同一とすることで単位が相違しないことを強制します. <br>
 *
 * @param <T> 実装クラスの型
 * @author Yamashita.Takahiro
 */
public interface Plus<T extends NullableNumberType<T>>
    extends NullableNumberType<T>, SingleArgumentNewInstance<BigDecimal, T> {

  /**
   * 加算したインスタンスを返却します.
   *
   * @param other 計算するインスタンス
   * @return 計算後のインスタンス
   */
  @SuppressWarnings("unchecked")
  default T plus(T... other) {
    if (other.length == 1) {
      var result = this.getOrZero().add(other[0].getOrZero());
      return this.newInstance(result);
    }
    var result = Stream.of(other).map(T::getOrZero).reduce(this.getOrZero(), BigDecimal::add);

    return this.newInstance(result);
  }

  /**
   * 複数要素を加算します.
   *
   * <p>加算は複数情報を一括で処理したいケースがあるため、デフォルトでも準備しています.
   *
   * @param others 計算するインスタンスリスト
   * @return 計算後のインスタンス
   * @throws RuntimeException 引数インスタンスからクラス情報が取得出来ない場合
   */
  default T plusAll(Collection<T> others) {
    var result = others.stream().map(T::getOrZero).reduce(this.getOrZero(), BigDecimal::add);
    return this.newInstance(result);
  }
}
