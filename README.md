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