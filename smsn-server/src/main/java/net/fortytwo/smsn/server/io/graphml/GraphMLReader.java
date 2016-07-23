package net.fortytwo.smsn.server.io.graphml;

import net.fortytwo.smsn.brain.MyOtherBrain;
import net.fortytwo.smsn.server.io.Format;
import net.fortytwo.smsn.server.io.BrainReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class GraphMLReader extends BrainReader {

    @Override
    public List<Format> getFormats() {
        return Arrays.asList(GraphMLFormat.getInstance());
    }

    @Override
    protected void importInternal(MyOtherBrain destBrain, final InputStream sourceStream, final Format format)
            throws IOException {
        com.tinkerpop.blueprints.util.io.graphml.GraphMLReader r = new com.tinkerpop.blueprints.util.io.graphml.GraphMLReader(destBrain.getAtomGraph().getPropertyGraph());
        r.inputGraph(sourceStream);
    }
}