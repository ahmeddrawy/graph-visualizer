import java.util.ArrayList;
/// coded by khaled edited to be directed by Hanafy- today 07/03/2020 5PM
public class DirectedGraphBuilder implements IGraphBuilder{
    private int nodes;
    private ArrayList<Edge> edgeList;
    private ArrayList<Integer>[] adjacencyList;
    private boolean[][] adjacencyMatrix;
    private int[][] representationMatrix;
    private int[][] incidenceMatrix;

    public DirectedGraphBuilder(int nodes, ArrayList<Edge> edgeList){
        this.nodes = nodes;
        this.edgeList = edgeList;
        buildAdjacencyList();
        buildAdjacencyMatrix();
        buildIncidenceMatrix();
        buildRepresentationMatrix();
    }

    public void buildAdjacencyList(){
        adjacencyList = new ArrayList[nodes];
        for(int i = 0; i<nodes; i++)
            adjacencyList[i] = new ArrayList();

        for(int i = 0; i<edgeList.size(); i++){
            int u = edgeList.get(i).getU();
            int v = edgeList.get(i).getV();

            adjacencyList[u].add(v);
        }
    }
    public ArrayList<Integer>[] getAdjacencyList(){return adjacencyList;}

    public void buildAdjacencyMatrix(){
        adjacencyMatrix = new boolean[nodes][nodes];
        for(int i = 0; i<edgeList.size(); i++){
            int u = edgeList.get(i).getU();
            int v = edgeList.get(i).getV();

            adjacencyMatrix[u][v] = true;
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

    public void buildIncidenceMatrix(){
        incidenceMatrix = new int[nodes][edgeList.size()];
        for(int i = 0; i<edgeList.size(); i++){
            int u = edgeList.get(i).getU();
            int v = edgeList.get(i).getV();
            incidenceMatrix[u][i] = -1;
            incidenceMatrix[v][i] += 1;
        }
    }
    public int[][] getIncidenceMatrix(){return incidenceMatrix;}

}
