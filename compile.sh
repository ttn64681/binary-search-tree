#!/bin/bash

javac -d bin src/cs2720/p3/NodeType.java
javac -cp bin -d bin src/cs2720/p3/BST.java
javac -cp bin -d bin src/cs2720/p3/BSTDriver.java
java -cp bin cs2720.p3.BSTDriver string-input.txt
