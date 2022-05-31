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

            for(int i=0; i<v; i++){
                int e = sc.nextInt();
                for(int j=0; j<e; j++){
                    int target_node = sc.nextInt();
                    int w = sc.nextInt();
                    g.setWeight(i, target_node-1, w);
                }
            }

            Scanner in = new Scanner(System.in);
            int start,finish;
            System.out.print("Masukkan node awal : ");
            start = in.nextInt();
            while(start <= 0 || start > g.getV()){
                System.out.print("Node awal tidak valid. Masukkan node awal : ");
                start = in.nextInt();
            }

            System.out.print("Masukkan node tujuan : ");
            finish = in.nextInt();
            while(finish <= 0 || finish > g.getV()){
                System.out.print("Node tujuan tidak valid. Masukkan node tujuan : ");
                finish = in.nextInt();
            }

            Dijkstra d = new Dijkstra(g);
            System.out.println(d.getResult(start-1,finish-1));
            List<Integer> path = d.getPath(start-1,finish-1);
            for(int i=0; i<path.size(); i++){
                System.out.print(path.get(i)+1);
                if(i<path.size()-1) System.out.print(" --> ");
            }
        }
    }
}
