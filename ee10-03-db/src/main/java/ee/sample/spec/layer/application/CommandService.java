package ee.sample.spec.layer.application;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * リソースの更新を行うサービスへ付与するアノテーションです.
 */
@ApplicationScoped
//@Transactional(rollbackOn = {Exception.class})
public @interface CommandService {

}
