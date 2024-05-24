package ee.sample.apps.common.module.message.application;

import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.module.message.domain.MessageCodeType;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

/**
 * メッセージコードからメッセージへの変換をします.
 *
 * <p>DBやPropertiesを使用しない簡易的な実装です.
 */
@ApplicationScoped
public class MessageService {

  private final Map<MessageCodeType, String> messageMap =
      Map.ofEntries(
          Map.entry(ErrorMessageCode.HTTP_STATUS_BAD_REQUEST, "構文が無効です"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_UNAUTHORIZED, "未認証です"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_FORBIDDEN, "アクセス権がありません"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_NOT_FOUND, "対象が存在しません"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_METHOD_NOT_ALLOWED, "Httpメソッドが無効です"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_NOT_ACCEPTABLE, "指定のデータ形式は提供していません"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_UNSUPPORTED_MEDIA_TYPE, "指定のメディア形式は提供していません"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_INTERNAL_SERVER_ERROR, "システム内部エラー"),
          Map.entry(ErrorMessageCode.HTTP_STATUS_SERVICE_UNAVAILABLE, "一時的に利用できません"));

  /**
   * エラーメッセージコードからメッセージ文言を取得します.
   *
   * @param errorMessageCode エラーメッセージコード
   * @return 取得結果
   */
  public String findByErrorMessageCode(ErrorMessageCode errorMessageCode) {
    return this.messageMap.getOrDefault(errorMessageCode, "not yet set message code");
  }
}
