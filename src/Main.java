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

            System.out.print(g.getWeight(1,2));
        }
    }
}
