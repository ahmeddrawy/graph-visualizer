import java.util.ArrayList;

public class UndirectedGraphBuilder implements IGraphBuilder {
    private int nodes;
    private ArrayList<Edge> edgeList;
    private ArrayList<Integer>[] adjacencyList;
    private boolean[][] adjacencyMatrix;
    private int[][] representationMatrix;

    public UndirectedGraphBuilder(int nodes, ArrayList<Edge> edgeList){
        this.nodes = nodes;
        this.edgeList = edgeList;
    }

    public void buildAdjacencyList(){
        adjacencyList = new ArrayList[nodes];
        for(int i = 0; i<nodes; i++)
            adjacencyList[i] = new ArrayList();

        for(int i = 0; i<edgeList.size(); i++){
            int u = edgeList.get(i).getU();
            int v = edgeList.get(i).getV();

            adjacencyList[u].add(v);
            adjacencyList[v].add(u);
        }
    }
    public ArrayList<Integer>[] getAdjacencyList(){return adjacencyList;}

    public void buildAdjacencyMatrix(){
        adjacencyMatrix = new boolean[nodes][nodes];
        for(int i = 0; i<edgeList.size(); i++){
            int u = edgeList.get(i).getU();
            int v = edgeList.get(i).getV();

            adjacencyMatrix[u][v] = true;
            adjacencyMatrix[v][u] = true;
        }
    }
    public boolean[][] getAdjacencyMatrix(){return adjacencyMatrix;}

    public void buildRepresentationMatrix(){ /// Building representation matrix from adjacency list as requested.
        if(adjacencyList == null)
            buildAdjacencyList();

        representationMatrix = new int[nodes][nodes]; /// initialized with zero by default.

        for(int i = 0; i<nodes; i++){
            for(int neighbour : adjacencyList[i]){
                representationMatrix[i][neighbour]++;
            }
        }
    }
    public int[][] getRepresentationMatrix(){return representationMatrix;}

    
//    public int[][] getIncidenceMatrix(){return incidenceMatrix;}

}
