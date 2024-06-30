# karate-01-openapi

karateを使ったtest


## Run

### 事前準備

確認をしたいProjectのアプリを起動します  
（Cargoでの起動前提としているのでPortを8080以外にしています）  


### 対象のProjectとテストの実行コマンド

|Project        |mvnコマンド                                                  |
|---|---|
|ee10-01-openapi|./mvnw test -DskipTest=false -DtestGroup=e2eTest -Dport=8081 -DcontextPath=ee10-01-openapi    |
|ee10-02-openapi|./mvnw test -DskipTest=false -DtestGroup=e2eTest -Dport=8081 -DcontextPath=ee10-02-openapi    |


##### mvnのパラメータの説明

|パラメータ|値|説明|
|---|---|---|
|skipTest    |false            |pom.xmlの初期値としてtrueとすることでルートプロジェクトで `./mvnw/package` でテストが走行しないようにしています. <br>falseにしてコマンド実行時だけ対象にします.
|testGroup   |e2eTest          |`@Tag`で指定したテスト対象ラベル
|contextPath |ee10-01-openapi  |実行するWebAPIのコンテキストパス（Project名）<br>`karate-config.js`でアクセス先のコンテキストを指定する際に使用します.

