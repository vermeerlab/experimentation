package ee.sample.apps.context.user.application;

import ee.sample.apps.common.application.CreateId;
import ee.sample.apps.context.user.adaptor.repository.UserRepository;
import ee.sample.apps.context.user.domain.User;
import ee.sample.apps.context.user.domain.UserId;
import ee.sample.spec.layer.application.CommandService;
import jakarta.inject.Inject;

/** PersistUser. */
@CommandService
public class PersistUser {

  @Inject UserRepository userRepository;

  @Inject CreateId createId;

  /**
   * 実行します.
   *
   * @param user ユーザー情報
   * @return 登録したユーザー情報
   */
  public User invoke(User user) {
    var persistUser = user.updatedUserId(UserId.of(createId.invoke()));
    var persisted = userRepository.persist(persistUser);
    return persisted;
  }
}
