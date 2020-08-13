package mrdelivery.model;

import org.json.JSONObject;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LectorJSON implements Lector {
    // Atributos
    private final File archivo;
    private ArrayList<JSONObject> archivosJson;
    private final String ruta;
    private final String rutaLeidos;
    private boolean lecturaContinua;

    public LectorJSON(String rutaRelativa, String _rutaLeidos){
        archivo = new File(Paths.get(".",rutaRelativa).toString());
        archivosJson = new ArrayList<>();
        ruta = rutaRelativa;
        rutaLeidos = _rutaLeidos;
        lecturaContinua = true;
    }
    public LectorJSON(String rutaRelativa){
        archivo = new File(Paths.get(".",rutaRelativa).toString());
        archivosJson = new ArrayList<>();
        ruta = rutaRelativa;
        rutaLeidos = null;
        lecturaContinua = false;
    }

    @Override
    public void leer (){
        try {
            if (archivo.isDirectory())
                leerCarpeta(archivo);
            else if (archivo.isFile())
                leerArchivo(archivo);
        }
        catch (IOException ex) {
            System.out.println("Hubo un problema al leer archivos de la ruta " + archivo.getPath());
        }
    }

    public void leerContinuamente(){
        lecturaContinua = true;
        while (lecturaContinua){
            leer();
        }
    }

    public void detenerLecturaContinua(){
        lecturaContinua = false;
    }

    public void leerArchivo(File archivo) throws IOException {
        if (archivo.exists() && archivo.toString().endsWith(".json")){
            FileReader lector = new FileReader(archivo);
            StringBuilder datos = new StringBuilder();
            int caracterLeido = 0;
            while ((caracterLeido = lector.read()) != -1){
                datos.append((char)caracterLeido);
            }
            JSONObject json = new JSONObject(datos.toString());
            archivosJson.add(json);
            System.out.println("Leyo: " + archivo.getName());
            lector.close();
            // Despues de haber leido el archivo, se mueve a la carpeta de leidos si es un lector continuo
            if (lecturaContinua) {
                String nuevaUbicacion = String.valueOf(Paths.get("",rutaLeidos.substring(1,rutaLeidos.length())+"/"+archivo.getName()));
                try {
                    Files.copy(archivo.toPath(), Path.of(nuevaUbicacion),REPLACE_EXISTING);

                    Files.delete(archivo.toPath());

                }
                catch (Exception e){
                    System.out.println(e.toString());
                    System.out.println("No se pudo mandar el archivo a los leidos");
                }
            }
        }
        else
            System.out.println("No es un archivo o no es un JSON");
    }

    public void leerCarpeta(File fichero) throws IOException {
        if (fichero.exists()){
            for (final File archivo : fichero.listFiles()) {
                if (archivo.isFile() && archivo.getName().endsWith(".json"))
                    leerArchivo(archivo);
            }
        }
        else
            System.out.println("El fichero " + fichero.getAbsolutePath() + " no existe");
    }

    public ArrayList<JSONObject> getArchivosJson(){
        return archivosJson;
    }

    public String obtenerRutaJSON(){
        return ruta;
    }
}
