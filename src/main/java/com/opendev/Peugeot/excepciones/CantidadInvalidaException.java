package com.opendev.Peugeot.excepciones;

public class CantidadInvalidaException extends Exception {
    private String mensaje;
    public CantidadInvalidaException(){
        this.mensaje = "La cantidad ingresada no es valida.";
    }

    public String mensaje(){
        return mensaje;
    }
}
