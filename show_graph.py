try:
    import matplotlib as plt
    import networkx as nx
except ImportError:
    exit("Please install networkx and matplotlib in order to run this script, (pip install matplotlib networkx)")

isDirected = input("Is the graph directed ? (y/n)")

isDirected = isDirected == 'y'

G = None
if(isDirected):
    G = nx.DiGraph()
else:
    G = nx.Graph()

nEdges = input("Enter the number of edges: ")

for i in range(nEdges):
    a = input("Enter edge " + (i+1) + " a: ")
    b = input("Enter edge " + (i+1) + " b: ")
    G.add_edge(a, b)

nx.draw(G)
# plt.show()
# plt.savefile("graph.png")