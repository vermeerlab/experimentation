package ee.sample.apps.context.user.presentation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import ee.sample.apps.common.presentation.openapi.BaseResponseBody;
import ee.sample.apps.common.presentation.openapi.ResponseBody;
import ee.sample.apps.context.user.domain.User;
import ee.sample.apps.context.user.domain.Users;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/** UserResponse. */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
@lombok.NoArgsConstructor
@Schema
public class UserResponse extends UserRequest {

  @Schema(title = "ユーザーID", example = "uuid", readOnly = true)
  private String id;

  static UserResponse from(User user) {
    var res = new UserResponse();
    res.setGender(user.getGender());
    res.setId(user.getUserId().getValue());
    res.setName(user.getName().getOrDefault());

    return res;
  }

  static List<UserResponse> from(Users users) {
    var resList =
        users.getItems().stream()
            .map(UserResponse::from)
            .sorted(Comparator.comparing(res -> res.getId()))
            .collect(Collectors.toList());
    return resList;
  }

  static class UserResponseBody extends ResponseBody<UserResponse> {}

  static class UserResponseListBody extends BaseResponseBody {
    @SuppressFBWarnings("UUF_UNUSED_FIELD")
    private List<UserResponse> body;
  }
}
