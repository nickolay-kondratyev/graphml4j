./build.sh

java -cp ./build/libs/graphml4j-1.0-SNAPSHOT.jar samples.SimpleGraph

open /tmp/output.graphml
