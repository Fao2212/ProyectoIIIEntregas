package mrdelivery.model.structures;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        Vertice v1 = new Vertice("A");
        Vertice v2 = new Vertice("B");
        Vertice v3 = new Vertice("C");
        Vertice v4 = new Vertice("D");
//        Vertice v5 = new Vertice("E");
//        Vertice v6 = new Vertice("F");
        Arista a1 = new Arista(v1,v2,true,5,5,5);
        Arista a2 = new Arista(v2,v3,true,8,8,3);
        Arista a3 = new Arista(v3,v1,true,12,12,1);
        Arista a4 = new Arista(v1,v3,true,7,7,6);
        Arista a5 = new Arista(v2,v4,true,4,4,4);
        Arista a6 = new Arista(v3,v4,true,9,9,5);
        Arista a7 = new Arista(v4,v1,true,10,10,3);
//        Arista a8 = new Arista(v3,v6,true,13,13,1);
//        Arista a9 = new Arista(v4,v6,true,15,15,6);
//        Arista a10 = new Arista(v5,v6,true,2,2,4);
//        Arista a11 = new Arista(v2,v1,true,5,5,5);
//        Arista a12 = new Arista(v3,v2,true,8,8,3);
//        Arista a13 = new Arista(v4,v3,true,12,12,1);
//        Arista a14 = new Arista(v5,v4,true,7,7,6);
//        Arista a15 = new Arista(v1,v5,true,4,4,4);
//        Arista a16 = new Arista(v6,v1,true,9,9,5);
//        Arista a17 = new Arista(v6,v2,true,10,10,3);
//        Arista a18 = new Arista(v6,v3,true,13,13,1);
//        Arista a19 = new Arista(v6,v4,true,15,15,6);
//        Arista a20 = new Arista(v6,v5,true,2,2,4);
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
//        vertices.add(v5);
//        vertices.add(v6);
        v1.addArista(a1);
        v2.addArista(a2);
        v3.addArista(a3);
        v1.addArista(a4);
        v2.addArista(a5);
        v1.addArista(a6);
        v4.addArista(a7);
//        v1.addArista(a6);
//        v2.addArista(a7);
//        v3.addArista(a8);
//        v4.addArista(a9);
//        v5.addArista(a10);
//        v2.addArista(a11);
//        v3.addArista(a12);
//        v4.addArista(a13);
//       v5.addArista(a14);
//        v1.addArista(a15);
//        v6.addArista(a16);
//        v6.addArista(a17);
//        v6.addArista(a18);
//        v6.addArista(a19);
//        v6.addArista(a20);
        ArrayList<Arista> aristas = new ArrayList<Arista>();
        aristas.add(a1);
        aristas.add(a2);
        aristas.add(a3);
        aristas.add(a4);
        aristas.add(a5);
        aristas.add(a6);
        aristas.add(a7);
//        aristas.add(a8);
//        aristas.add(a9);
//        aristas.add(a10);
//        aristas.add(a11);
//        aristas.add(a12);
//        aristas.add(a13);
//        aristas.add(a14);
//        aristas.add(a15);
//        aristas.add(a16);
//        aristas.add(a17);
//        aristas.add(a18);
//        aristas.add(a19);
//        aristas.add(a20);
        Grafo grafo = new Grafo(vertices,aristas);
//        System.out.println("Todos los caminos");
//        ArrayList<Camino> caminos = grafo.todosLosCaminos(v1,v4);
//        for (Camino camino:caminos){
//            camino.imprimirCamino();
//            System.out.println();
//        }

        //Prueba prim
        grafo.prim();
        ArrayList<CaminoAristas> lca = grafo.todosLosCaminos(v1,v3);
        System.out.println(lca);
        for (CaminoAristas camino:lca){
            System.out.println("Camino");
            for (Arista arista: camino.camino){
                System.out.println(arista.toStringConexion());
            }
        }

        //Prueba profundidad
       /* CaminoAristas ca = grafo.recorridoEnAnchura(v3);
        for(Arista arista:ca.camino){
            System.out.println(arista.toStringConexion());
        }*/
    }

}
