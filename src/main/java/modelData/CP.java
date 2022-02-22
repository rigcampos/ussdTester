/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelData;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author rigcampos
 */
public class CP {
    
    private String titulo;
    private String link;
    private ArrayList<Pasos> pasos;
    
    public CP(String titulo, String link, ArrayList<Pasos> pasos) {
        this.titulo = titulo;
        this.link = link;
        this.pasos = pasos;
    }
    
    public CP(JSONObject cp) {
        this.titulo = (String) cp.get("titulo");
        this.link = (String) cp.get("link");
        JSONArray ps = (JSONArray) cp.get("pasos");
        this.pasos = new ArrayList<Pasos>();
        ps.forEach((js)->{
            this.pasos.add(new Pasos((JSONObject)js));
        });
        
    }
    
    @Override
    public String toString() {
        return "CP{" + "titulo=" + titulo + ", link=" + link + ", pasos=" + pasos.toString() + '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Pasos> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<Pasos> pasos) {
        this.pasos = pasos;
    }
    
}
