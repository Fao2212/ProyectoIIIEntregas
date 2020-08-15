package mrdelivery.model.structures;

import mrdelivery.view.componentes.Boton;
import mrdelivery.view.componentes.BotonVertice;

import java.util.ArrayList;
import java.util.Collections;

public class Vertice {

    String nombre;
    ArrayList<Arista> aristas;
    boolean visitado;
    boolean activo;
    BotonVertice boton;

    public Vertice(String nombre){
        this.nombre = nombre;
        this.aristas = new ArrayList<>();
        this.visitado = false;
        this.activo = true;
    }

    public Vertice(Vertice vertice){//Paso el vertice completo por si luego se le agregan mas atributos que asignar
        this.nombre = vertice.nombre;
        this.aristas = new ArrayList<>();
        this.visitado = false;
        this.activo = true;
    }

    public BotonVertice getBoton() {
        return boton;
    }

    public void addArista(Arista arista){
        this.aristas.add(arista);
    }

    public boolean buscarCamino(Vertice vertice2) {
        return false;
    }

    public void resaltarEnPantalla() {
        boton.resaltar();
    }

    public boolean isVisitado() {
        return visitado;
    }

    public boolean isVisitadoActivo(){
        return visitado && activo;
    }

    public void setVisitado(boolean value){
        this.visitado = value;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setBoton(BotonVertice btnVertice) {
        this.boton = btnVertice;
    }

    public void quitarResaltado() {
        boton.quitarResaltado();
    }
}
