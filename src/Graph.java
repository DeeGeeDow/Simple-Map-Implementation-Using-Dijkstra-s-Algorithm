import java.util.*;
public class Graph {
    private int v;
    private int[][] adj_matrix;
    private String[] node_name;

    public Graph(int v){
        this.v = v;
        this.adj_matrix = new int[v][v];
        for(int i=0; i<v; i++){
            for(int j=0; j<v; j++){
                if(i!=j) adj_matrix[i][j] = Integer.MAX_VALUE;
            }
        }
        this.node_name = new String[v];
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

    public void setNode_name(String[] node_name){
        this.node_name = node_name;
    }

    public int getIndexNode(String node){
        for(int i=0; i<v; i++){
            if(node_name[i].equals(node)){
                return i;
            }
        }
        return -1;
    }

    public String getNodeName(int index){
        return node_name[index];
    }
    public void showGraph(){
        System.out.println("Graph contains " + Integer.toString(v) + " nodes");
        System.out.println("Node names : ");
        for(int i=0; i<v; i++){
            System.out.print(node_name[i]);
            System.out.print(", ");
        }
        System.out.println();
        for(int i=0; i<v; i++){
            for(int j=0; j<v; j++) {
                if(i == j && adj_matrix[i][j] == 0) continue;
                else if(i != j && adj_matrix[i][j] == Integer.MAX_VALUE) continue;
                System.out.println(node_name[i] + " ---[" + adj_matrix[i][j] + "]--> " + node_name[j]);
            }
        }
    }
}