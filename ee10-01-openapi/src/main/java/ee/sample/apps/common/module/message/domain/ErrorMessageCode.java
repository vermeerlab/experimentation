package ee.sample.apps.common.module.message.domain;

/** 検証エラーメッセージ. */
public enum ErrorMessageCode implements MessageCodeType {
  HTTP_STATUS_BAD_REQUEST,
  HTTP_STATUS_UNAUTHORIZED,
  HTTP_STATUS_FORBIDDEN,
  HTTP_STATUS_NOT_FOUND,
  HTTP_STATUS_METHOD_NOT_ALLOWED,
  HTTP_STATUS_NOT_ACCEPTABLE,
  HTTP_STATUS_UNSUPPORTED_MEDIA_TYPE,
  HTTP_STATUS_INTERNAL_SERVER_ERROR,
  HTTP_STATUS_SERVICE_UNAVAILABLE,

  NOT_NUll,
  ;

  @Override
  public String getCode() {
    return this.name();
  }
}
