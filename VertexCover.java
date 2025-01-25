import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VertexCover {
	private Graph graph;
	private int k;
	private ArrayList<Vertex> cover;
	protected void setK(Integer k) {
		this.k = k;
	}
	
	public VertexCover(Graph graph) {
		this.graph = graph;
	}
	
	public ArrayList<Vertex> getCover() {
		return this.cover;
	}
	
	
	public void printVetexList(ArrayList<Vertex> vertices) {
//		System.out.println();
		System.out.print("{");
		for (int i = 0; i < vertices.size() - 1; i++) {
			System.out.print(vertices.get(i).getValue() + ",");
		}
		System.out.print(vertices.get(vertices.size() - 1).getValue());
		System.out.print("}");
//		System.out.println();
	}
	
	protected void removeEdges(Vertex vertex, ArrayList<Edge> edgeList) {
		ArrayList<Edge> removeEdge = new ArrayList<Edge>();
		for (int i = 0; i < edgeList.size(); i++) {
			if ((edgeList.get(i)).hasVertex(vertex))
				removeEdge.add(edgeList.get(i));
		}
		for (int i = 0; i < removeEdge.size(); i++)
			edgeList.remove(removeEdge.get(i));
	}
	
	protected Vertex getMaxDegree(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
		Map<Vertex, Integer> map = new HashMap<Vertex, Integer>();
		int max = 0;
		Vertex maxVertex = new Vertex();
		for (Vertex vertex : vertices) {
			map.put(vertex,  0);
			for (Edge edge : edges) {
				if (edge.hasVertex(vertex)) {
					map.put(vertex,map.get(vertex) + 1);
				}
			}
			if (map.get(vertex) > max) {
				max = map.get(vertex);
				maxVertex = vertex;
			}
		}
		return maxVertex;
	}
	
	protected ArrayList<Vertex> getVertexEdges(Vertex v, ArrayList<Edge> edges) {
		ArrayList<Vertex> connectedTov = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.getVertex1().getValue() == v.getValue()) {
				connectedTov.add(edge.getVertex2());
			}
		}
		return connectedTov;
	}
	
	
	protected boolean isCover() {
		ArrayList<Edge> edges = this.graph.getEdges();
		for (int i = 0; i < this.cover.size(); i++) {
			this.removeEdges(this.cover.get(i), edges);
		}
		if (edges.isEmpty()) {
			System.out.println("Is valid cover");
			return true;
		}
		return false;
	}
	
	protected ArrayList<Vertex> getVertexDegree(ArrayList<Edge> edges) {
		Map<Vertex, Integer> map = new HashMap<Vertex, Integer>();
		int max = 0;
		for (Vertex vertex : this.graph.getVertices()) {
			map.put(vertex,  0);
			for (Edge edge : edges) {
				if (edge.hasVertex(vertex)) {
					map.put(vertex,map.get(vertex) + 1);
				}
			}
			if (map.get(vertex) > max) {
				max = map.get(vertex);
			}
		}
		ArrayList<Vertex> maxDegreeVertices = new ArrayList<Vertex>(); 
		for (Vertex v : map.keySet()) {
			if (max == map.get(v)) {
				maxDegreeVertices.add(v);
			}
		}
		return maxDegreeVertices;
	}
	
	protected int greedyCover(Integer currentGraph, Boolean shouldPrint) {
		Instant startTime = Instant.now();
		ArrayList<Edge> edges = graph.getEdges();
		ArrayList<Vertex> vertices = graph.getVertices();
		cover = new ArrayList<Vertex>();
		graph.toString();
		while (!edges.isEmpty()) {
			ArrayList<Vertex> degree = new ArrayList<Vertex>();
			degree = getVertexDegree(edges);
			if (degree.size() == 1) {
				cover.add(degree.get(0));
				vertices.remove(degree.get(0));
				this.removeEdges(degree.get(0), edges);
			} else if (degree.size() > 1) {
				Vertex choose = null;
				for (Vertex v : degree) {
					Integer found = 0;
					Integer len = 0;
					Integer cnt = 0;
					for (int i = 0; i < edges.size(); i++) {
						Edge currentEdge = edges.get(i);
						if (currentEdge.getVertex1().getValue() == v.getValue()) {
							for (int j = 0; j < cover.size(); j++) {
								if (currentEdge.getVertex2().getValue() == cover.get(j).getValue()) {
									cnt++;
								}
							}
						}
					}
					for (int i = 0; i < edges.size(); i++) {
						Edge currentEdge = edges.get(i);
						if (currentEdge.getVertex1().getValue() == v.getValue()) {
							len++;
							for (int j = i + 1; j < edges.size(); j++) {
								if (currentEdge.getVertex2().getValue() == edges.get(j).getVertex1().getValue()) {
									if (currentEdge.getVertex1().getValue() == edges.get(j).getVertex2().getValue()) {
										found++;
										break;
									}
								}
							}
						}
					}
					if (found == len) {
						
					} else {
						if (cnt < len - found) {
							choose = v;
						}
					}
				}
				if (choose == null) {
					choose = degree.get(1);
				}
				cover.add(choose);
				this.removeEdges(choose, edges);
			}
		}
		
		Collections.sort(this.cover, new VertexListComparator());
		
		if (shouldPrint) {
			System.out.println();
			System.out.print("Greedy Cover ");
			Instant endTime = Instant.now();
			System.out.print("G" + currentGraph + " " + "(" + this.graph.edgesSize() + ", " + this.graph.verticesSize());
			System.out.print(" size" + this.cover.size() + " ms=" + Duration.between(startTime,endTime).toMillis() + " ");
			this.printVetexList(this.cover);
			System.out.println();
		}
		return cover.size();
	}
	
	protected void greedyCoverExp(Integer currentGraph, boolean shouldPrint) {
		Instant startTime = Instant.now();
		ArrayList<Edge> edges = graph.getEdges();
		ArrayList<Vertex> vertices = graph.getVertices(); 
		this.cover = new ArrayList<Vertex>();
		boolean[] visit = new boolean[graph.getVertices().size()];
		for(int i = 0; i < graph.getVertices().size(); i++) {
			visit[i] = false;
		}
		ArrayList<Vertex> initialCover = new ArrayList<Vertex>();
		this.greedyCoverExp(edges, vertices, this.k, initialCover, visit);
		Collections.sort(this.cover, new VertexListComparator());
		Instant endTime = Instant.now();
//		G1 ( 4, 2) (size=1 ms=0) {0}
		if (shouldPrint) {
			System.out.println();
//			System.out.print("Greedy Cover Optimised ");
			System.out.print("G" + currentGraph + " " + "(" + this.graph.verticesSize() + ", " + this.graph.edgesSize() + ")");
			System.out.print(" (size=" + this.cover.size() + " ms=" + Duration.between(startTime,endTime).toMillis() + ") ");
			this.printVetexList(this.cover);
//			this.isCover();
			System.out.println();
		}
		return;
	}
	
	protected void removeVertex(ArrayList<Vertex> vertices, Vertex vertex) {
		int idx = -1;
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getValue() == vertex.getValue()) {
				idx = i;
			}
		}
		if (idx != -1) {
			vertices.remove(idx);
		}
	}
	
	
	protected boolean greedyCoverExp(ArrayList<Edge> edges, ArrayList<Vertex> vertices, Integer k, ArrayList<Vertex> currentCover, boolean[] visit) {
		if (currentCover.size() > this.k) {
			return false;
		}
		if (edges.isEmpty() && k >= 0) {
			if (currentCover.size() > 0 && currentCover.size() <= this.k) {
				if (this.cover.size() == 0) {
//					do nothing
				} else if (this.cover.size() > currentCover.size()) {
//					do nothing
				}
				if (this.cover.size() == 0 || (this.cover.size() != 0 && this.cover.size() > currentCover.size())) {
//					System.out.println("found cover " + k);
					this.cover.clear(); 
					this.cover.addAll(currentCover);
				}
			}
			return true;
		}
		if (k <= 0) {
			return false;
		}
		
		
		Vertex maxDegVer = getMaxDegree(vertices, edges);
		ArrayList<Edge> tem = new ArrayList<Edge>(edges);
		ArrayList<Vertex> ver = new ArrayList<Vertex>(vertices);
		currentCover.add(maxDegVer);
		this.removeEdges(maxDegVer, tem);
		ver.remove(maxDegVer);
		
		
//		include max degree vertex
		greedyCoverExp(tem, ver, k - 1, currentCover, visit); // first call
		
		this.removeVertex(currentCover, maxDegVer);
		tem.clear();
		ver.clear();
		ArrayList<Edge> tem2 = new ArrayList<Edge>(edges);
		ArrayList<Vertex> ver2 = new ArrayList<Vertex>(vertices);
		ArrayList<Vertex> connectedToMaxDegVer = this.getVertexEdges(maxDegVer, tem2);
		if (connectedToMaxDegVer.size() == 0) {
			return false;
		}
		ArrayList<Vertex> added = new ArrayList<Vertex>();
		for (int i = 0; i < connectedToMaxDegVer.size(); i++) {
			this.removeEdges(connectedToMaxDegVer.get(i), tem2);
			boolean dontAdd = false;
			for (int j = 0; j < currentCover.size(); j++) {
				if (currentCover.get(j).getValue() == connectedToMaxDegVer.get(i).getValue()) {
					dontAdd = true;
				}
			}
			if (!dontAdd) {
				added.add(connectedToMaxDegVer.get(i));
				currentCover.add(connectedToMaxDegVer.get(i));
			}
		}
//		include max degree edges
		greedyCoverExp(tem2, ver2, k - connectedToMaxDegVer.size(), currentCover, visit);
		for (int i = 0; i < added.size(); i++) {
			
			for (int j = 0; j < currentCover.size(); j++) {
				if (currentCover.get(j).getValue() == added.get(i).getValue()) {
					currentCover.remove(j);
				}
			}
		}
		return false;
	}
	
	protected void getClique(Integer currentGraph) {
		Instant startTime = Instant.now();
		this.greedyCoverExp(currentGraph, false);
		ArrayList<Vertex> vertices = this.graph.getVertices();
		ArrayList<Vertex> clique = new ArrayList<Vertex>();
		for (int i = 0; i < this.graph.verticesSize(); i++) {
			boolean notFound = true;
			for (int j = 0; j < this.cover.size(); j++) {
				if (this.cover.get(j).getValue() == vertices.get(i).getValue()) {
					notFound = false;
				}
			}
			if (notFound) {
				clique.add(vertices.get(i));
			}
		}
		Collections.sort(clique, new VertexListComparator());
		Instant endTime = Instant.now();
		System.out.println();
		System.out.print("Clique ");
		System.out.print("G" + currentGraph + " " + "(" + this.graph.verticesSize() + ", " + this.graph.edgesSize() + ")");
		System.out.print(" (size=" + clique.size() + " ms=" + Duration.between(startTime,endTime).toMillis() + ") ");
		this.printVetexList(clique);
//		this.isCover();
		System.out.println();
	}


}
