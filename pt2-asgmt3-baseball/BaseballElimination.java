import java.util.HashMap;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {
    
    private HashMap<String, Integer> names;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] games;
    private FordFulkerson ff;
    
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        names = new HashMap<String, Integer>();
        wins = new int[n];
        losses = new int[n];
        remaining = new int[n];
        games = new int[n][n];
        
        int i = 0;
        while (!in.isEmpty()) {
            names.put(in.readString(), i);
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < n; j++) {
                games[i][j] = in.readInt();
            }
            i++;
        }
    }
    
    public int numberOfTeams() {
        return names.size();
    }
    
    public Iterable<String> teams() {
        return names.keySet();
    }
    
    public int wins(String team) {
        validateTeam(team);
        
        return wins[names.get(team)];
    }
    
    public int losses(String team) {
        validateTeam(team);
        
        return losses[names.get(team)];
    }
    
    public int remaining(String team) {
        validateTeam(team);
        
        return remaining[names.get(team)];
    }
    
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        
        return games[names.get(team1)][names.get(team2)];
    }
    
    public boolean isEliminated(String team) {
        validateTeam(team);
        int x = names.get(team);
        
        if (isTriviallyEliminated(x)) {
            return true;
        }
        
        return isNonTriviallyEliminated(x);
    }
    
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
        int x = names.get(team);
        ArrayList<String> teams =  new ArrayList<String>();
        
        if (isTriviallyEliminated(x)) {
            for (String t : names.keySet()) {
                if (wins[names.get(team)] + remaining[names.get(team)] < wins[names.get(t)]) {
                    teams.add(t);
                    return teams;
                }
            }
        }
        
        if (isNonTriviallyEliminated(x)) {
            for (String t : names.keySet()) {
                if (ff.inCut(names.get(t))) {
                    teams.add(t);
                }
            }
            return teams;
        }
        
        return null;
    }
    
    private boolean isTriviallyEliminated(int x) {
        for (int i = 0; i < numberOfTeams(); i++) {
            if (wins[x] + remaining[x] - wins[i] < 0) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isNonTriviallyEliminated(int x) { 
        int n = numberOfTeams();
        int v = (n - 1) * (n - 2) / 2 + n + 2;
        int s = v - 2;
        int t = v - 1;
        FlowNetwork network = createNetwork(x, v, s, t);
        ff = new FordFulkerson(network, s, t);
        
        for (FlowEdge e : network.adj(s)) {
            if (e.flow() != e.capacity()) {
                return true;
            }
        }
        
        return false;
    }
    
    private FlowNetwork createNetwork(int x, int v, int s, int t) {
        FlowNetwork network = new FlowNetwork(v);
        int n = numberOfTeams();
        int offset = n;
        
        for (int i = 0; i < n; i++) {
            if (i == x) {
                continue;
            }
            network.addEdge(new FlowEdge(i, t, wins[x] + remaining[x] - wins[i]));
            for (int j = i + 1; j < n; j++) {
                if (j == x) {
                    continue;
                }
                network.addEdge(new FlowEdge(s, offset, games[i][j]));
                network.addEdge(new FlowEdge(offset, i, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(offset, j, Double.POSITIVE_INFINITY));
                offset++;
            }
        }
        
        return network;
    }
    
    private void validateTeam(String team) {
        if (!names.containsKey(team)) {
            throw new IllegalArgumentException("Not a valid team");
        }
    }
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
    }
    }
    
}