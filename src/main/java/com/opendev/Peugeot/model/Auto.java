package com.opendev.Peugeot.model;

import jakarta.persistence.*;



import java.util.*;

@Entity
public class Auto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String marca;
    private String modelo;
    private String tipo;
    private String color;
    private int puertas;
    private int maxVel;
    private String motor;
    private int torque;
    private int hp;
    private Long precio;
    private int unidadesEnStock;


    public Auto() {}
    public Auto(String marca, String modelo, String tipo, String color, int puertas, int maxVel, String motor, int torque, int hp, Long precio, int unidadesEnStock) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.color = color;
        this.puertas = puertas;
        this.maxVel = maxVel;
        this.motor = motor;
        this.torque = torque;
        this.hp = hp;
        this.precio = precio;
        this.unidadesEnStock = unidadesEnStock;
    }


    public Long getId() { return id; }

    public String getMarca() {
        return marca;
    }

    public String getModelo(){return modelo; }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public int getPuertas() {
        return puertas;
    }

    public int getMaxVel() {
        return maxVel;
    }

    public String getMotor() {
        return motor;
    }

    public int getTorque() {
        return torque;
    }

    public int getHp() {
        return hp;
    }

    public Long getPrecio() {
        return precio;
    }

    public int getUnidadesEnStock() {
        return unidadesEnStock;
    }

    public void setUnidadesEnStock(int cantidad){
        if(cantidad > 0) this.unidadesEnStock = cantidad;
    }

    public void setPrecio(Long precio){
        if(precio > 0){
            this.precio = precio;
        }
    }

    public void hacerVenta(int unidades){
        if(this.unidadesEnStock >= unidades){
            this.unidadesEnStock -= unidades;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return puertas == auto.puertas && maxVel == auto.maxVel && torque == auto.torque && hp == auto.hp && unidadesEnStock == auto.unidadesEnStock && Objects.equals(marca, auto.marca) && Objects.equals(modelo, auto.modelo) && Objects.equals(tipo, auto.tipo) && Objects.equals(color, auto.color) && Objects.equals(motor, auto.motor) && Objects.equals(precio, auto.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash( marca, modelo, tipo, color, puertas, maxVel, motor, torque, hp, precio, unidadesEnStock);
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", color='" + color + '\'' +
                ", puertas=" + puertas +
                ", maxVel=" + maxVel +
                ", motor='" + motor + '\'' +
                ", torque=" + torque +
                ", hp=" + hp +
                ", precio=" + precio +
                ", unidadesEnStock=" + unidadesEnStock +
                '}';
    }
}