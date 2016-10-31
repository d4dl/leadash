#!/usr/bin/env bash

mvn clean install package -DskipTests

scp target/leadash-2.0-SNAPSHOT.jar d4dl.com:~/public_html/d4dl.com

ssh d4dl.com <<EOF
cd ~/public_html/d4dl.com
mv leadash-2.0-SNAPSHOT.jar leadash.jar
EOF
