package ee.sample.apps.context.user.adaptor.repository;

import ee.sample.apps.context.user.domain.User;
import ee.sample.apps.context.user.domain.UserId;
import ee.sample.apps.context.user.domain.UserSearchCondition;
import ee.sample.apps.context.user.domain.UserSearchMultiGenderCondition;
import ee.sample.apps.context.user.domain.Users;
import java.util.Optional;

/** UserRepository. */
public interface UserRepository {

  public Optional<User> findById(UserId userId);

  public Users findByCondition(UserSearchCondition condition);

  public Users findByCondition(UserSearchMultiGenderCondition condition);

  public Users findAll();

  public User persist(User user);

  public void update(User user);

  public void delete(UserId userId);
}
