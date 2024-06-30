package plugin.archunit;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.CacheMode;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import ee.sample.spec.layer.presentation.EntryPoint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(
    packages = ArchUnitConfig.PROJECT_ROOT_PACKAGE,
    cacheMode = CacheMode.PER_CLASS,
    importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

  @ArchTest
  protected static final ArchRule 循環参照検証 = slices().matching("..(**)").should().beFreeOfCycles();

  @ArchTest
  protected static final ArchRule レイヤー依存検証 =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Presentation")
          .definedBy("..(**).presentation..")
          .layer("Application")
          .definedBy("..(**).application..")
          .layer("Infrastructure")
          .definedBy("..(**).infrastructure..")
          .layer("Domain")
          .definedBy("..(**).domain..")
          .layer("Adaptor")
          .definedBy("..(**).adaptor..")
          .whereLayer("Presentation")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Application")
          .mayOnlyBeAccessedByLayers("Presentation")
          .whereLayer("Adaptor")
          .mayOnlyBeAccessedByLayers("Application", "Infrastructure")
          .whereLayer("Infrastructure")
          .mayNotBeAccessedByAnyLayer();

  /** 下位階層パッケージのクラスが上位階層パッケージに直接依存することを禁止します. */
  @ArchTest
  protected static final ArchRule no_accesses_to_upper_package =
      NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

  /**
   * クラスから汎用例外をスローすることを禁止します。たとえば、RuntimeExceptionをスローする代わりに、IllegalArgumentExceptionのようなカスタム例外を使ってください
   */
  @ArchTest
  protected static final ArchRule no_generic_exceptions =
      NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  protected static final ArchRule presentationRule_entry_point_class_end_with =
      classes()
          .that()
          .areAnnotatedWith(EntryPoint.class)
          .should()
          .haveSimpleNameEndingWith("Controller")
          .because("エントリーポイントのクラス名の末尾文字が正しくありません.");

  @ArchTest
  protected static final ArchRule presentationRule_entry_point_be_annotated =
      classes()
          .that()
          .haveSimpleNameEndingWith("Controller")
          .should()
          .beAnnotatedWith(EntryPoint.class)
          .because("クラス名がアクセスポイントの接尾文字になっているのにエントリーポイントへ付与すべきアノテーションが設定されていません.");

  /**
   * 外部との接点となるDTOではBuilderパターンは使用しないでください
   *
   * <p>リソースクラスはJavaBeans仕様に則ったものにするためです.
   * 多くのフレームワークにおいてJacksonなどを使用する場合、JavaBeans仕様に則っていることが前提になっていることが多いです.<br>
   * テストライブラリなどでリソースクラスを使用するときに後から見直すことが無いように事前に制約を設けています.<br>
   * default constructorを許容するために fieldをfinalにしないことを検証しています（lombok.Valueの検証を間接的に行っています）<br>
   */
  @ArchTest
  protected static final ArchRule presentationRule_resource_field_not_final =
      fields()
          .that()
          .areDeclaredInClassesThat()
          .resideInAnyPackage("..presentation..")
          .and()
          .areDeclaredInClassesThat()
          .resideOutsideOfPackage("..plugin..")
          .and()
          .areDeclaredInClassesThat()
          .areNotAnnotatedWith(EntryPoint.class)
          .and()
          .areAnnotatedWith(Schema.class)
          .should()
          .notBeFinal()
          .because(
              "Presentationのリソースを扱うクラス（エントリーポイントとなるクラス以外）では fieldをfinalにする lombok.Value を使用しないでください（詳細の理由はテストJavaDocを参照）.");

  /**
   * jakarta.ws.rs.GET のメソッド名は get から始めてください.
   *
   * <p>typescript-generatorのアクセスメソッドとしてHttpMethodを同じにするため使用感の統一を図っています.
   */
  @ArchTest
  protected static final ArchRule presentationRule_method_name_start_get =
      methods()
          .that()
          .areAnnotatedWith(jakarta.ws.rs.GET.class)
          .should()
          .haveNameStartingWith("get")
          .because("jakarta.ws.rs.GET をアノテーションしているメソッド名は get にしてください（詳細の理由はテストJavaDocを参照）");

  /**
   * jakarta.ws.rs.POST のメソッド名は post から始めてください.
   *
   * <p>typescript-generatorのアクセスメソッドとしてHttpMethodを同じにするため使用感の統一を図っています.
   */
  @ArchTest
  protected static final ArchRule presentationRule_method_name_start_post =
      methods()
          .that()
          .areAnnotatedWith(jakarta.ws.rs.POST.class)
          .should()
          .haveNameStartingWith("post")
          .because("jakarta.ws.rs.POST をアノテーションしているメソッド名は post にしてください（詳細の理由はテストJavaDocを参照）");

  /**
   * jakarta.ws.rs.PUT のメソッド名は put から始めてください.
   *
   * <p>typescript-generatorのアクセスメソッドとしてHttpMethodを同じにするため使用感の統一を図っています.
   */
  @ArchTest
  protected static final ArchRule presentationRule_method_name_start_put =
      methods()
          .that()
          .areAnnotatedWith(jakarta.ws.rs.PUT.class)
          .should()
          .haveNameStartingWith("put")
          .because("jakarta.ws.rs.PUT をアノテーションしているメソッド名は put にしてください（詳細の理由はテストJavaDocを参照）");

  /**
   * jakarta.ws.rs.DELETE のメソッド名は delete から始めてください.
   *
   * <p>typescript-generatorのアクセスメソッドとしてHttpMethodを同じにするため使用感の統一を図っています.
   */
  @ArchTest
  protected static final ArchRule presentationRule_method_name_start_delete =
      methods()
          .that()
          .areAnnotatedWith(jakarta.ws.rs.DELETE.class)
          .should()
          .haveNameStartingWith("delete")
          .because("jakarta.ws.rs.DELETE をアノテーションしているメソッド名は delete にしてください（詳細の理由はテストJavaDocを参照）");

  @ArchTest
  protected static final ArchRule AdaptorRule =
      classes().that().resideInAnyPackage("..adaptor..").should().beInterfaces();

  @ArchTest
  protected static final ArchRule DomainRule =
      classes()
          .that()
          .resideInAnyPackage("..domain..")
          .should()
          .onlyDependOnClassesThat(
              resideInAnyPackage(
                  "..(**).domain..", "java..", "edu.umd.cs.findbugs.annotations..", "lombok.."))
          .because("DomainクラスはDomainおよびJava言語仕様以外の依存は許容しません.");

  /**
   * REST APIのメソッド名の重複検証.
   *
   * <p>OpenAPIのOperationIdとしてメソッド名を使用するため重複をしていないことを検証します
   */
  @Test
  public void REST_APIのメソッド名の重複検証() {
    JavaClasses javaClasses =
        new ClassFileImporter().importPackages(ArchUnitConfig.PROJECT_ROOT_PACKAGE);

    var entryPointClasses =
        javaClasses.stream()
            .filter(clazz -> clazz.getPackageName().contains("presentation"))
            .filter(clazz -> clazz.isMetaAnnotatedWith(EntryPoint.class))
            .collect(Collectors.toList());

    var restApiMethodNameList =
        entryPointClasses.stream()
            .map(clazz -> clazz.getMethods())
            .flatMap(method -> method.stream())
            .map(method -> method.getName())
            .collect(Collectors.toList());

    var restApiMethodNameSet = new HashSet<>(restApiMethodNameList);

    if (restApiMethodNameList.size() == restApiMethodNameSet.size()) {
      return;
    }

    var restApiMethodNameGroupMap =
        restApiMethodNameList.stream().collect(Collectors.groupingBy(String::toString));

    var duplicateMethodNameLSet =
        restApiMethodNameGroupMap.entrySet().stream()
            .filter(entry -> (1 < entry.getValue().size()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());

    var javaMethodEntrySet =
        entryPointClasses.stream()
            .filter(
                clazz -> {
                  var anyMatch =
                      clazz.getMethods().stream()
                          .map(JavaMethod::getName)
                          .anyMatch(method -> duplicateMethodNameLSet.contains(method));
                  return anyMatch;
                })
            .map(
                clazz -> {
                  var javaMethod =
                      clazz.getMethods().stream()
                          .filter(method -> duplicateMethodNameLSet.contains(method.getName()))
                          .findAny()
                          .get();
                  return javaMethod;
                })
            .collect(Collectors.groupingBy(JavaMethod::getName));

    var resultMap = new HashMap<String, List<String>>();
    javaMethodEntrySet
        .entrySet()
        .forEach(
            javaMethod -> {
              var list =
                  javaMethod.getValue().stream()
                      .map(JavaMethod::getFullName)
                      .collect(Collectors.toList());
              resultMap.put("duplicateMethodName:" + javaMethod.getKey(), list);
            });

    fail("メソッド名が重複しているので見直してください." + resultMap.toString());
  }
}
