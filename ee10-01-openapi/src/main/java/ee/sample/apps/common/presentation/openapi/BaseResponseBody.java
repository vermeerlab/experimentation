package ee.sample.apps.common.presentation.openapi;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/** BaseResponseBody. */
@Schema
public class BaseResponseBody {
  @Schema(title = "レスポンス成否", description = "正常の場合はtrue", readOnly = true)
  boolean ok;

  public boolean isOk() {
    return ok;
  }

  public void setOk(boolean ok) {
    this.ok = ok;
  }
}
