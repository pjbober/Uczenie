#!/bin/sh


ROBOCODE_DIR=/home/kliput/Programy/robocode

# usage: agh.uczenie.BattleRunner <robocode_dir> <num_of_battles> <num_of_rounds>

java -DNOSECURITY=true -cp target/classes:$ROBOCODE_DIR/libs/robocode.jar agh.uczenie.BattleRunner $ROBOCODE_DIR $1 $2

