package ee.sample.apps.context.user.domain;

import ee.sample.apps.common.domain.part.text.Text;

/** ユーザー. */
@lombok.Builder
@lombok.Getter
@lombok.EqualsAndHashCode
@lombok.ToString
public class User {

  private final UserId userId;
  private final Text name;
  private final Gender gender;

  public User updatedUserId(UserId userId) {
    var builder = this.copy().userId(userId);
    return builder.build();
  }

  UserBuilder copy() {

    var builder = User.builder().userId(this.userId).name(this.name).gender(this.gender);

    return builder;
  }

  /** for JavaDoc. */
  public static class UserBuilder {}
}
