# ee10-01-openapi

Payara（Jakarta EE10（もしくはMicroprofile 6））で OpenAPIを使った実装


## Run

Cargoを使ってEEサーバを準備しなくても取りあえず動かす

関連する資産のビルドも必要なので初回は、プロジェクトのルートにある `expreimentation`プロジェクトでも以下のコマンドを実行すること.  

```
./mvnw package
```

```
./mvnw cargo:run -Ppayara
```

OpenAPI-UIでWebAPIを使う

```
http://localhost:8081/ee10-01-openapi/api/openapi-ui/index.html
```

## OpenAPI

### 拡張ポイント

#### プロジェクト全体の定義

`OpenApiModelReader`（OASModelReaderの実装）に

- Lisence
- ドキュメント
- セキュリティ（トークンの設定）

などのプロジェクトの基本情報を設定します.  

#### オブジェクトのSchemaを追加

`OpenApiSchema`に変換をしたいクラスと `ref`で使用するキー値を登録します.

```java
  public static final String Gender = prifixPath + "Gender";

```
キー値のペアとなる、Schema生成関数も登録します.

```java
  // openApiのUtilは実装依存となるため実行時に決定するように関数で指定します.
  private static final Map<String, Supplier<Schema>> schemaMap =
      Map.ofEntries(
          entry(Gender, () -> OpenApiSchemaUtil.createEnumSchema(Gender.class)),
          entry(Genders, () -> OpenApiSchemaUtil.createEnumListSchema(Gender.class)),
          entry(UploadFile, () -> OpenApiSchemaUtil.createUploadFileSchema()),
          entry(UploadFiles, () -> OpenApiSchemaUtil.createUploadFileListSchema()));
```

実際に生成するのは `OpenApiModelReader#buildModel`内で`OpenApiSchema.appendSchema(components);`で行います.

登録したキー値は`@Schema`の`ref`として指定をします.

```java
  @Parameters({
    @Parameter(name = "gender", description = "性別", schema = @Schema(ref = OpenApiSchema.Gender)),
    @Parameter(name = "name", description = "ユーザー名", example = "user name")
  })
  public Response getUsersByQuery(
      @QueryParam("gender") Gender gender, @QueryParam("name") String name) {
```

#### @ExampleObjectにjsonを指定

`@ExampleObject`の`value`にjsonを記述をすることができますが、resource配下のjsonファイルを指定することはできません.  
本来、`externalValue`は`http`を使用して外部リソースを参照するものですが、あまり使用することは無いと考え、`externalValue`にリソースパスを指定して読み込めるようにしました.

```java
externalValue = "openapi/user/get_response_default.json"
```

```
src/main/resources/openapi/user/get_response_default.json
```

内部的には、`externalValue`のjsonを展開した結果を`value`へ転記して、OpenAPI.yamlとして、そのまま使えるようにしています.

#### モックとしてjsonを使用する

開発初期では、インターフェースとしてのOpenAPIの定義設定にあわせて アプリケーションサーバーをモックサーバとして使用したいケースがあります.  
その際、すでに作成済みの`@ExampleObject`のjsonをそのまま使用すると楽ができます.

```java
  public UserResponse.UserResponseBody getJsonUserById(@PathParam("id") String id) {
    var response =
        JsonUtil.readFromResource(
            "openapi/user/get_response_default.json", UserResponse.UserResponseBody.class);
    return response.get();
  }
```

メソッドの戻り値の型が`Response`ではなく、直接レスポンスを返却しています.  
（実行結果のHttpが200が固定になりますが、モックであるため問題ないと考えます）
