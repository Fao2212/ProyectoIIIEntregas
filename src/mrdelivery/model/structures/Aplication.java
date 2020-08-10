package mrdelivery.model.structures;

import org.omg.CORBA.ARG_IN;

import java.util.ArrayList;

public class Aplication {

    Grafo actualOriginal;
    Grafo actualModificado;

    public void crearGrafo(JSObject object){

        JSONArray vertices = object.getJSONArray("vertices");
        JSONArray aristas = object.getJSONArray("aristas");
        ArrayList<Vertice> listaVertices = new ArrayList<Vertice>();
        ArrayList<Arista> listaAristas = new ArrayList<>();
        for (int i = 0; i < vertices.length();i++){
            listaVertices.add(new Vertice(vertices.getString(i)));
        }
        for (int i = 0; i < aristas.length();i++){
            //Si encuentra un nulo puede enviarse a errores
            //Si encuentra un nulo puede considerarse como que no tiene camino
            JSObject arista = aristas.get(i);
            Vertice origen = buscarVertice(arista.getString("origen"));
            Vertice destino = buscarVertice(arista.getString("destino");
            Arista nuevaArista = new Arista(origen,destino,arista.getBoolean("activo"),
                    arista.getDouble("costo"),arista.getDouble("km"),
                    arista.getDouble("minutos");
            origen.addArista(nuevaArista);
            destino.addArista(nuevaArista);
            listaAristas.add(nuevaArista);
        }
        actualOriginal = new Grafo(listaVertices,listaAristas);
        actualModificado = actualOriginal.clonarGrafo();
    }

    public Vertice buscarVertice(String nombre,ArrayList<Vertice> vertices){
        for (Vertice vertice:vertices){
            if(vertice.nombre.equals(nombre))
                return vertice;
        }
        return null;
    }

}
