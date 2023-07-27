package com.opendev.Peugeot.excepciones;

public class CantidadInvalidaException extends Exception {
    private String message;
    public CantidadInvalidaException(){
        this.message = "La cantidad ingresada no es valida.";
    }

    @Override
    public String getMessage(){
        return message;
    }
}
