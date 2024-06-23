package ee.sample.apps.context.user.application;

import ee.sample.apps.context.user.adaptor.repository.UserRepository;
import ee.sample.apps.context.user.domain.User;
import ee.sample.spec.layer.application.CommandService;
import jakarta.inject.Inject;

/** UpdateUser. */
@CommandService
public class UpdateUser {

  @Inject UserRepository userRepository;

  /**
   * 実行します.
   *
   * @param user ユーザー情報
   */
  public void invoke(User user) {
    userRepository.update(user);
  }
}
