package org.vermeerlab.base.domain.type.object;

/**
 * 引数なしでインスタンス生成をします.
 *
 * @author Yamashita.Takahiro
 * @param <R> 生成するインスタンスの型
 */
public interface NoArgumentNewInstance<R> {

  /**
   * 引数なしでインスタンスを生成します.
   *
   * @return 生成したインスタンス
   */
  public R newInstance();
}
