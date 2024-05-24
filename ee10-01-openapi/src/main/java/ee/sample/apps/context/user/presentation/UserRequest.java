package ee.sample.apps.context.user.presentation;

import ee.sample.apps.common.domain.part.text.Text;
import ee.sample.apps.context.user.domain.Gender;
import ee.sample.apps.context.user.domain.User;
import ee.sample.apps.context.user.domain.UserId;
import java.util.Objects;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/** UserRequest. */
@lombok.Data
@lombok.NoArgsConstructor
@Schema
public class UserRequest {

  @Schema(
      title = "性別",
      example = "OTHER",
      enumeration = {"MALE", "FEMALE", "OTHER"},
      required = true)
  private String gender;

  @Schema(title = "ユーザー名", example = "User Name", required = true)
  private String name;

  User toModel() {
    return this.toModel(null);
  }

  User toModel(String userId) {
    var model =
        User.builder()
            .userId(Objects.isNull(userId) ? null : UserId.of(userId))
            .gender(Gender.valueOf(this.gender))
            .name(Text.of(name))
            .build();
    return model;
  }
}
