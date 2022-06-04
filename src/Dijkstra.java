import java.util.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

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

    public List<Integer> getPath(int start, int finish) {
        if (this.start != start) {
            this.run(start);
        }
        List<Integer> path = new ArrayList<>();
        int n = finish;
        while (n != start) {
            path.add(0, n);
            n = src_node[n];
        }
        path.add(0, n);
        return path;
    }

    public void printSolution(int start_index, int finish_index){
        System.out.println(getResult(start_index,finish_index));
        List<Integer> path = getPath(start_index,finish_index);
        for(int i=0; i<path.size(); i++){
            System.out.print(g.getNodeName(path.get(i)));
            if(i<path.size()-1) {
                System.out.print(" ---[");
                System.out.print(g.getWeight(path.get(i), path.get(i+1)));
                System.out.print("]--> ");
            }
        }
    }

    public void drawSolution(int start_index, int finish_index){
        JFrame frame = new JFrame("Solution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mxGraph graph = new mxGraph();
        Object parent =graph.getDefaultParent();

        System.out.println(getResult(start_index,finish_index));
        List<Integer> path = getPath(start_index,finish_index);
        for(int i=0; i<path.size(); i++){
            System.out.print(g.getNodeName(path.get(i)));
            if(i<path.size()-1) {
                System.out.print(" ---[");
                System.out.print(g.getWeight(path.get(i), path.get(i+1)));
                System.out.print("]--> ");
            }
        }
        graph.getModel().beginUpdate();
        try
        {
            int centerx=200;
            int centery=200;
            int radius=100;
            Object[] v = new Object[6];
            for(int i=0; i<g.getV(); i++){
                v[i] = graph.insertVertex(parent, null, g.getNodeName(i), centerx + Math.cos(2*Math.PI*i/6)*radius, centerx + Math.sin(2*Math.PI*i/6)*radius, 40, 40);
            }
            for(int i=0; i<g.getV(); i++){
                for(int j=0; j<g.getV(); j++){
                    if(g.getWeight(i,j) != Integer.MAX_VALUE && i!=j){
                        graph.insertEdge(parent, null, Integer.toString(g.getWeight(i,j)), v[i], v[j]);
                    }
                }
            }
            Object[] vlist = new Object[path.size()];
            if(path.size()>1) {
                Object[] elist = new Object[path.size()-1];
                for(int i=0; i<path.size(); i++){
                    vlist[i] = v[path.get(i)];
                    if(i>0) elist[i-1] = graph.getEdgesBetween(v[path.get(i-1)], v[path.get(i)])[0];
                }
                graph.setCellStyle("defaultVertex;fillColor=lightgreen", vlist);
                graph.setCellStyle("defaultEdge;strokeColor=lightgreen", elist);
            }
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        frame.add(graphComponent);
        frame.setSize(new Dimension(400,400));
        frame.setVisible(true);
    }
}
