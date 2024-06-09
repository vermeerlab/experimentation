package ee.sample.spec.layer.application;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * リソースの参照を行うサービスへ付与するアノテーションです.
 * <p>
 * マルチテナントをする場合には<code>jakarta.transaction.Transactional</code>を付与してください.
 * </p>
 */
@ApplicationScoped
public @interface QueryService {

}
