package ee.sample.utils.json;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JsonUtilTest {

  public JsonUtilTest() {}

  @Test
  public void testReadFromResource() {

    Optional<TestJson> testJson = JsonUtil.readFromResource("json/testjson.json", TestJson.class);

    assertEquals("item1_value", testJson.get().getItem1());
    assertEquals("item2_value", testJson.get().getItem2());
  }

  @Test
  public void testReadFromResourceNotFound() {

    Optional<TestJson> testJson =
        JsonUtil.readFromResource("json/testjson_not_found.json", TestJson.class);

    assertEquals(Optional.empty(), testJson);
  }

  @Test
  public void testReadFromResourceParseError() {

    Optional<TestJson> testJson =
        JsonUtil.read(
            "{\n" + "\"item1\": \"item1_value\",\n" + "\"item2\": \"item2_value\"\n" + "",
            TestJson.class);

    assertEquals(Optional.empty(), testJson);
  }

  private static class TestJson {
    String item1;
    String item2;

    public TestJson() {}

    public String getItem1() {
      return item1;
    }

    public void setItem1(String item1) {
      this.item1 = item1;
    }

    public String getItem2() {
      return item2;
    }

    public void setItem2(String item2) {
      this.item2 = item2;
    }
  }
}