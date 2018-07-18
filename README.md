## Welcome to Jtops

Jtops is a topological mesh operators library developed in JAVA and based upon :
[Topological Mesh Operators](https://www.visgraf.impa.br/Data/RefBib/PS_PDF/cagd-tops/tops-rev2.pdf)
by Thomas Lewiner, Hélio Lopes, Esdras Medeiros, Geovan Tavares and Luiz Velho.

## Some "theoretical" forewords

Jtops is a framework for manipulation of triangulated 2-manifold with or without boundaries. Which basically means, Jtop allow you to manipulate mesh made of triangulated 2D surfaces not necessarily closed (boundaries). The 2-manifold condition means that two faces (triangles) of the mesh can only be either disconnected or connected through an edge. Better than long explanations some drawings can help understand this fundamental condition.  

Here is the most simple possible mesh:


![the most simple mesh](/doc/images/img2.png)


It is made of a single triangle: 3 vertices connected by edges. The edges define a "loop" (for instance A to B, then B to C and then C to A). This loop is closed and defines an "interior" and an exterior. We need a convention to know where the interior is: we orient our loop counter clockwise. So this triangle is ABC.

As it is, all edges here are boundary edges: on their "left" there is a face and on their "right" there is nothing.

Here is another example where the edge (e) is an internal edge. This mesh is made of two triangles and has one boundary (edges b1, b2, b3, b4)


![2 triangles](/doc/images/img3.png)


Another example would be the cube where, amazingly enough, there is no boudary :-) as all edges have a face on their left and right. The mesh is fully closed. Here represented with non triangular faces for simplicity.


![cube](/doc/images/img4.png)


We can also easily create several different boundaries :


![multiple boundaries](/doc/images/img5.png)

As mentionned previously some mesh would not be 2-manifold (and cannot be handled with Jtop)... for instance a mesh where two faces are connected by a single vertex, or a mesh where more than two faces share a common edge:


![non 2-manifold](/doc/images/img6.png)


## The half-edge data structure

To store conveniently such a mesh we need a dedicated data structure. A pretty popular one is the half-edge. An edge is connecting two vertices but is not oriented. In order to provide orientation to a face we need some sort of oriented edge. That's were the idea of spliting an edge into two oriented half edge comes handy. Our ABC triangle would look like this:

![half edge](/doc/images/img7.png)



We also a minimal set of operators.
