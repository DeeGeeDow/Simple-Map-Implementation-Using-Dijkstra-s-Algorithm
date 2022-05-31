import java.util.*;

public class Dijkstra {
    private List<Integer> unvisited;
    private final Graph g;
    private int start;
    private int[] distance;
    private int[] src_node;


    public Dijkstra(Graph g){
        this.g = g;
        int v = g.getV();
        this.unvisited = new ArrayList<>();
        for(int i=0; i<v; i++){
            unvisited.add(i);
        }
        this.distance = new int[v];
        for(int i=0; i<v; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        this.src_node = new int[v];
        this.start = -1;
    }

    public void run(int start){
        this.clear();
        this.start = start;
        distance[start] = 0;
        //System.out.println(start);
        while(!unvisited.isEmpty()){
            int cur_node = -1;
            for(Integer i : unvisited){
                if(cur_node == -1 || distance[cur_node] > distance[i]){
                    cur_node = i;
                }
            }
            for(int i=0; i<g.getV(); i++){
                int new_dist = distance[cur_node]+g.getWeight(cur_node,i);
                if (new_dist < 0) new_dist = Integer.MAX_VALUE;
                if(distance[i]  > new_dist){
                    distance[i]  = new_dist;
                    src_node[i] = cur_node;
                }
            }
            unvisited.remove(Integer.valueOf(cur_node));
        }
    }

    public void clear(){
        int v = g.getV();
        this.unvisited = new ArrayList<>();
        for(int i=0; i<v; i++){
            unvisited.add(i);
        }
        this.distance = new int[v];
        for(int i=0; i<v; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        this.src_node = new int[v];
        this.start = -1;
    }

    public int getResult(int start, int finish){
        if(this.start != start){
            this.run(start);
        }
        return this.distance[finish];
    }

    public List<Integer> getPath(int start, int finish){
        if(this.start != start){
            this.run(start);
        }
        List<Integer> path = new ArrayList<>();
        int n = finish;
        while(n != start){
            path.add(0,n);
            n = src_node[n];
        }
        path.add(0,start);
        return path;
    }
}
