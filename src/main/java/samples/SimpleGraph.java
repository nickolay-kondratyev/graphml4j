/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package samples;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.fritaly.graphml4j.GraphMLWriter;


/**
 *
 * @author francois_ritaly
 */
public class SimpleGraph {

    public static void main(String[] args) throws Exception {

        final File file = getOutFile(args);

        System.out.println("Writing GraphML file to " + file.getAbsolutePath() + " ...");

        // This list will store the identifiers of nodes added to the graph
        final List<String> nodeIds = new ArrayList<String>();

        final FileWriter fileWriter = new FileWriter(file);

        final GraphMLWriter graphWriter = new GraphMLWriter(fileWriter);

        try {
            // Open the graph
            graphWriter.graph();

            // Generate 3 groups (of nodes) with 5 nodes in each
            for (int i = 0; i < 3; i++) {
                // Randomly decide whether the group should be rendered as open or closed
                final boolean open = new Random().nextBoolean();

                // Open a new group of nodes
                graphWriter.group(String.format("Group #%d", i + 1), open);

                // Add 5 nodes to the group
                for (int j = 0; j < 5; j++) {
                    nodeIds.add(graphWriter.node(String.format("N%d", (i * 5) + j + 1)));
                }

                // Close the group
                graphWriter.closeGroup();
            }

            // Randomly generate 15 edges between the nodes
            for (int i = 0; i < 15; i++) {
                Collections.shuffle(nodeIds);

                graphWriter.edge(nodeIds.get(0), nodeIds.get(1));
            }

            // Close the graph
            graphWriter.closeGraph();

            System.out.println("Done");
        } finally {
            // Calling GraphMLWriter.close() is necessary to dispose the underlying resources
            graphWriter.close();
            fileWriter.close();
        }
    }

    private static File getOutFile(final String[] args) {

        if (args == null || args.length == 0) {
            return new File("/tmp/output.graphml");
        }
        if (args.length == 1) {
            return new File(args[0]);
        } else {
            System.out.println(String.format("%s <output-file>", SimpleGraph.class.getSimpleName()));
            System.exit(1);
            throw new RuntimeException("Not implemented");
        }

    }
}
