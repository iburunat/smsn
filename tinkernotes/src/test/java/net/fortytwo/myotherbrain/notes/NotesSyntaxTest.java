package net.fortytwo.myotherbrain.notes;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * User: josh
 * Date: 6/20/11
 * Time: 7:58 PM
 */
public class NotesSyntaxTest extends TestCase {
    //private Graph graph;
    //private FramesManager manager;
    private NotesSyntax syntax;

    @Override
    public void setUp() throws Exception {
        //graph = new TinkerGraph();
        //manager = new FramesManager(graph);
        syntax = new NotesSyntax();
    }

    @Override
    public void tearDown() throws Exception {
        //graph.shutdown();
    }

    public void testAll() throws Exception {
        String s = "" +
                ".  a\n" +
                "    .  b\n" +
                "    .  c\n" +
                "V  d\n" +
                "\n" +
                "[second context]\n" +
                ".  e\n" +
                "0000000:1111111: .  f\n" +
                "#####42:2222222: .  g";

        List<NoteContext> contexts = parse(s);
        assertEquals(2, contexts.size());
        assertEquals(2, contexts.get(0).getNotes().size());
        assertEquals(3, contexts.get(1).getNotes().size());
        assertEquals(2, contexts.get(0).getNotes().get(0).getChildren().size());
        assertEquals("a", contexts.get(0).getNotes().get(0).getTargetValue());
        assertEquals("c", contexts.get(0).getNotes().get(0).getChildren().get(1).getTargetValue());
        assertEquals("d", contexts.get(0).getNotes().get(1).getTargetValue());
        assertEquals("V", contexts.get(0).getNotes().get(1).getLinkValue());
        assertEquals("", contexts.get(0).getTargetValue());
        assertEquals("second context", contexts.get(1).getTargetValue());
        assertEquals("e", contexts.get(1).getNotes().get(0).getTargetValue());

        // Link and target keys
        assertNull(contexts.get(1).getNotes().get(0).getLinkKey());
        assertNull(contexts.get(1).getNotes().get(0).getTargetKey());
        assertEquals("f", contexts.get(1).getNotes().get(1).getTargetValue());
        assertEquals("0000000", contexts.get(1).getNotes().get(1).getLinkKey());
        assertEquals("1111111", contexts.get(1).getNotes().get(1).getTargetKey());

        // "Ephemeral" link keys
        assertEquals("g", contexts.get(1).getNotes().get(2).getTargetValue());
        assertNull(contexts.get(1).getNotes().get(2).getLinkKey());
        assertEquals("2222222", contexts.get(1).getNotes().get(2).getTargetKey());
    }

    private List<NoteContext> parse(final String s) throws IOException, NotesSyntax.NoteParsingException {
        InputStream in = new ByteArrayInputStream(s.getBytes());
        try {
            return syntax.readContexts(in);
        } finally {
            in.close();
        }
    }
}
