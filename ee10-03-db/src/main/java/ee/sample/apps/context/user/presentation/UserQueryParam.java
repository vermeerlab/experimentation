package ee.sample.apps.context.user.presentation;

import ee.sample.apps.context.user.domain.Gender;
import jakarta.ws.rs.QueryParam;

/** UserQueryParam. */
@lombok.Data
public class UserQueryParam {
  @QueryParam("gender")
  private Gender gender;

  @QueryParam("name")
  private String name;
}
