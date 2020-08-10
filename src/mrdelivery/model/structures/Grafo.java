package mrdelivery.model.structures;

import netscape.javascript.JSObject;

import java.util.ArrayList;

public class Grafo {

    ArrayList<Vertice> vertices;
    ArrayList<Arista> aristas;
    int[][] representacionMatriz;

    Grafo(ArrayList<Vertice> vertices,ArrayList<Arista> aristas){
        this.vertices = vertices;
    }

    public Grafo clonarGrafo(){
        ArrayList<Vertice> copiaVertices = new ArrayList<>();
        ArrayList<Arista> copiaAristas = new ArrayList<>();
        for (Vertice vertice:vertices){
            copiaVertices.add(new Vertice(vertice));
        }
        for(Arista arista:aristas){
            Vertice clonOrigen = buscarVertice(arista.origen,copiaVertices);
            Vertice clonDestino = buscarVertice(arista.destino,copiaVertices);
            Arista copiaArista = new Arista(clonOrigen,clonDestino,arista.activo, arista.distancia, arista.tiempo, arista.precio);
            clonDestino.addArista(copiaArista);
            clonOrigen.addArista(copiaArista);
            copiaAristas.add(copiaArista);
        }
        return new Grafo(copiaVertices,copiaAristas);
    }

    public void grafoAMatriz(){
        int size = vertices.size();
        this.representacionMatriz = new int[size][size];
        for (int i = 0;i<size;i++){
            for (int j = 0;i<size;i++){
                representacionMatriz[i][j] = realcion(vertices.get(i),vertices.get(j));
            }
        }
    }

    private int realcion(Vertice vertice1, Vertice vertice2) {
        if(vertice1.buscarCamino(vertice2))
            return 1;
        else
            return 0;
    }

    private Vertice buscarVertice(Vertice vertice,ArrayList<Vertice> vertices){
        for(Vertice ver:vertices){
            if(ver.nombre.equals(vertice.nombre))
                return ver;
        }
        return null;
    }

}
