package ee.sample.spec.layer.presentation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * APIのエントリーポイントになるクラスに付与してください.
 * <p>
 * Interceptorによるhookをするために使用することを想定しています.
 * </p>
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@InterceptorBinding
@ApplicationScoped
public @interface EntryPoint {

}
