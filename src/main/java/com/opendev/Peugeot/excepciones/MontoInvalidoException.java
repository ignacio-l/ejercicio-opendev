package com.opendev.Peugeot.excepciones;

public class MontoInvalidoException extends Exception {
    private String mensaje;
    public MontoInvalidoException(){
        this.mensaje = "El monto ingresado no es valido.";
    }

    @Override
    public String getMessage(){
        return mensaje;
    }
}
