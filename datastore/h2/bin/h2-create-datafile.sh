#!/bin/sh
dir=$(dirname "$0")
h2jar=h2-2.2.224.jar
datafilePath=~/appdata
java -cp "$dir/$h2jar" org.h2.tools.Shell -url "jdbc:h2:$datafilePath" -driver "Driver" -user sa
