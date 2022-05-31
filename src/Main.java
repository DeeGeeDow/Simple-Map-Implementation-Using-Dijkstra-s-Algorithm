import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws Exception{
        if(args.length == 2 && args[0].equals("-f")){
            File file = new File(args[1]);
            Scanner sc = new Scanner(file);
            int v = sc.nextInt();
            Graph g = new Graph(v);
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

            Scanner in = new Scanner(System.in);
            String start,finish;
            System.out.print("Masukkan node awal : ");
            start = in.nextLine();
            int start_index = g.getIndexNode(start);
            while(start_index == -1){
                System.out.print("Node awal tidak valid. Masukkan node awal : ");
                start = in.nextLine();
                start_index = g.getIndexNode(start);
            }

            System.out.print("Masukkan node tujuan : ");
            finish = in.nextLine();
            int finish_index = g.getIndexNode(finish);
            while(finish_index == -1){
                System.out.print("Node tujuan tidak valid. Masukkan node tujuan : ");
                finish = in.nextLine();
                finish_index = g.getIndexNode(finish);
            }

            Dijkstra d = new Dijkstra(g);
            d.printSolution(start_index, finish_index);
        }
    }
}
