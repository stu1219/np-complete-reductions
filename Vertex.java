public class Vertex {
	private int vertex;

	public Vertex() {
		super();
	}
	
	public Vertex(int vertex) {
		this.vertex = vertex;
	}
	
	protected int getValue() {
		return vertex;
	}
	
	public String toString() {
		return Integer.toString(vertex);
	}
}
