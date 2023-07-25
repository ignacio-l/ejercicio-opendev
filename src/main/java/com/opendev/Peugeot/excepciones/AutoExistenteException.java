package com.opendev.Peugeot.excepciones;

public class AutoExistenteException extends Exception {
    private String mensaje;
    public AutoExistenteException(){
        this.mensaje = "El vehiculo ya existe en la base de datos.";
    }

    public String mensaje(){
        return mensaje;
    }
}
