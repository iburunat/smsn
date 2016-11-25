package net.fortytwo.smsn.brain;

import net.fortytwo.smsn.brain.model.Atom;
import net.fortytwo.smsn.brain.model.AtomGraph;
import net.fortytwo.smsn.brain.model.Filter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class NoteHistory {
    private static final int CAPACITY = 1000;

    private final String[] visitedAtoms;
    private int totalVisits;

    public NoteHistory() {
        this.visitedAtoms = new String[CAPACITY];
        totalVisits = 0;
    }

    public void visit(final String atomId) {
        visitedAtoms[totalVisits % CAPACITY] = atomId;
        totalVisits++;
    }

    public Iterable<Atom> getHistory(final int maxlen,
                                   final boolean dedup,
                                   final AtomGraph graph,
                                   final Filter filter) {
        Collection<Atom> atoms = dedup
                ? new LinkedHashSet<>()
                : new LinkedList<>();

        int low = Math.max(totalVisits - CAPACITY, 0);

        for (int i = totalVisits - 1; i >= low; i--) {
            if (atoms.size() >= maxlen) {
                break;
            }

            String id = visitedAtoms[i % CAPACITY];

            Atom a = graph.getAtom(id);
            if (null != a && filter.isVisible(a)) {
                atoms.add(a);
            }
        }

        return atoms;
    }
}
