import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        int n = reader.nextInt();
        ArrayList<Edge> edgeList = new ArrayList<>();
        while(true){
            int u = reader.nextInt();
            int v = reader.nextInt();
            if(u < 0 || v < 0) break;

            Edge e = new Edge();
            e.setU(u);
            e.setV(v);
            edgeList.add(e);
        }

        UndirectedGraphBuilder ugb = new UndirectedGraphBuilder(n, edgeList);
        ugb.buildAdjacencyMatrix();
        boolean adjMatrix[][] = ugb.getAdjacencyMatrix();

        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; ++j){
                System.out.print((adjMatrix[i][j] ? 1 : 0 )+ " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        ugb.buildAdjacencyList();
        ArrayList<Integer>[] adjList = ugb.getAdjacencyList();
        for(int i = 0; i<n; i++){
            System.out.print(i + " -> ");
            for(int j = 0; j<adjList[i].size(); ++j)
                System.out.print(adjList[i].get(j) + " ");
            System.out.print("\n");
        }
        System.out.print("\n");

        ugb.buildRepresentationMatrix();
        int adj[][] = ugb.getRepresentationMatrix();
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; ++j){
                System.out.print(adj[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
