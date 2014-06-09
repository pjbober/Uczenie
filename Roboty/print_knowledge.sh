#!/bin/sh


ROBOCODE_DIR=/home/kliput/Programy/robocode

java -DNOSECURITY=true -cp target/classes:$ROBOCODE_DIR/libs/robocode.jar agh.uczenie.utils.KnowledgePrinter $1

