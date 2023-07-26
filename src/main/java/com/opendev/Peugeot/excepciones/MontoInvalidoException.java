package com.opendev.Peugeot.excepciones;

public class MontoInvalidoException extends Exception {
    private String mensaje;
    public MontoInvalidoException(){
        this.mensaje = "El monto ingresado no es valido.";
    }

    public String mensaje(){
        return mensaje;
    }
}
