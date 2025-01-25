# NP-Complete Problem Reductions

Implementation of polynomial-time reductions between NP-complete problems (3SAT, Vertex Cover, Clique), demonstrating practical applications of theoretical computer science concepts.

## Problem Overview

The project implements three key reductions:
- 3SAT ≤P Vertex-Cover 
- Clique ≤P Vertex-Cover
- Vertex Cover optimization

## Implementation Details

### Vertex Cover Solver
- Optimized recursive algorithm combining greedy and brute-force approaches
- Dynamic k-value bounding using greedy initial estimates
- Efficient handling of large graphs

### Graph Data Structure
- ArrayList-based implementation
- Edge and vertex manipulation methods
- Degree calculation optimizations

### Key Methods
- `GreedyCoverExponential`: Main recursive solver
- `findVCover`: k-vertex cover detection
- `greedyCover`: Initial k-value estimation
- Graph manipulation utilities (removeEdges, getMaxDegree, etc.)

## Time Complexity Optimization

The solution improves upon pure brute-force approaches by:
1. Using greedy estimates for initial bounds
2. Implementing recursive optimization
3. Efficient vertex selection strategy
## Performance Analysis
* Successfully handles graphs with 200+ vertices
* Optimized for sparse and dense graphs
* Efficient memory utilization

## References
* Michael Sipser's Algorithm Design lectures
* "Polynomial-time reduction proofs" - Tutorials Point
* Dynamic programming approaches for vertex cover

## Contributing
Pull requests welcome. For major changes, please open an issue first.
