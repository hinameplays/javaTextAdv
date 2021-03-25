#!/bin/sh

javac -encoding UTF-8 -source 14 -target 14 -verbose ./Main.java

java Main

echo "Setup script exited successfully."