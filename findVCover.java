import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class findVCover {

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println(args[0]);

        File file = new File(args[0]);
        String arg2 = null;
        Integer graphCount = Integer.MAX_VALUE;
        if (args.length == 2) {
        	arg2 = args[1];
        	graphCount = Integer.parseInt(arg2);
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        int x = 1;
        while ((st = br.readLine()) != null && graphCount > 0) {
        	int len = Integer.parseInt(st);
            if(len == 0){
                System.out.println("\n The graph size is zero");
                break;
            }
        	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        	ArrayList<Edge> edges = new ArrayList<Edge>();
            int [][] mat = new int[len][len];
            for (Integer k = 0; k < len; k++) {
            	String str = br.readLine();
            	String[] line = str.split(" ");
            	vertices.add(new Vertex(k));
            	for (int j = 0; j < line.length; j++) {
            		mat[k][j] = Integer.parseInt(line[j]);
            		if (k == j)
            			mat[k][j] = 0;
                    if (mat[k][j] == 1) {
                    	edges.add(new Edge(k, j));
                    }
            	}
            }
//            if (x == 100) {
            	Graph graph = new Graph(vertices, edges);
                VertexCover vc = new VertexCover(graph);
                Integer k = vc.greedyCover(x, false);
                vc.setK(k);
                vc.greedyCoverExp(x, true);
//            }
            graphCount--;
            x++;
        }
        br.close();

	}

}
