import java.util.ArrayList;
import java.util.Scanner;
/*
test case
4 7
0 1
1 2
1 2
3 2
3 1
3 0
3 3
----
4 4

0 1
1 0
1 3
2 1


 */
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int e = sc.nextInt();
        ArrayList<Integer>[] adjacencyList;
        boolean[][] adjacencyMatrix;
        int[][] representationMatrix;
        int[][] incidenceMatrix;

        ArrayList<Edge>  arr = new ArrayList<Edge>();
        for(int i = 0 ; i < e ; ++i){
            int x , y;
            x= sc.nextInt() ;
            y = sc.nextInt();
            arr.add(new Edge(x,y));


        }

        DirectedGraphBuilder g = new DirectedGraphBuilder(n, arr);
        adjacencyList= g.getAdjacencyList();
        adjacencyMatrix =g.getAdjacencyMatrix();
        incidenceMatrix =  g.getIncidenceMatrix();
        representationMatrix =  g.getRepresentationMatrix();
        System.out.println("printing adjaceny list");
        for(int i = 0 ; i < n ; ++i){
            System.out.printf(i + " ");
            for (Integer v:adjacencyList[i]) {
                System.out.printf(v + " ");
            }
            System.out.println("");
        }
        for(int i = 0  ; i < n ; ++i){
            System.out.print(i + " : ");
            for (int j = 0 ; j < n ; ++j){
                System.out.print(adjacencyMatrix[i][j]? 1+ " ": 0  + " ");

            }
            System.out.println("");
        }
        for(int i = 0  ; i < n ; ++i){
            System.out.print(i + " : ");
            for (int j = 0 ; j < n ; ++j){
                System.out.print(representationMatrix[i][j]+ " ");

            }
            System.out.println("");
        }
        System.out.println("");
        for(int i = 0  ; i < n ; ++i){
            System.out.print(i+1 + " : ");
            for (int j = 0 ; j < e ; ++j){
                System.out.print(incidenceMatrix[i][j]+ " ");

            }
            System.out.println("");
        }


    }
}
