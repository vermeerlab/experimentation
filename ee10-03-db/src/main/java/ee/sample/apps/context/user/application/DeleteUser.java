package ee.sample.apps.context.user.application;

import ee.sample.apps.context.user.adaptor.repository.UserRepository;
import ee.sample.apps.context.user.domain.UserId;
import ee.sample.spec.layer.application.CommandService;
import jakarta.inject.Inject;

/** DeleteUser. */
@CommandService
public class DeleteUser {

  @Inject UserRepository userRepository;

  /**
   * 実行します.
   *
   * @param userId ユーザーID
   */
  public void invoke(UserId userId) {
    userRepository.delete(userId);
  }
}
