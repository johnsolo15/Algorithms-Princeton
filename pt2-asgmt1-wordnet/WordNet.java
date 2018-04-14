import java.util.HashMap;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    
    private HashMap<Integer, String> idMap;
    private HashMap<String, ArrayList<Integer>> wordMap;
    private Digraph graph;
    private SAP sap;
    
   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        idMap = new HashMap<Integer, String>();
        wordMap = new HashMap<String, ArrayList<Integer>>();
        
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            String[] words = tokens[1].split(" ");
            idMap.put(id, tokens[1]);
            for (int i = 0; i < words.length; i++) {
                if (!wordMap.containsKey(words[i])) {
                    wordMap.put(words[i], new ArrayList<Integer>());
                }
                wordMap.get(words[i]).add(id);
            }
        }
        
        graph = new Digraph(idMap.size());
        
        in = new In(hypernyms);
        while(in.hasNextLine()) {
            String line = in.readLine();
            String[] ids = line.split(",");
            for (int i = 1; i < ids.length; i++) {
                graph.addEdge(Integer.parseInt(ids[0]), Integer.parseInt(ids[i]));
            }
        }
        
        validateGraph(graph);
        sap = new SAP(graph);
    }

   // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordMap.keySet();
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return wordMap.containsKey(word);
    }

   // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateVertex(nounA);
        validateVertex(nounB);
        Iterable<Integer> idsA = wordMap.get(nounA);
        Iterable<Integer> idsB = wordMap.get(nounB);
        return sap.length(idsA, idsB);
    }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateVertex(nounA);
        validateVertex(nounB);
        Iterable<Integer> idsA = wordMap.get(nounA);
        Iterable<Integer> idsB = wordMap.get(nounB);
        int id = sap.ancestor(idsA, idsB);
        if (id == -1) {
            return null;
        }
        return idMap.get(id);
    }
    
    private void validateGraph(Digraph graph) {
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        int roots = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0) {
                roots++;
            }
        }
        if (roots != 1) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateVertex(String vertex) {
        if (vertex == null || !isNoun(vertex)) {
            throw new IllegalArgumentException();
        }
    }

   // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("tests\\synsets.txt", "tests\\hypernyms.txt");
        String w = wordnet.sap("shrewishness", "foglamp");
        System.out.println(w);
    }
}