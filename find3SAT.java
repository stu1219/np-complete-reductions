import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class find3SAT {

	public static void main(String[] args) throws IOException {
		System.out.println(args[0]);

        File file = new File(args[0]);
        boolean flag = true;
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
        	String[] line = st.split(" ");
        	Instant startTime = Instant.now();
        	Map<Integer, String> variables = new HashMap<Integer, String>();
        	ArrayList<Integer> vals = new ArrayList<Integer>();
        	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        	ArrayList<Edge> edges = new ArrayList<Edge>();
        	for (int i = 0; i < line.length; i++) {
        		Integer var = Integer.parseInt(line[i]);
        		variables.put(var, "F");
        		variables.put(-var, "F");
        		vals.add(var);
        	}
        	ArrayList<Integer> keys = new ArrayList<>(variables.keySet());
        	Collections.sort(keys, (o1, o2) -> o1.compareTo(o2));
        	for (Integer i : keys) {
        		vertices.add(new Vertex((keys.size() / 2) + i - (i > 0 ? 1 : 0)));
        		edges.add(
					new Edge(
						(keys.size() / 2) + i - (i > 0 ? 1 : 0),
						(keys.size() / 2) - i - (-i > 0 ? 1 : 0)
					)
    			);
        	}
        	ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
        	for (int i = 0; i < line.length; i = i + 3) {
        		ArrayList<Integer> clause = new ArrayList<Integer>();
        		for (int j = 0; j < 3; j++) {
        			Integer val = vals.get(i + j);
        			clause.add(val);
        			vertices.add(new Vertex(keys.size() + i + j));
        			edges.add(
    					new Edge(
							(keys.size() / 2) + val - (val > 0 ? 1 : 0),
							keys.size() + i + j
    					)
        			);
        			edges.add(
    					new Edge(
    						keys.size() + i + j,
							(keys.size() / 2) + val - (val > 0 ? 1 : 0)
    					)
        			);
        			edges.add(
    					new Edge(
    						keys.size() + i + j,
							keys.size() + i + ((j + 1) % 3)
    					)
        			);
        			edges.add(
        					new Edge(
        						keys.size() + i + ((j + 1) % 3),
        						keys.size() + i + j
        					)
            			);
        		}
        		clauses.add(clause);
        	}
        	
        	Integer k = (variables.keySet().size() / 2) + (2 * clauses.size());
        	
        	Graph graph = new Graph(vertices, edges);
            VertexCover vc = new VertexCover(graph);
        	System.out.println();
        	Integer K = vc.greedyCover(x, false);
        	vc.setK(K);
            vc.greedyCoverExp(x, false);
            ArrayList<Vertex> cover = vc.getCover();
            System.out.println();
            for (Vertex v : cover) {
            	if (v.getValue() < keys.size()) {
            		variables.put(keys.get(v.getValue()), "T");
            	}
            }
            
//          3CNF No.6:[n=4 k=5]->[V=23, E=34, k=14](0 ms) Solution:[1:F 2:F 3:F 4:T]
//          (-2|-3|-2)∧( 1| 3| 4)∧( 2|-1|-1)∧( 3|-3| 2)∧(-4|-1|-4) ==>
//          ( T| T| T)∧( F| F| T)∧( F| T| T)∧( F| T| F)∧( F| T| F)
            
            Instant endTime = Instant.now();
            System.out.print("3CNF No." + x + ":[n=" + keys.size() / 2 + " k=" + clauses.size() + "]->");
            System.out.print("[V=" + vertices.size() + ", E=" + edges.size() / 2 + ", k=" + k + "(" + Duration.between(startTime,endTime).toMillis() + " ms) ");
            if(cover.size() == k){
//				System.out.println("answer");
			} else {
				System.out.print("  No Solution  ");
				flag = false;
			}
			if(flag == true){
				System.out.print("Solution: [");
			} else {
				System.out.print("Random: [");
				flag = true;
			}
            for (Integer i = 0; i < keys.size() / 2; i++) {
            	if (i == (keys.size() / 2) - 1) {
            		System.out.print((i + 1) + ":" + variables.get(i + 1));
            	} else {
            		System.out.print((i + 1) + ":" + variables.get(i + 1) + " ");
            	}
            }
            System.out.println("]");
            for (int i = 0; i < clauses.size(); i++) {
            	ArrayList<Integer> c = clauses.get(i);
            	System.out.print("(");
            	for (int j = 0; j < 3; j++) {
            		if (j != 2) {
            			System.out.print(c.get(j) + "|");
            		} else {
            			System.out.print(c.get(j));
            		}
            	}
            	System.out.print(")");
            	if (i != clauses.size() - 1) {
            		System.out.print("∧");
            	} else {
            		System.out.println(" ==>");
            	}
            }
            for (int i = 0; i < clauses.size(); i++) {
            	ArrayList<Integer> c = clauses.get(i);
            	System.out.print("(");
            	for (Integer j = 0; j < 3; j++) {
            		if (j != 2) {
            			System.out.print(variables.get(c.get(j)) + "|");
            		} else {
            			System.out.print(variables.get(c.get(j)));
            		}
            	}
            	System.out.print(")");
            	if (i != clauses.size() - 1) {
            		System.out.print("∧");
            	} else {
            		System.out.println("");
            	}
            }
//        	System.out.println("-----------");
        	x++;
        	graphCount--;
        }
        br.close();
	}

}
