package ee.sample.apps.context.user.infrastructure.datastore;

import ee.sample.apps.common.domain.part.text.Text;
import ee.sample.apps.context.user.adaptor.repository.UserRepository;
import ee.sample.apps.context.user.domain.Gender;
import ee.sample.apps.context.user.domain.User;
import ee.sample.apps.context.user.domain.UserId;
import ee.sample.apps.context.user.domain.UserSearchCondition;
import ee.sample.apps.context.user.domain.UserSearchMultiGenderCondition;
import ee.sample.apps.context.user.domain.Users;
import ee.sample.spec.layer.infrastructure.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** UserDataStore. */
@Repository
public class UserDataStore implements UserRepository {

  // DBなしで簡易なデータとして
  private List<User> users =
      List.of(
          User.builder()
              .gender(Gender.MALE)
              .userId(UserId.of("1"))
              .name(Text.of("Name:name-1"))
              .build(),
          User.builder()
              .gender(Gender.FEMALE)
              .userId(UserId.of("2"))
              .name(Text.of("Name:name-2"))
              .build(),
          User.builder()
              .gender(Gender.OTHER)
              .userId(UserId.of("3"))
              .name(Text.of("Name:name-3"))
              .build());

  @Override
  public Optional<User> findById(UserId userId) {

    var filtered = users.stream().filter(e -> e.getUserId().equals(userId)).findAny();
    return filtered;
  }

  @Override
  public Users findByCondition(UserSearchCondition condition) {

    var filterd =
        users.stream()
            .filter(
                e -> {
                  if (condition.getGender().isEmpty()) {
                    return true;
                  } else {
                    return condition.getGender().get() == e.getGender();
                  }
                })
            .filter(
                e -> {
                  if (condition.getName().isEmpty()) {
                    return true;
                  } else {
                    return condition.getName().get().equals(e.getName().getOrDefault());
                  }
                })
            .collect(Collectors.toList());

    return Users.of(filterd);
  }

  @Override
  public Users findByCondition(UserSearchMultiGenderCondition condition) {
    var filterd =
        users.stream()
            .filter(
                e -> {
                  if (condition.getGenders().isEmpty()) {
                    return true;
                  } else {
                    return condition.getGenders().contains(e.getGender());
                  }
                })
            .filter(
                e -> {
                  if (condition.getName().isEmpty()) {
                    return true;
                  } else {
                    return condition.getName().get().equals(e.getName().getOrDefault());
                  }
                })
            .collect(Collectors.toList());

    return Users.of(filterd);
  }

  @Override
  public Users findAll() {
    return Users.of(users);
  }

  @Override
  public User persist(User user) {
    return user;
  }

  @Override
  public void update(User user) {
    System.out.println("user updated !!::" + user.toString());
  }

  @Override
  public void delete(UserId userId) {
    System.out.println("user deleted !!::" + userId.toString());
  }
}
