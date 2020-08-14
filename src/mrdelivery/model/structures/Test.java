package mrdelivery.model.structures;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        Vertice v1 = new Vertice("A");
        Vertice v2 = new Vertice("B");
        Vertice v3 = new Vertice("C");
        Vertice v4 = new Vertice("D");
        Vertice v5 = new Vertice("E");
        Vertice v6 = new Vertice("F");
        Arista a1 = new Arista(v1,v2,true,5,2.7,5);
        Arista a2 = new Arista(v2,v3,true,8,2.7,3);
        Arista a3 = new Arista(v3,v4,true,12,2.7,1);
        Arista a4 = new Arista(v4,v5,true,7,2.7,6);
        Arista a5 = new Arista(v5,v1,true,4,2.7,4);
        Arista a6 = new Arista(v1,v6,true,9,2.7,5);
        Arista a7 = new Arista(v2,v6,true,10,2.7,3);
        Arista a8 = new Arista(v3,v6,true,13,2.7,1);
        Arista a9 = new Arista(v4,v6,true,15,2.7,6);
        Arista a10 = new Arista(v5,v6,true,2,2.7,4);
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        vertices.add(v5);
        vertices.add(v6);
        v1.addArista(a1);
        v2.addArista(a2);
        v3.addArista(a3);
        v4.addArista(a4);
        v5.addArista(a5);
        v1.addArista(a6);
        v2.addArista(a7);
        v3.addArista(a8);
        v4.addArista(a9);
        v5.addArista(a10);
        ArrayList<Arista> aristas = new ArrayList<Arista>();
        aristas.add(a1);
        aristas.add(a2);
        aristas.add(a3);
        aristas.add(a4);
        aristas.add(a5);
        aristas.add(a6);
        aristas.add(a7);
        aristas.add(a8);
        aristas.add(a9);
        aristas.add(a10);
        Grafo grafo = new Grafo(vertices,aristas);
//        System.out.println("Todos los caminos");
//        ArrayList<Camino> caminos = grafo.todosLosCaminos(v1,v4);
//        for (Camino camino:caminos){
//            camino.imprimirCamino();
//            System.out.println();
//        }

        //Prueba prim
        //grafo.prim(Peso.DISTANCIA);
        /*for (Vertice vertice: grafo.vertices){
            if(vertice.visitado)
                System.out.println(vertice.nombre);
        }
        for (Arista arista: grafo.aristas){
            if(arista.activo)
                System.out.println(arista.toStringToolTip());
        }*/
        //Prueba profundidad
        CaminoAristas ca = grafo.recorridoEnProfundidad(v1);
        for(Arista arista:ca.camino){
            System.out.println(arista.toStringConexion());
        }
    }

}
