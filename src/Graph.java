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

    public int getWeight(int n1, int n2){
        return adj_matrix[n1][n2];
    }

    public void setWeight(int n1, int n2, int w){
        adj_matrix[n1][n2] = w;
    }
}