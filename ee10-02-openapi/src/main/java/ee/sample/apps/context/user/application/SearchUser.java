package ee.sample.apps.context.user.application;

import ee.sample.apps.context.user.adaptor.repository.UserRepository;
import ee.sample.apps.context.user.domain.User;
import ee.sample.apps.context.user.domain.UserId;
import ee.sample.apps.context.user.domain.UserSearchCondition;
import ee.sample.apps.context.user.domain.UserSearchMultiGenderCondition;
import ee.sample.apps.context.user.domain.Users;
import ee.sample.spec.layer.application.QueryService;
import jakarta.inject.Inject;
import java.util.Optional;

/** ユーザーを検索します. */
@QueryService
public class SearchUser {

  @Inject UserRepository userRepository;

  public Optional<User> findById(UserId userId) {
    return userRepository.findById(userId);
  }

  public Users findByCondition(UserSearchCondition condition) {
    return userRepository.findByCondition(condition);
  }

  public Users findByCondition(UserSearchMultiGenderCondition condition) {
    return userRepository.findByCondition(condition);
  }

  public Users findAll() {
    return userRepository.findAll();
  }
}
