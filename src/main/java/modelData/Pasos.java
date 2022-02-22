/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelData;

import org.json.simple.JSONObject;

/**
 *
 * @author rigcampos
 */
public class Pasos {
    
    private String detalle;
    private String llave;
    private String valor;
    private String accion;
    private String input;

    @Override
    public String toString() {
        return "Pasos{" + "detalle=" + detalle + ", llave=" + llave + ", valor=" + valor + ", accion=" + accion + ", input=" + input + '}';
    }
    
    public Pasos(JSONObject jo){
        detalle = (String) jo.get("detalle");
        llave = (String) jo.get("llave");
        valor = (String) jo.get("valor");
        accion = (String) jo.get("accion");
        input = (String) jo.get("input");
    }
    
    public Pasos(String detalle, String llave, String valor, String accion, String input) {
        this.detalle = detalle;
        this.llave = llave;
        this.valor = valor;
        this.accion = accion;
        this.input = input;
    }
    
    public Pasos(String accion) {
        this.detalle = "";
        this.llave = "";
        this.valor = "";
        this.accion = accion;
        this.input = "";
    }
    
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
    
}
