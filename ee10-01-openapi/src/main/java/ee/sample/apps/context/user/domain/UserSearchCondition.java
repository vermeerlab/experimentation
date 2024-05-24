package ee.sample.apps.context.user.domain;

import java.util.Optional;

/** UserSearchCondition. */
public class UserSearchCondition {

  private final Gender gender;

  private final String name;

  public UserSearchCondition(Gender gender, String name) {
    this.gender = gender;
    this.name = name;
  }

  public static UserSearchCondition of(Gender gender, String name) {
    return new UserSearchCondition(gender, name);
  }

  public Optional<Gender> getGender() {
    return Optional.ofNullable(gender);
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }
}
