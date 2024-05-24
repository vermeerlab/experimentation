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

