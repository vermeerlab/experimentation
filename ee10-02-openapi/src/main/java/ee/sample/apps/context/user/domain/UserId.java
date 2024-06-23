package ee.sample.apps.context.user.domain;

import ee.sample.apps.common.module.validator.domain.ValidatedResults;
import ee.sample.apps.common.module.validator.domain.Validator;
import java.util.Objects;
import org.vermeerlab.base.domain.type.object.NotEmptyType;

/** ユーザーID. */
@lombok.Value(staticConstructor = "of")
public class UserId implements NotEmptyType<String> {

  private static final String LABEL = "ユーザーID";
  private final String value;

  public static ValidatedResults validate(String value) {
    return ValidatedResults.of(Validator.notNull(value, LABEL).orElse(null));
  }

  public boolean isNull() {
    return Objects.isNull(this.value);
  }
}
