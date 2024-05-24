package ee.sample.apps.common.application;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

/**
 * CreateId.
 * <p>
 * ID生成器をテストなどで置き換えられるようにDI管理対象としています.
 * </p>
 */
@ApplicationScoped
public class CreateId {

  public String invoke() {
    var uuid = UUID.randomUUID();
    return uuid.toString();
  }

}
