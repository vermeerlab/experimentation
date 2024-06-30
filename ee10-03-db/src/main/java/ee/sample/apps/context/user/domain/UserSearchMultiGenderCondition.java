package ee.sample.apps.context.user.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/** UserSearchCondition. */
@lombok.Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
public class UserSearchMultiGenderCondition {

  private final Set<Gender> genders;

  private final String name;

  private UserSearchMultiGenderCondition(List<Gender> genders, Gender gender, String name) {

    var genderSet = genders.stream().collect(Collectors.toSet());
    genderSet.add(gender);
    this.genders = genderSet;
    this.name = name;
  }

  public static UserSearchMultiGenderCondition of(
      List<Gender> genders, Gender gender, String name) {
    return new UserSearchMultiGenderCondition(genders, gender, name);
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }
}
