package ee.sample.apps.common.presentation.openapi;

import java.util.Optional;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * ResponseBody.
 *
 * <p>レスポンス結果の成否とボディを共通して扱うインターフェースでラップするためのクラスです.
 *
 * <p>フロント側がステータスコードにより判定をすることも可能ですが、成否プロパティを設けることで判定をやりやすくします
 *
 * @param <T> レスポンスボディの型
 */
@Schema
public class ResponseBody<T> extends BaseResponseBody {

  @Schema(title = "レスポンスボディ", description = "レスポンス毎の独自の型のオブジェクト", readOnly = true)
  private T body;

  public ResponseBody() {}

  public ResponseBody(boolean ok, T body) {
    super.ok = ok;
    this.body = body;
  }

  public Optional<T> getBody() {
    return Optional.ofNullable(body);
  }

  public void setBody(T body) {
    this.body = body;
  }
}
