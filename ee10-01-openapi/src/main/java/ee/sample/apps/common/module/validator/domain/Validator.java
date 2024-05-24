package ee.sample.apps.common.module.validator.domain;

import ee.sample.apps.common.module.message.domain.ErrorMessageCode;
import ee.sample.apps.common.module.message.domain.Message;
import java.util.Objects;
import java.util.Optional;

/**
 * 検証を行うUtil.
 *
 * @author Yamashita Takahiro
 */
public class Validator {

  /**
   * Nullでないことを検証（空文字はOK）.
   *
   * @param value 検証対象の値
   * @param label 検証対象の名称
   * @return 検証結果
   */
  public static Optional<ValidatedResult> notNull(Object value, String label) {
    return Objects.isNull(value)
        ? Optional.of(ValidatedResult.of(Message.of(ErrorMessageCode.NOT_NUll, label)))
        : Optional.empty();
  }
}
