# karate-01-openapi

karateを使ったtest


## Run

確認をしたいProjectのアプリを起動します（Cargoでの起動前提としているのでPortが8080以外になっています）  

対象のProjectとテストの実行コマンド

|Project        |mvnコマンド                                                  |
|---|---|
|ee10-01-openapi|./mvnw test -DskipTest=false -DtestGroup=ee10-01-openapi -Dport=8081 -DcontextPath=ee10-01-openapi    |



