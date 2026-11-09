#!/usr/bin/env sh
set -eu
mkdir -p out data
find src/main/java -name '*.java' > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out com.mnr.fooddelivery.ui.Main
