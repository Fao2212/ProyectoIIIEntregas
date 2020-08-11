package mrdelivery.model.structures;

import netscape.javascript.JSObject;

import java.util.ArrayList;

public class Grafo {

    ArrayList<Vertice> vertices;
    ArrayList<Arista> aristas;
    Arista[][] representacionMatriz;

    public Grafo(ArrayList<Vertice> vertices,ArrayList<Arista> aristas){
        this.vertices = vertices;
        grafoAMatriz();
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

    public void grafoAMatriz(){//Cuidar aristas repetidas? setear a null los vertices que no existen?
        for (Vertice vertice:vertices) {
            for (Arista arista : aristas) {
                if (arista.isActivo())
                    representacionMatriz[indexVertice(arista.origen)][indexVertice(arista.destino)] = arista;
            }
        }
    }

    public ArrayList<Camino> todosLosCaminos(Vertice inicio,Vertice destino){
        ArrayList<Camino> caminos = new ArrayList<>();
        return buscarCamino(inicio,inicio,destino,caminos,new Camino(),true);
    }

    private ArrayList<Camino> buscarCamino(Vertice original,Vertice inicio, Vertice destino, ArrayList<Camino> caminos, Camino camino,boolean first) {
        if(!first && inicio == original)
            return null;
        camino.addCamino(inicio);
        if (inicio == destino) {//Hace falta probar y la condicion inicio == null
            caminos.add(camino);
        }
        for(Arista arista: inicio.aristas){
            if(arista.activo)
                buscarCamino(original,arista.destino,destino,caminos,new Camino(camino),false);
        }
        return caminos;
    }
    //Toma el origen y encola todas las aristas o vertices
    //Agrega al camino y busca en el siguiente vertice
    //Al llegar a un final(Null o nodo objetivo)

    private void ordenarCaminos(int index/*Peso peso*/){

    }

    private Vertice buscarVertice(Vertice vertice,ArrayList<Vertice> vertices){
        for(Vertice ver:vertices){
            if(ver.nombre.equals(vertice.nombre))
                return ver;
        }
        return null;
    }

    private int indexVertice(Vertice vertice){
        for(Vertice ver:vertices){
            if(ver.equals(vertice))
                return vertices.indexOf(ver);
        }
        return -1;
    }

}
