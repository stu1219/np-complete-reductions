public class Edge {
	private Vertex vertex1;
	private Vertex vertex2;
		
	public Edge(int vertex1, int vertex2) {
		this.vertex1 = new Vertex(vertex1);
		this.vertex2 = new Vertex(vertex2);
	}
	
	public Vertex getVertex1() {
		return this.vertex1;	
	}
	
	public Vertex getVertex2() {
		return this.vertex2;
	}
	
	public boolean hasVertex(Vertex vertex) {
		if (this.vertex1.getValue() == vertex.getValue() || this.vertex2.getValue() == vertex.getValue())
			return true; 
		else
			return false;
	}
	
	public String toString() {
		return "(" + vertex1 + ", " + vertex2 + ")";
	}
}
