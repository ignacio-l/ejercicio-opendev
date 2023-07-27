package com.opendev.Peugeot.excepciones;

public class AutoInexistenteException extends Exception{
    private String mensaje;
    public AutoInexistenteException(Long id){
        this.mensaje = "No existe el auto con id " + id + ".";
    }

    @Override
    public String getMessage(){
        return mensaje;
    }
}
