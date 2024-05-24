package org.vermeerlab.base.domain.type.object;

/**
 * 引数１つでインスタンス生成をします.
 *
 * @author Yamashita.Takahiro
 * @param <T> 生成するインスタンスのプロパティの型
 * @param <R> 生成するインスタンスの型
 */
public interface SingleArgumentNewInstance<T, R> {

  /**
   * 引数ありでインスタンスを生成します.
   *
   * @param value インスタンスのプロパティ
   * @return 生成したインスタンス
   */
  R newInstance(T value);
}
