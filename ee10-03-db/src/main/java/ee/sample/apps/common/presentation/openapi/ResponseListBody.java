package ee.sample.apps.common.presentation.openapi;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * ResponseListBody.
 *
 * <p>レスポンス結果の成否とボディ（リスト構造）を共通して扱うインターフェースでラップするためのクラスです.
 *
 * <p>フロント側がステータスコードにより判定をすることも可能ですが、成否プロパティを設けることで判定をやりやすくします
 *
 * @param <T> レスポンスボディの型
 */
@Schema
public class ResponseListBody<T> extends BaseResponseBody {

  @Schema(title = "レスポンスボディ", description = "レスポンス毎の独自の型のオブジェクト", readOnly = true)
  private List<T> body;

  public ResponseListBody() {}

  @SuppressFBWarnings("EI_EXPOSE_REP2")
  public ResponseListBody(boolean ok, List<T> body) {
    super.ok = ok;
    this.body = body;
  }

  @SuppressFBWarnings("EI_EXPOSE_REP")
  public List<T> getBody() {
    return this.body;
  }

  @SuppressFBWarnings("EI_EXPOSE_REP2")
  public void setBody(List<T> body) {
    this.body = (List<T>) body;
  }
}
