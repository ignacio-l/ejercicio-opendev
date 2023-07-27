package com.opendev.Peugeot.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Auto {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String marca;

    private String modelo;

    private String tipo;

    private String color;

    private Integer puertas;

    private Integer maxVel;

    private String motor;

    private Integer torque;

    private Integer hp;

    private Double precio;

    private Integer unidadesEnStock;


    public Auto() {}

    public Auto(String marca, String modelo, String tipo, String color, Integer puertas, Integer maxVel, String motor, Integer torque, Integer hp, Double precio, Integer unidadesEnStock) {
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

    public Long getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public Integer getPuertas() {
        return puertas;
    }

    public Integer getMaxVel() {
        return maxVel;
    }

    public String getMotor() {
        return motor;
    }

    public Integer getTorque() {
        return torque;
    }

    public Integer getHp() {
        return hp;
    }

    public double getPrecio() {
        return precio;
    }

    public Integer getUnidadesEnStock() {
        return unidadesEnStock;
    }

    public void setUnidadesEnStock(int cantidad){
        if(cantidad > 0) this.unidadesEnStock = cantidad;
    }

    public void setPrecio(double precio){
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
        return  Objects.equals(marca, auto.marca) && Objects.equals(modelo, auto.modelo) && Objects.equals(tipo, auto.tipo) && Objects.equals(color, auto.color) && Objects.equals(puertas, auto.puertas) && Objects.equals(maxVel, auto.maxVel) && Objects.equals(motor, auto.motor) && Objects.equals(torque, auto.torque) && Objects.equals(hp, auto.hp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modelo, tipo, color, puertas, maxVel, motor, torque, hp);
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