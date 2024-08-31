#!/bin/sh
dir=$(dirname "$0")
h2jar=h2-2.2.224.jar
webPort=8085
tcpPort=9093
java -Dh2.browser=/usr/bin/google-chrome -cp "$dir/$h2jar:$H2DRIVERS:$CLASSPATH" org.h2.tools.Console "$@" -webPort $webPort -tcpPort $tcpPort
