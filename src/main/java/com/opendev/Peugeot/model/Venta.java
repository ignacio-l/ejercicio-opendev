package com.opendev.Peugeot.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    private Auto auto;
    private int cantidadVendida;
    private LocalDateTime fechaVenta;
    private double monto;
    public Venta(VentaBuilder builder){
        this.cantidadVendida = builder.cantidadVendida;
        this.fechaVenta = builder.fechaVenta;
        this.auto = builder.auto;
        this.monto = this.cantidadVendida * this.auto.getPrecio();
    }

    public Venta() {}

    public Long getId(){return id; }
    public Auto getAuto() { return auto; }
    public int getCantidadVendida() {
        return cantidadVendida;
    }
    public LocalDateTime getFechaVenta() {return fechaVenta;}
    public double getMonto(){ return this.monto; }
    public String getStringFecha(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return this.fechaVenta.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return cantidadVendida == venta.cantidadVendida && Double.compare(venta.monto, monto) == 0 && Objects.equals(auto, venta.auto) && Objects.equals(fechaVenta, venta.fechaVenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auto, cantidadVendida, fechaVenta, monto);
    }

    @Override
    public String toString() {

        return "Se han vendido " + this.getCantidadVendida() + " unidades del vehiculo Marca: " + auto.getMarca() + " Modelo: " + auto.getModelo() + " Color: " + auto.getColor() + ", en la fecha: " + this.getStringFecha() + ".";
    }

    public static class VentaBuilder{

        private Auto auto;
        private int cantidadVendida;
        private LocalDateTime fechaVenta;

        private VentaBuilder(){}

        public static VentaBuilder nuevaInstancia() {
            return new VentaBuilder();
        }

        public VentaBuilder setAuto(Auto auto){
            this.auto = auto;
            return this;
        }

        public VentaBuilder setCantidadVendida(int cantidadVendida){
            this.cantidadVendida = cantidadVendida;
            return this;
        }

        public VentaBuilder setFechaVenta(LocalDateTime fechaVenta){
            this.fechaVenta = fechaVenta;
            return this;
        }

        public Venta build() {
            return new Venta(this);
        }
    }
}