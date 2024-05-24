package org.vermeerlab.base.domain.type.text.behavior;

import org.vermeerlab.base.domain.type.object.NoArgumentNewInstance;
import org.vermeerlab.base.domain.type.object.SingleArgumentNewInstance;
import org.vermeerlab.base.domain.type.text.NullableTextType;

/**
 * 文字列のキャリッジリターン除去.
 *
 * @author Yamashita.Takahiro
 * @param <T> 本インターフェースを実装した具象クラスの型
 */
public interface TextRemoveReturn<T>
    extends NullableTextType<T>, NoArgumentNewInstance<T>, SingleArgumentNewInstance<String, T> {

  /**
   * キャリッジリターンを除去したインスタンスを返却します.
   *
   * @return キャリッジリターンを除去した文字インスタンス
   */
  default T removeReturn() {
    if (this.getNullableValue().isEmpty()) {
      return this.newInstance();
    }

    var replaced = this.getNullableValue().orElse("").replaceAll("\\r\\n|\\n", "");
    return this.newInstance(replaced);
  }
}
