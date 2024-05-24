package org.vermeerlab.base.domain.type.collection;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ファーストクラスの基底型となるインターフェース.
 *
 * @author Yamashita.Takahiro
 * @param <T> リストの要素の型
 */
public interface FirstClassCollectionType<T> {

  /**
   * 保持しているリストを返却します.
   *
   * <p>ファーストクラスコレクション内で使用するようにしてください.
   *
   * @return インスタンス変数
   */
  List<T> getValues();

  /**
   * リストが空であることを判定します.
   *
   * @return 空リストの場合はtrue
   */
  default boolean isEmpty() {
    return this.getValues().isEmpty();
  }

  /**
   * 保持しているリストの件数を返却します.
   *
   * @return 保持しているリストの件数
   */
  default int size() {
    return this.getValues().size();
  }

  /**
   * 要素の変換をしたリストを返却します.
   *
   * <p>ドメインオブジェクトからDTOへの変換に使用することを想定したもののため、更にファーストクラスコレクションにしたい場合は
   * 変換後のリストをコンストラクタ（またはFactoryメソッド）の引数として指定してください.
   *
   * @param <R> 変換後のリスト要素の型
   * @param function リストの要素を変換する関数
   * @return 要素を変換したリスト
   */
  default <R> List<R> apply(Function<T, R> function) {
    return this.getValues().stream().map(function::apply).collect(Collectors.toUnmodifiableList());
  }
}
