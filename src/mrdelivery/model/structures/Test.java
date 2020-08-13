package mrdelivery.model.structures;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        Vertice v1 = new Vertice("A");
        Vertice v2 = new Vertice("B");
        Vertice v3 = new Vertice("C");
        Vertice v4 = new Vertice("D");
        Arista a1 = new Arista(v1,v2,true,20,2.7,5);
        Arista a2 = new Arista(v2,v3,true,20,2.7,3);
        Arista a3 = new Arista(v1,v3,true,20,2.7,1);
        Arista a4 = new Arista(v3,v4,true,20,2.7,6);
        Arista a5 = new Arista(v4,v1,true,20,2.7,4);
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        v1.addArista(a1);
        v2.addArista(a2);
        v1.addArista(a3);
        v3.addArista(a4);
        v4.addArista(a5);
        ArrayList<Arista> aristas = new ArrayList<Arista>();
        aristas.add(a1);
        aristas.add(a2);
        aristas.add(a3);
        aristas.add(a4);
        aristas.add(a5);
        Grafo grafo = new Grafo(vertices,aristas);
//        System.out.println("Todos los caminos");
//        ArrayList<Camino> caminos = grafo.todosLosCaminos(v1,v4);
//        for (Camino camino:caminos){
//            camino.imprimirCamino();
//            System.out.println();
//        }
        grafo.caminosMinimos(v1,0);
    }

}
