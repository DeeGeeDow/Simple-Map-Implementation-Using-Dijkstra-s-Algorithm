import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.List;
public class GUI {
    private JPanel pnl_north;
    private JPanel pnl_center;
    private JPanel pnl_left;
    private JPanel pnl_south;
    private JTextField txt_path;
    private JButton btn_browse;
    private JPanel pnl_path;
    private JButton btn_draw_graph;
    private JPanel pnl_draw_msg;
    private JPanel panelMain;
    private JPanel pnl_graph;
    private JTextField txt_source;
    private JTextField txt_target;
    private JButton btn_search;
    private JLabel lbl_draw_msg;
    private JLabel lbl_search_msg;
    private mxGraph graph = new mxGraph();
    private mxGraphComponent graphComponent = new mxGraphComponent(graph);
    private Graph g;
    private  Dijkstra dijkstra;

    public GUI() {
        pnl_graph.add(graphComponent);

        Object parent = graph.getDefaultParent();

        btn_browse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                String current_dir, selected_filepath;
                File f = new File(".");
                current_dir = f.getAbsolutePath();
                JFileChooser fileChooser = new JFileChooser(f);
                fileChooser.showOpenDialog(null);
                f = fileChooser.getSelectedFile();
                selected_filepath = f.getAbsolutePath();
                txt_path.setText(selected_filepath);
            }
        });
        btn_draw_graph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);

                File file = new File(txt_path.getText());
                Scanner sc;
                Object[] ver;
                try {
                    sc = new Scanner(file);
                    int v = sc.nextInt();
                    g = new Graph(v);
                    sc.nextLine();
                    g.setNode_name(sc.nextLine().split(" "));
                    for(int i=0; i<v; i++){
                        String[] sline = sc.nextLine().split(" ");
                        for(int j=0; j<sline.length; j+=2){
                            String target_node = sline[j];
                            int w = Integer.valueOf(sline[j+1]);
                            g.setWeight(i, g.getIndexNode(target_node), w);
                        }
                    }

                    dijkstra = new Dijkstra(g);
                    graph.getModel().beginUpdate();
                    int centerx = 200;
                    int centery = 200;
                    int radius = 100;
                    ver = new Object[v];
                    for(int i=0; i<v; i++){
                        ver[i] = graph.insertVertex(parent,null,g.getNodeName(i), centerx + Math.cos(2*Math.PI*i/v)*radius, centery + Math.sin(2*Math.PI*i/v)*radius, 40, 40);
                    }
                    for(int i=0; i<v; i++){
                        for(int j=0; j<v; j++){
                            if(g.getWeight(i,j) != Integer.MAX_VALUE && i!=j){
                                graph.insertEdge(parent, null, Integer.toString(g.getWeight(i,j)), ver[i], ver[j]);
                            }
                        }
                    }
                    graph.getModel().endUpdate();
                    System.out.println("Run");
                }catch(FileNotFoundException err){
                    System.out.println("File Not Found!");
                    lbl_draw_msg.setText("File Not Found!");
                }
            }
        });
        btn_search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // harusnya ada vlist sm elist
                graph.selectAll();
                graph.removeCells();
                lbl_search_msg.setText("");
                int start_index, finish_index;
                start_index = g.getIndexNode(txt_source.getText());
                finish_index = g.getIndexNode(txt_target.getText());
                String search_msg = "";
                if(start_index == -1 || finish_index == -1){
                    search_msg = "Node not found";
                }else{
                    search_msg += "<html>Minimum cost : ";
                    search_msg += Integer.toString(dijkstra.getResult(start_index, finish_index));
                    search_msg += "<br/>";
                    List<Integer> path = dijkstra.getPath(start_index, finish_index);
                    for(int i=0; i<path.size(); i++){
                        search_msg += (g.getNodeName(path.get(i)));
                        if(i<path.size()-1) {
                            search_msg += (" ---[");
                            search_msg += (g.getWeight(path.get(i), path.get(i+1)));
                            search_msg += ("]--> ");
                        }
                    }
                    graph.getModel().beginUpdate();
                    try
                    {
                        int centerx=200;
                        int centery=200;
                        int radius=100;
                        Object[] v = new Object[g.getV()];
                        for(int i=0; i<g.getV(); i++){
                            v[i] = graph.insertVertex(parent, null, g.getNodeName(i), centerx + Math.cos(2*Math.PI*i/g.getV())*radius, centery + Math.sin(2*Math.PI*i/g.getV())*radius, 40, 40);
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
                    search_msg += "</html>";
                }
                graph.refresh();
                lbl_search_msg.setText(search_msg);
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setContentPane(new GUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
