package ee.sample.apps.context.user.domain;

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;

/**
 * ユーザー（集約）.
 */
@lombok.AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Users {

  private final List<User> items;

  public static Users of(List<User> items) {
    return new Users(items);
  }

  public List<User> getItems() {
    return Collections.unmodifiableList(items);
  }

}
