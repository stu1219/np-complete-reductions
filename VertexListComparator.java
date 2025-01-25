import java.util.Comparator;

public class VertexListComparator implements Comparator<Vertex> {

	public int compare(Vertex v1, Vertex v2) {
        if (v1.getValue() == v2.getValue()) {
            return 0;
        }
        else if (v1.getValue() > v2.getValue()) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
