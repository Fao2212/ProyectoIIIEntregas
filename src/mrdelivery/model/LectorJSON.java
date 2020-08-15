package mrdelivery.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LectorJSON implements Lector {
    // Atributos
    private final File archivo;
    private ArrayList<JSONObject> archivosJson;
    private final String ruta;
    private final String rutaLeidos;
    private boolean lecturaContinua;
    private final String rutaBorrados;

    public LectorJSON(String rutaRelativa, String _rutaLeidos,String _rutaBorrados){
        archivo = new File(Paths.get(".",rutaRelativa).toString());
        archivosJson = new ArrayList<>();
        ruta = rutaRelativa;
        rutaLeidos = _rutaLeidos;
        rutaBorrados = _rutaBorrados;
        lecturaContinua = true;
    }

    public LectorJSON(String rutaRelativa, String _rutaLeidos){
        archivo = new File(Paths.get(".",rutaRelativa).toString());
        archivosJson = new ArrayList<>();
        ruta = rutaRelativa;
        rutaLeidos = _rutaLeidos;
        rutaBorrados = null;
        lecturaContinua = true;
    }
    public LectorJSON(String rutaRelativa){
        archivo = new File(Paths.get(".",rutaRelativa).toString());
        archivosJson = new ArrayList<>();
        ruta = rutaRelativa;
        rutaBorrados = null;
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
        if (archivo.exists() && archivo.toString().endsWith(".json")) {
            FileReader lector = new FileReader(archivo);
            StringBuilder datos = new StringBuilder();
            int caracterLeido = 0;
            while ((caracterLeido = lector.read()) != -1) {
                datos.append((char) caracterLeido);
            }
            JSONObject json = new JSONObject(datos.toString());
                archivosJson.add(json);
                System.out.println("Leyo: " + archivo.getName());
                lector.close();
                if (lecturaContinua) {
                    if (validarJSON(json)) {
                    String nuevaUbicacion = String.valueOf(Paths.get("", rutaLeidos.substring(1, rutaLeidos.length()) + "/" + archivo.getName()));
                    try {
                        Files.copy(archivo.toPath(), Path.of(nuevaUbicacion), REPLACE_EXISTING);

                        Files.delete(archivo.toPath());

                    } catch (Exception e) {
                        System.out.println(e.toString());
                        System.out.println("No se pudo mandar el archivo a los leidos");
                    }
                }
                    else {
                        String nuevaUbicacion = String.valueOf(Paths.get("", rutaBorrados.substring(1, rutaBorrados.length()) + "/" + archivo.getName()));
                        try {
                            Files.copy(archivo.toPath(), Path.of(nuevaUbicacion), REPLACE_EXISTING);
                            Files.delete(archivo.toPath());
                        }
                        catch (Exception e){
                            System.out.println(e.toString());
                            System.out.println("No se pudo mandar el archivo a los errores");
                        }
                        System.out.println("Archivo con errores");
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

    public boolean validarJSON(JSONObject json){
        System.out.println(json);
        JSONArray vertices = json.getJSONArray("vertices");
        JSONArray aristas = json.getJSONArray("aristas");
        HashSet<String > nombresVertices = new HashSet<>();
        try {
            for (int i = 0;i < vertices.length();i++){
                String nombre = vertices.getString(i);
                nombresVertices.add(nombre);
            }
        }
        catch (JSONException e){
            System.out.println("ERROR NOMBRE DE VERTICE NO ES UN STRING");
            return false;
        }
        if(nombresVertices.size() != vertices.length()) {
            System.out.println("ERROR NOMBRE DE VERTICE REPETIDO");
            return false;
        }
        else
            return validarTodasAristas(nombresVertices,aristas);
    }

    public boolean validarTodasAristas(HashSet<String> vertices,JSONArray aristas){//TODO:CONSIDERAR SI VIENE VACIO O UN ELEMENTO
        HashSet<String> origenDestino = new HashSet<>();
        for (int i = 0;i< aristas.length();i++){
            try {
                JSONObject arista = aristas.getJSONObject(i);
                String origen = arista.getString("origen");
                String destino = arista.getString("destino");
                arista.getBoolean("activo");
                arista.getDouble("costo");
                arista.getDouble("km");
                arista.getDouble("minutos");
                origenDestino.add(origen+destino);
                if(!(vertices.contains(origen) && vertices.contains(destino))) {
                    System.out.println("NO EXISTE EL VERTICE PARA ASIGNAR LA ARISTA");
                    return false;
                }
            }
            catch (JSONException e) {
                System.out.println("LOS VALORES DE LAS ARISTAS NO SON CORRECTOS");
                return false;
            }
        }
        if(origenDestino.size() != aristas.length()) {
            System.out.println("ERROR ARISTA REPETIDO");
            return false;
        }
        return true;
    }

}
