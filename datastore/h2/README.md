# H2DB

## Overview

H2DBの環境構築の手順.
H2DBはプログラムから起動することもできますが、他のRDMSと同じ使用をする作業手順を整理します.

## Site

https://www.h2database.com/html/main.html

## 準備

### ダウンロード

https://www.h2database.com/html/download.html


### データベースファイルを作成

ダウンロードしたファイルを解凍して、h2のjarを実行してデータベースの保存先となるファイルを作成します.
アプリで使用する単位で作成します.
データベースファイルのパスなど修正をしてください.

```
./h2/bin/h2-create-datafile.sh
```

### サーバを起動

```
./h2/bin/h2-chrome.sh
```

- h2.jarと同じフォルダへコピーをします
- 実行するh2のバージョンに修正します
- ローカルのFirefoxを削除してブラウザからの起動が出来なくなったのでパラメータにローカルのchromeを起動するようにしています
- 利用するアプリ側で指定する tcpポートは sh の指定を参考にしてください


## Webコンソール

サーバを起動するとブラウザが立ち上がります.  
上記のShellの記載にあわせてサーバモードでアクセスします.  

```
jdbc:h2:tcp://localhost:9093/~/appdata;DATABASE_TO_LOWER=true
```

パラメータ(`DATABASE_TO_LOWER`)はテーブルを小文字にするための設定です.  
JPAを使用した場合にテーブル名を小文字にしておく必要があるため指定しています.  