package plugin.archunit;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import java.lang.annotation.Annotation;

/** ArchUnitUtil. */
public class ArchUnitUtil {

  /**
   * 指定のアノテーションを使用することはできません.
   *
   * @param annotationType 使用不可としたいアノテーション
   * @return 検証結果
   */
  public static ArchCondition<JavaMethod> notHaveParametersAnnotatedWith(
      Class<? extends Annotation> annotationType) {

    var archCondition =
        new ArchCondition<JavaMethod>(
            "not have parameters annotated with @" + annotationType.getSimpleName()) {
          @Override
          public void check(JavaMethod method, ConditionEvents events) {
            method.getParameters().stream()
                .filter(parameter -> parameter.isAnnotatedWith(annotationType))
                .forEach(
                    parameter -> {
                      events.add(
                          SimpleConditionEvent.violated(
                              method,
                              parameter.getDescription()
                                  + " is annotated with @"
                                  + annotationType.getSimpleName()));
                    });
          }
        };
    return archCondition;
  }

  /**
   * 指定のアノテーションを必ず使用してください.
   *
   * @param annotationType 使用不可としたいアノテーション
   * @return 検証結果
   */
  public static ArchCondition<JavaMethod> haveParametersAnnotatedWith(
      Class<? extends Annotation> annotationType) {

    var archCondition =
        new ArchCondition<JavaMethod>(
            "have parameters annotated with @" + annotationType.getSimpleName()) {
          @Override
          public void check(JavaMethod method, ConditionEvents events) {
            method.getParameters().stream()
                .filter(parameter -> parameter.isAnnotatedWith(annotationType) == false)
                .forEach(
                    parameter -> {
                      events.add(
                          SimpleConditionEvent.violated(
                              method,
                              parameter.getDescription()
                                  + " is not annotated with @"
                                  + annotationType.getSimpleName()));
                    });
          }
        };
    return archCondition;
  }
}
