package scan;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import ee.sample.spec.layer.application.CommandService;
import ee.sample.spec.layer.application.QueryService;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * ソースコードを解析.
 *
 * <p>実行資産に解析用ライブラリの依存が入らないようにするためテストとして実装します.
 */
public class CodeScanner {

  @Test
  /** コントローラーからの呼び出すクラスの構造を出力します. */
  void scan() {

    JavaClasses classes = new ClassFileImporter().importPackages("ee.sample");

    var scanedPairs =
        classes.stream()
            .filter(c -> c.isTopLevelClass())
            .filter(c -> c.getAllConstructors().isEmpty() == false)
            .filter(
                c -> {
                  return c.isAnnotatedWith(CommandService.class)
                      || c.isAnnotatedWith(QueryService.class);
                })
            .map(JavaClass::getMethodCallsToSelf)
            .flatMap(Set::stream)
            .filter(c -> c.getOwner().isMethod())
            .map(
                c -> {
                  var scanedPair =
                      new ScanedPair(
                          new ScanedItem(
                              c.getOriginOwner().getFullName(), c.getOrigin().getFullName()),
                          new ScanedItem(
                              c.getTargetOwner().getFullName(), c.getTarget().getFullName()));

                  return scanedPair;
                })
            .collect(Collectors.toSet());

    List<ScanedPair> scanedPairList = new ArrayList<>();

    scanedPairs.stream()
        .forEach(
            s -> {
              createJavaParser(s.getFrom().getClassPath())
                  .ifPresent(
                      unit -> {
                        var comment =
                            unit.getAllComments().stream()
                                .filter(c -> c.isJavadocComment())
                                .map(c -> c.getContent())
                                .findAny()
                                .orElseGet(() -> "");
                        s.from.setClassPathComment(comment);
                      });

              createJavaParser(s.getTo().getClassPath())
                  .ifPresent(
                      unit -> {
                        var comment =
                            unit.getAllComments().stream()
                                .filter(c -> c.isJavadocComment())
                                .map(c -> c.getContent())
                                .findAny()
                                .orElseGet(() -> "");
                        s.to.setClassPathComment(comment);
                      });

              var javaParserUnit = createJavaParser(s.getTo().getClassPath()).get();

              javaParserUnit
                  .findAll(MethodDeclaration.class)
                  .forEach(
                      m -> {
                        System.out.println("test::" + m);
                      });

              scanedPairList.add(s);
            });

    scanedPairList.forEach(System.out::println);

    // ルートクラスとしてコントローラのアノテーションを付与したクラスを抽出.
    // コントローラクラスから呼び出ししているクラスを末端まで取得
    // 取得結果をJSON形式で出力できるようにクラスに格納
    // クラスからJson文字列へ変換をしてファイル出力

  }

  static Optional<CompilationUnit> createJavaParser(String path) {
    try {
      var source = Paths.get("src/main/java/" + path.replace('.', '/') + ".java");
      var unit = StaticJavaParser.parse(source);
      return Optional.of(unit);
    } catch (IOException ex) {
      Logger.getLogger(CodeScanner.class.getName()).log(Level.SEVERE, null, ex);
    }
    return Optional.empty();
  }

  static class ScanedPair {
    private final ScanedItem from;
    private final ScanedItem to;

    public ScanedItem getFrom() {
      return from;
    }

    public ScanedItem getTo() {
      return to;
    }

    public ScanedPair(ScanedItem from, ScanedItem to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public String toString() {
      return "ScanedPair{" + "from=" + from + ", to=" + to + '}';
    }

    @Override
    public int hashCode() {
      int hash = 3;
      hash = 43 * hash + Objects.hashCode(this.from);
      hash = 43 * hash + Objects.hashCode(this.to);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final ScanedPair other = (ScanedPair) obj;
      if (!Objects.equals(this.from, other.from)) {
        return false;
      }
      return Objects.equals(this.to, other.to);
    }
  }

  static class ScanedItem {

    ScanedItem(String classPath, String method) {
      this.classPath = classPath;
      this.method = method;
    }

    private final String classPath;
    private final String method;
    private String classPathComment;

    public String getClassPath() {
      return classPath;
    }

    public String getMethod() {
      return method;
    }

    public String getClassPathComment() {
      return classPathComment;
    }

    public void setClassPathComment(String classPathComment) {
      this.classPathComment = classPathComment;
    }

    @Override
    public String toString() {
      return "{"
          + "classPath="
          + classPath
          + ", method="
          + method
          + ", classPathComment="
          + classPathComment
          + '}';
    }

    @Override
    public int hashCode() {
      int hash = 3;
      hash = 29 * hash + Objects.hashCode(this.classPath);
      hash = 29 * hash + Objects.hashCode(this.method);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final ScanedItem other = (ScanedItem) obj;
      if (!Objects.equals(this.classPath, other.classPath)) {
        return false;
      }
      return Objects.equals(this.method, other.method);
    }
  }
}