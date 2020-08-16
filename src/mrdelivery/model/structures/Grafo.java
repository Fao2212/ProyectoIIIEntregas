package mrdelivery.model.structures;

import mrdelivery.view.Out;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Grafo {

    ArrayList<Vertice> vertices;
    ArrayList<Arista> aristas;
    Arista[][] representacionMatriz;
    int ponderacionActiva;
    CaminoAristas caminoPorRecorrer;
    boolean busquedaTerminada;

    public Grafo(ArrayList<Vertice> vertices,ArrayList<Arista> aristas){
        this.vertices = vertices;
        this.aristas = aristas;
        ponderacionActiva = 0;
        grafoAMatriz();
    }

    public Grafo(Grafo grafo){
        for (Vertice vertice:grafo.vertices){
            this.vertices.add(new Vertice(vertice));
        }
        for (Arista arista:grafo.aristas){
            this.aristas.add(new Arista(arista));
        }
        ponderacionActiva = 0;
        grafoAMatriz();
    }

    public void resetGrafo(ArrayList<Vertice> vertices,ArrayList<Arista> aristas){
        this.aristas = aristas;
        this.vertices = vertices;
        grafoAMatriz();
    }

    public ArrayList<Vertice> getVertices(){
        return this.vertices;
    }

    public CaminoAristas getRecorridoActual() {
        return caminoPorRecorrer;
    }

    public void setRecorridoActual(CaminoAristas recorridoActual) {
        this.caminoPorRecorrer = recorridoActual;
    }

    public Grafo clonarGrafo(){
        Grafo clon;
        ArrayList<Vertice> copiaVertices = new ArrayList<>();
        ArrayList<Arista> copiaAristas = new ArrayList<>();
        for (Vertice vertice:vertices){
            copiaVertices.add(new Vertice(vertice));
        }
        for(Arista arista:aristas){
            Vertice clonOrigen = buscarVertice(arista.origen,copiaVertices);
            Vertice clonDestino = buscarVertice(arista.destino,copiaVertices);
            Arista copiaArista = new Arista(clonOrigen,clonDestino,arista.activo, arista.precio, arista.distancia, arista.tiempo);
//            copiaArista.setGrafoAsociado(clon);
            clonOrigen.addArista(copiaArista);
            copiaAristas.add(copiaArista);
        }
        return new Grafo(copiaVertices,copiaAristas);
    }

    public void grafoAMatriz(){
        representacionMatriz = new Arista[vertices.size()][vertices.size()];
        for (Vertice vertice : vertices) {
            for (Arista arista : vertice.aristas) {
                if (arista.isActivo())
                    representacionMatriz[indexVertice(arista.origen)][indexVertice(arista.destino)] = arista;
            }
        }
        imprimirListaAdyacenciaGrafo();
    }

    public int getPonderacionActiva(){
        return ponderacionActiva;
    }
    public void setPonderacionActiva(int tipo){
        ponderacionActiva = tipo;
    }

    public void imprimirListaAdyacenciaGrafo(){
        System.out.println("LISTA DE ADYACENCIA DEL GRAFO");
        for (Vertice vertice : vertices){
            StringBuffer s = new StringBuffer();
            s.append(vertice.getNombre()).append(" ");
            for (Arista arista : vertice.aristas){
                if (arista.isActivo())
                    s.append("[").append(arista.getDestino().getNombre()+" ").append(arista.getPonderacionActual()).append("]");
                else
                    s.append("["+arista.getDestino().getNombre()+" ").append("inactivo]");
            }
            System.out.println(s);
        }
    }

    public ArrayList<CaminoAristas> todosLosCaminos(Vertice inicio, Vertice destino){
        if(inicio != destino){
        ArrayList<CaminoAristas> caminoAristas = new ArrayList<>();
        imprimirListaAdyacenciaGrafo();
        busquedaTerminada = false;
        return buscarCamino(inicio,inicio,destino, caminoAristas,new CaminoAristas(),true,null);
        }
        return null;
    }

    private ArrayList<CaminoAristas> buscarCamino(Vertice original, Vertice inicio, Vertice destino, ArrayList<CaminoAristas> caminos, CaminoAristas camino, boolean primero, Arista aristaActual) {
         //inicio.setVisitado(true);
        //resetvisitados
        System.out.println(inicio.nombre);
        if(!primero && (inicio == original)) {
            System.out.println("Encontre un igual");
            return null;
        }
        if (!primero)
            camino.addCamino(aristaActual);
        if (inicio == destino) {
            if(caminoRepetido(camino,caminos))
                busquedaTerminada = true;
            else
                caminos.add(camino);
            reestablecerVisitados();
            return null;
        }
        if (!inicio.isActivo()){ // Por si un vertice esta inactivo
            return null;
        }
        for(Arista arista: inicio.aristas){
            if(arista.activo) {
                if(!arista.destino.isVisitado() && !busquedaTerminada) {
                    inicio.setVisitado(true);
                    buscarCamino(original, arista.destino, destino, caminos, new CaminoAristas(camino), false, arista);
                }
                else{
                    System.out.println("Termina");
                }
                }
        }
        return caminos;
    }

    private boolean caminoRepetido(CaminoAristas camino,ArrayList<CaminoAristas> caminos) {
        System.out.println("REPETIDOS");
        for (CaminoAristas caminoTemp:caminos){
            if(caminoTemp.compararCamino(camino))
                return true;
        }
        return false;
    }

    public CaminoAristas caminoOptimo(Vertice origen,Vertice destino){
        ArrayList<CaminoAristas> caminos = todosLosCaminos(origen,destino);
        return Collections.min(caminos);
    }

    private void ordenarCaminos(int index,Peso peso){

    }

    private Vertice buscarVertice(Vertice vertice,ArrayList<Vertice> _vertices){
        for(Vertice ver:_vertices){
            if (ver.nombre.equals(vertice.nombre) && ver.isActivo())
                return ver;
        }
        return null;
    }

    public Vertice buscarVertice(String nombreVertice){
        if (vertices != null){
            for (Vertice vertice : vertices){
                // TODO: Verificar si se ocupa saber si el vertice esta activo siempre o si hay casos en los que no
                if (vertice.getNombre().equals(nombreVertice) && vertice.isActivo())
                    return vertice;
            }
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


    public Arista[][] getMatriz() {
        return representacionMatriz;
    }

    public boolean esConexo(){
        boolean sigueConexo;
        for (Vertice vertice : vertices){
            ArrayList<Vertice> conexos = new ArrayList<>();
            conexos.add(vertice);
            // Hay que verificar que se puede llegar a todos los demas vertices
            reestablecerVisitados();
            sigueConexo = tieneCaminosConTodos(vertice,conexos);
            if (!sigueConexo)
                return false;
        }
        reestablecerVisitados();
        return true;
    }

    private boolean tieneCaminosConTodos(Vertice actual,ArrayList<Vertice> conexos){
        boolean tieneCaminoConTodos = true;
        if (!actual.isVisitado()) // Tiene que estar activo
            actual.setVisitado(true);
        if (vertices.size() == conexos.size())
            return true;
        if (actual.aristas.size() == 0) // Si no tiene aristas automaticamente es un grafo no conexo
            return false;
        for (Arista arista : actual.aristas){
            if (arista.destino.isActivo()){ // Tiene que estar activo
                if (buscarVertice(arista.destino,conexos) == null) {
                    conexos.add(arista.destino);
                    tieneCaminoConTodos = tieneCaminoConTodos && tieneCaminosConTodos(arista.destino, conexos);
                }
            }
        }
        //System.out.println("Estan todos visitados? "+estanTodosVisitados()+" vertices.size() != conexos.size()? "+(vertices.size() != conexos.size()));
        if (estanTodosVisitados() && (vertices.size() != conexos.size()))
            return false;
        return tieneCaminoConTodos;
    }

    private void reestablecerVisitados(){
        for (Vertice vertice : vertices)
            vertice.setVisitado(false);
    }

    public Vertice getMinimoNoVisitado(HashMap<Vertice,CaminoAristas> minimos){
        double menor = Double.MAX_VALUE;
        Vertice verticeConCaminoMinimo = null;
        for (Vertice vertice : minimos.keySet()) {
            /*  Se valida que:
                - El vertice no este visitado
                - Que el minimo sea menor al minimo actual */
            if (!vertice.isVisitadoActivo() && (menor > minimos.get(vertice).getDistanciaTotal())) {
                menor = minimos.get(vertice).getDistanciaTotal();
                verticeConCaminoMinimo = vertice;
            }
        }
        return verticeConCaminoMinimo;
    }

    public boolean estanTodosVisitados(){
        int numVisitados = 0;
        for (Vertice vertice : vertices) {
            if (vertice.isVisitado())
                numVisitados++;
        }
        return numVisitados == vertices.size();
    }

    public HashMap<Vertice,CaminoAristas> caminosMinimos(Vertice origen, int tipoPonderacion){
            // Valida que el grafo sea conexo
            if (!origen.isActivo()){
                Out.msg("Algo anda mal ...","Debe proporcionar un vertice activo");
                return null;
            }
            else if (esConexo()) {
                HashMap<Vertice,CaminoAristas> minimos = new HashMap<>();
                HashMap<Vertice,Arista> previos = new HashMap<>();

                // Se prepara la lista de adyacencia de distancias minimas
                for (Vertice vertice : vertices)
                    minimos.put(vertice, new CaminoAristas(Double.MAX_VALUE));
                minimos.put(origen,new CaminoAristas(0.0));
                // Algoritmo de Dijkstra
                Vertice actual = origen;    // Se pone el actual como el origen seleccionado para hacer el algoritmo
                double ponderacionAcumulada = 0.0;
                double nuevaPonderacion = 0.0;
                while (!estanTodosVisitados()) {
                    actual.setVisitado(true);
                    for (Arista arista : actual.aristas){
                        if (!arista.destino.isVisitadoActivo()){
                            ponderacionAcumulada = minimos.get(arista.destino).getDistanciaTotal();
                            nuevaPonderacion = minimos.get(actual).getDistanciaTotal() + arista.getPonderacion(tipoPonderacion);
                            if (ponderacionAcumulada > nuevaPonderacion) {
                                previos.put(arista.destino,arista);  // Agrega la arista del anterior al vertice actual
                                minimos.get(arista.destino).setDistanciaTotal(nuevaPonderacion);  // Se actualiza la distancia desde el origen al vertice
                            }
                        }
                    }
                    actual = getMinimoNoVisitado(minimos);
                    if (actual == null) // Ya todos estan visitados
                        break;
                }
                // Se recontruyen los caminos para llegar a cada vertice desde el origen especificado
                // Se recontruyen los caminos para llegar a cada vertice desde el origen especificado
                for (Vertice vertice : previos.keySet()){
                    Vertice posiblePrevio = vertice;
                    Arista aristaConPrevio;
                    Stack<Arista> pilaAristas = new Stack<Arista>();
                    while ((aristaConPrevio = previos.get(posiblePrevio)) != null){
                        pilaAristas.push(aristaConPrevio);
                        posiblePrevio = aristaConPrevio.getOrigen();
                    }
                    while (!pilaAristas.isEmpty()){
                        minimos.get(vertice).addCamino(pilaAristas.pop());
                    }
                }
                //TODO: Impresion para desarrollo, borrar luego
                System.out.println("RESULTADO FINAL DE LA TABLA - ALGORITMO DE DIJKSTRA");
                for (Vertice vertice : minimos.keySet()) {
                    System.out.println(vertice.nombre + " " + minimos.get(vertice).getDistanciaTotal());
                }
                reestablecerVisitados();    // Para futuras corridas
                return minimos;
            }
            else{
                Out.msg("Algo anda mal ...","El grafo debe ser conexo para poder encontrar los caminos mínimos desde cualquier vértice");
                return null;
            }
    }

    public void prim(){
        ArrayList<Vertice> nuevosVertices = new ArrayList<>();
        ArrayList<Arista> nuevosAristas = new ArrayList<>();
        for (int i = 0;i<vertices.size();i++){
            nuevosVertices.add(new Vertice(vertices.get(i)));
        }
        if(esConexo()){
            System.out.println("Es conexo");
            desactivarAristas();
            reestablecerVisitados();//TODO:SE ESTAN RETORNANDO TODAS LAS ARISTAS DESACTIVADAS
            for (int i = 0;i<vertices.size()-1;i++){
                Arista menor = menorAristaPrim();
                menor.destino.setVisitado(true);
                menor.setActivo(true);
                //Codigo feo
                Vertice clonOrigen = buscarVertice(menor.origen,nuevosVertices);
                Vertice clonDestino = buscarVertice(menor.destino,nuevosVertices);
                Arista copiaArista = new Arista(clonOrigen,clonDestino,menor);
                Arista copiaAristaInverso = new Arista(clonDestino,clonOrigen,menor);
                clonOrigen.addArista(copiaArista);
                clonDestino.addArista(copiaAristaInverso);
                nuevosAristas.add(copiaArista);
                nuevosAristas.add(copiaAristaInverso);
                ///
                menor.origen.setVisitado(true);
            }
            resetGrafo(nuevosVertices,nuevosAristas);
        }
        else {
            System.out.println("No es conexo");
        }
    }

    private void desactivarAristas(){
        for (Arista arista:aristas){
            arista.setActivo(false);
        }
    }
    //profundidad busca un nodo no marcado
    //


    private Arista menorAristaPrim() {//No deberia dar problemas con posibles vacios por ser conexo
        ArrayList<Arista> posibles = new ArrayList<>();
        if(ningunoSinVisitar()){
            return Collections.min(vertices.get(0).aristas);
        }
        else {
            for (Arista arista : aristas) {
                if (!arista.isActivo() && arista.origen.visitado && !arista.destino.visitado) {
                    posibles.add(arista);
                }
            }
        }
        return Collections.min(posibles);
    }

    public boolean ningunoSinVisitar(){
        for (Vertice vertice:vertices){
            if(vertice.visitado)
                return false;
        }
        return true;
    }

    public CaminoAristas recorridoEnProfundidad(Vertice origen){
        System.out.println("Entro a hacer el recorrido en profundidad");
        reestablecerVisitados();
        CaminoAristas camino = new CaminoAristas();
        recorridoEnProfundidad(origen,camino);
        if (camino == null)
            System.out.println("El camino resultante de el algoritmo es nulo");
        else
            System.out.println("El camino no esta nulo");
        return camino;
    }

    public void recorridoEnProfundidad(Vertice vertice,CaminoAristas camino){
        System.out.println(vertice);
        if(vertice.aristas.isEmpty())
            return;
        for (Arista arista: vertice.aristas){
            if (!arista.destino.visitado && arista.isActivo()){
                arista.origen.setVisitado(true);
                arista.destino.setVisitado(true);
                camino.addCamino(arista);
                recorridoEnProfundidad(arista.destino,camino);
                if(todosVisitados())
                    return;
            }
        }
    }

    private boolean todosVisitados() {
        for (Vertice vertice:vertices){
            if (!vertice.visitado)
                return false;
        }
        return true;
    }

    public CaminoAristas recorridoEnAnchura(Vertice origen){
        reestablecerVisitados();
        CaminoAristas camino = new CaminoAristas();
        recorridoEnAnchura(origen,camino);
        return camino;
    }

    public void recorridoEnAnchura(Vertice vertice,CaminoAristas camino){
        System.out.println(vertice);
        if(vertice.aristas.isEmpty())
            return;
        for (Arista arista: vertice.aristas){
            if(!arista.destino.visitado && arista.isActivo()) {
                System.out.println(arista.toStringToolTip());
                arista.origen.setVisitado(true);;
                camino.addCamino(arista);
            }
        }
        CaminoAristas caminoTemp = new CaminoAristas(camino);
        for (Arista arista: caminoTemp.camino){
            if(!arista.destino.visitado && arista.isActivo()){
                arista.destino.setVisitado(true);
                recorridoEnProfundidad(arista.destino,camino);
            }
        }
    }

}
