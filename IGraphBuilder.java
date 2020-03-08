import java.util.ArrayList;

public interface IGraphBuilder {
    public void buildAdjacencyList();
    public ArrayList<Integer>[] getAdjacencyList();

    public void buildAdjacencyMatrix();
    public boolean[][] getAdjacencyMatrix();

    public void buildRepresentationMatrix();
    public int[][] getRepresentationMatrix();

    public void buildIncidenceMatrix();
    public int[][] getIncidenceMatrix();
}
