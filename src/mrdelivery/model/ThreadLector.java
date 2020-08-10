package mrdelivery.model;

import java.io.IOException;
import java.nio.file.*;

public class ThreadLector extends Thread {
    LectorJSON lectorJSON;

    public ThreadLector(LectorJSON _lectorJSON){
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
