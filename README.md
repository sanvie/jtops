## Welcome to Jtops

Jtops is a topological mesh operators library developed in JAVA and based upon :
[Topological Mesh Operators](https://www.visgraf.impa.br/Data/RefBib/PS_PDF/cagd-tops/tops-rev2.pdf)
by Thomas Lewiner, Hélio Lopes, Esdras Medeiros, Geovan Tavares and Luiz Velho.

## Some "theoretical" forewords

Jtops is a framework for manipulation of triangulated 2-manifold with or without boundaries. Which basically means, Jtop allow you to manipulate mesh made of triangulated 2D surfaces not necessarily closed (boundaries). The 2-manifold condition means that two faces (triangles) of the mesh can only be either disconnected or connected through an edge. Better than long explanations some drawings can help understand this fundamental condition.  

Here is the most simple possible mesh:


![the most simple mesh](/doc/images/img1.png)


It is made of a single triangle. The triangle is made of 3 vertices connected by edges.

