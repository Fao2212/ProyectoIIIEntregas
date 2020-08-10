package mrdelivery.model;

import java.io.IOException;
import java.nio.file.*;

public class MonitorDatos extends Thread {
    LectorJSON lectorJSON;

    public MonitorDatos(LectorJSON _lectorJSON){
        lectorJSON = _lectorJSON;
    }

    public void detener() {
        lectorJSON.detenerLecturaContinua();
    }

    @Override
    public void run(){
        lectorJSON.leerContinuamente();
    }
}
