package ee.sample.apps.context.user.presentation;

import ee.sample.apps.common.presentation.openapi.ResponseBody;
import ee.sample.apps.context.user.domain.UserId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/** UserResourceId. */
@lombok.Data
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@Schema
public class UserResourceId {

  @Schema(title = "新規ユーザーID", example = "UUID")
  private String id;

  static UserResourceId from(UserId userId) {
    var res = new UserResourceId();
    res.setId(userId.getValue());
    return res;
  }

  static class UserResponseIdBody extends ResponseBody<UserResourceId> {}
}
