import java.util.*;
public class Graph {
    private int v;
    private int[][] adj_matrix;
    public Graph(int v){
        this.v = v;
        this.adj_matrix = new int[v][v];
        for(int i=0; i<v; i++){
            for(int j=0; j<v; j++){
                if(i!=j) adj_matrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public int getV(){
        return this.v;
    }

    public int getWeight(int n1, int n2){
        return adj_matrix[n1][n2];
    }

    public void setWeight(int n1, int n2, int w){
        adj_matrix[n1][n2] = w;
    }

    public void showGraph(){
        System.out.println("Graph contains " + Integer.toString(v) + " nodes");
        for(int i=0; i<v; i++){
            for(int j=0; j<v; j++) {
                if(i == j && adj_matrix[i][j] == 0) continue;
                else if(i != j && adj_matrix[i][j] == Integer.MAX_VALUE) continue;
                System.out.println("#" + Integer.toString(i + 1) + " --> #" + Integer.toString(j+1) + " = " + Integer.toString(adj_matrix[i][j]));
            }
        }
    }
}