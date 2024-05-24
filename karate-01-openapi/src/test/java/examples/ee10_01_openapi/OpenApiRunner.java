package examples.ee10_01_openapi;

import com.intuit.karate.junit5.Karate;

class OpenApiRunner {

  @Karate.Test
  Karate testOpenApi() {
    return Karate.run("ee10_01_openapi").relativeTo(getClass());
  }
}
