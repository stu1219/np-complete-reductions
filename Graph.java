import java.util.ArrayList;

public class Graph {
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;

	public Graph(ArrayList<Vertex> newVertices, ArrayList<Edge> newEdges) {
		this.edges = newEdges;
		this.vertices = newVertices;
	}

	protected Vertex getVertex(int i) {
		return vertices.get(i);
	}

	protected ArrayList<Edge> getEdges() {
		ArrayList<Edge> edges = new ArrayList<Edge>();
//		System.out.print("[");
		for (Edge edge : this.edges) {
			edges.add(edge);
//			System.out.print("(" + edge.getVertex1().getValue() + "," + edge.getVertex2().getValue() + "),");
		}
//		System.out.print("]");
		return edges;
	}
	
	protected Integer edgesSize() {
		return edges.size();
	}

	protected ArrayList<Vertex> getVertices() {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for (Vertex vertex : this.vertices)
			vertices.add(vertex);
		return vertices;
	}
	
	protected Integer verticesSize() {
		return vertices.size();
	}

	public String toString() {
		return "Vertices: " + vertices.toString() + "\nEdges: " + edges.toString();
	}
}
