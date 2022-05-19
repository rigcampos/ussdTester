/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelData;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author rigcampos
 */
public class ModelCredencial {
    
    private String titulo;
    private Map<String, String> credenciales;

    public ModelCredencial(String titulo, Map<String, String> credenciales) {
        this.titulo = titulo;
        this.credenciales = credenciales;
    }
    
    public ModelCredencial(JSONObject c){
        this.titulo = (String)c.get("titulo");
        this.credenciales = new HashMap<>();
        JSONArray tempList = (JSONArray) c.get("credenciales");
        this.credenciales = ((JSONObject)tempList.get(0));
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Map<String, String> getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(Map<String, String> credenciales) {
        this.credenciales = credenciales;
    }
    
}
