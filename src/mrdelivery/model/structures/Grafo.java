package mrdelivery.model.structures;

import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {

    ArrayList<Vertice> vertices;
    ArrayList<Arista> aristas;
    Arista[][] representacionMatriz;

    public Grafo(ArrayList<Vertice> vertices,ArrayList<Arista> aristas){
        this.vertices = vertices;
        this.aristas = aristas;
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
        this.representacionMatriz = new Arista[vertices.size()][vertices.size()];
        for (Vertice vertice:vertices) {
            for (Arista arista : vertice.aristas) {
                if (arista.isActivo())
                    representacionMatriz[indexVertice(arista.origen)][indexVertice(arista.destino)] = arista;
            }
        }
    }

    public ArrayList<CaminoVertices> todosLosCaminos(Vertice inicio, Vertice destino){
        ArrayList<CaminoVertices> caminoVertices = new ArrayList<>();
        return buscarCamino(inicio,inicio,destino, caminoVertices,new CaminoVertices(),true);
    }

    private ArrayList<CaminoVertices> buscarCamino(Vertice original, Vertice inicio, Vertice destino, ArrayList<CaminoVertices> caminos, CaminoVertices camino, boolean primero) {
        System.out.println(inicio.nombre);
        // Caso en que el origen y el destino sean iguales, no se permiten lazos
        if(!primero && (inicio == original)) {
            return null;
        }
        camino.addCamino(inicio);
        if (inicio == destino) {
            caminos.add(camino);
            return null;
        }
        for(Arista arista: inicio.aristas){
            if(arista.activo)
                buscarCamino(original,arista.destino,destino, caminos,new CaminoVertices(camino),false);
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

    public boolean esConexo(){
        for (Vertice vertice : vertices){
            // Hay que verificar que se puede llegar a todos los demas vertices
            ArrayList<Vertice> conexos = new ArrayList<>();
            conexos.add(vertice);
            return tieneCaminosConTodos(vertice,conexos);
        }
        return true;
    }

    private boolean tieneCaminosConTodos(Vertice actual,ArrayList<Vertice> conexos){
        if (vertices.size() == conexos.size())
            return true;
        for (Arista arista : actual.aristas){
            if (buscarVertice(arista.destino,conexos) == null){
                conexos.add(arista.destino);
                return tieneCaminosConTodos(arista.destino,conexos);
            }
        }
        return false;
    }

    private void reestablecerVisitados(){
        for (Vertice vertice : vertices)
            vertice.visitado = false;
    }

    public Vertice getMinimo(Vertice origen, HashMap<Vertice,CaminoAristas> minimos){
        double menor = Double.MAX_VALUE;
        Vertice verticeConCaminoMinimo = null;
        for (Vertice vertice : minimos.keySet()) {
            // Se valida que sea la distancia minima desde el origen, no puede ser el mismo
            // origen porque la distancia desde este siempre es 0.
            if ((menor > minimos.get(vertice).getDistanciaTotal()) && !vertice.equals(origen)) {
                menor = minimos.get(vertice).getDistanciaTotal();
                verticeConCaminoMinimo = vertice;
            }
        }
        return verticeConCaminoMinimo;
    }

    public HashMap<Vertice,CaminoAristas> caminosMinimos(Vertice origen, int tipoPonderacion){

            HashMap<Vertice,CaminoAristas> minimos = new HashMap<>();
            // Se prepara la lista de adyacencia de distancias minimas
            for (Vertice vertice : vertices) {
                if (vertice.equals(origen))
                    minimos.put(vertice,new CaminoAristas(0.0));
                else
                    minimos.put(vertice, new CaminoAristas(Double.MAX_VALUE));
            }
            // Algoritmo de Dijkstra
            int i = 0;
            Vertice actual = origen;
            double distanciaAcumulada = 0.0;
            double nuevaDistancia = 0.0;

            while (i < vertices.size()) {
                actual.setVisitado(true);
                for (Arista arista : actual.aristas){
                    if (!arista.destino.visitado){
                        distanciaAcumulada = minimos.get(arista.destino).getDistanciaTotal();
                        nuevaDistancia = minimos.get(actual).getDistanciaTotal() + arista.getPonderacion(tipoPonderacion);
                        if (nuevaDistancia < distanciaAcumulada)
                        {
                            minimos.get(arista.destino).addCamino(arista);
                            minimos.get(arista.destino).setDistanciaTotal(nuevaDistancia);  // Se actualiza la distancia desde el origen al vertice
                        }
                    }
                }
                actual = getMinimo(origen, minimos);
                i++;
            }
            //TODO: Impresion para desarrollo, borrar luego
            System.out.println("RESULTADO FINAL DE LA TABLA - ALGORITMO DE DIJKSTRA");
            for (Vertice vertice : minimos.keySet()) {
                System.out.println(vertice.nombre + " " + minimos.get(vertice).getDistanciaTotal());
            }
            return minimos;
    }


}
