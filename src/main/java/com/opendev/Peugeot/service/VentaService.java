package com.opendev.Peugeot.service;


import com.opendev.Peugeot.model.Auto;
import com.opendev.Peugeot.model.Venta;
import com.opendev.Peugeot.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private final IVentaRepository repositorio;

    @Autowired
    public VentaService(IVentaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public String hacerVenta(Auto auto, int unidades) {
        String mensaje = "Venta realizada con exito.";
        ;
        LocalDateTime fecha = LocalDateTime.now();
        if (auto.getUnidadesEnStock() >= unidades) {
            auto.hacerVenta(unidades);
            Venta venta = Venta
                    .VentaBuilder.nuevaInstancia()
                    .setAuto(auto)
                    .setCantidadVendida(unidades)
                    .setFechaVenta(fecha)
                    .build();
            repositorio.save(venta);
        } else {
            mensaje = "No hay Stock suficiente.";
        }
        return mensaje;
    }

    public String traerVentas() {
        List<Venta> listaVentas = repositorio.findAll();
        String ventas = listaVentas.stream().map(x -> x.toString() + "\n").reduce("", String::concat);
        return ventas;
    }

    public String traerVentasAnuales() {
        LocalDateTime fecha = LocalDateTime.now().minusYears(1);
        List<Venta> listaVentas = this.traerListaVentasAnuales();
        String ventasAnuales = listaVentas.stream()
                .map(e -> e.toString() + "\n")
                .reduce("", String::concat);
        return ventasAnuales;
    }

    public List<Venta> traerListaVentasAnuales() {
        LocalDateTime fecha = LocalDateTime.now().minusMonths(12);
        List<Venta> listaVentas = repositorio.findAll();
        List<Venta> ventasAnuales = listaVentas.stream()
                .filter(x -> x.getFechaVenta().isAfter(fecha))
                .sorted(Comparator.comparing(x -> x.getFechaVenta().toString()))
                .collect(Collectors.toList());
        return ventasAnuales;
    }
    public String reporteDeVentasAnual() {
        String reporte = "Reporte Anual de Ventas \n";

        List<Venta> ventaAnual = this.traerListaVentasAnuales();

        TreeMap<YearMonth, List<Venta>> ventasPorMesYAnio = ventaAnual.stream().collect(Collectors.groupingBy(x -> YearMonth.from(x.getFechaVenta()),
                                                                                                                TreeMap::new,
                                                                                                                Collectors.toList()));
        TreeMap<YearMonth, List<Venta>> ventasOrdenadas = new TreeMap<>((x,y)-> x.compareTo(y));
        ventasOrdenadas.putAll(ventasPorMesYAnio);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        double ventasTotales = 0;
        int unidadesVendidas = 0;
        for (Map.Entry<YearMonth, List<Venta>> entry : ventasOrdenadas.entrySet()) {
            YearMonth mes = entry.getKey();
            List<Venta> ventasDelMes = entry.getValue();

            double ventasPorMes = ventasDelMes.stream().mapToDouble(Venta::getMonto).sum();
            int unidadesPorMes = ventasDelMes.stream().mapToInt(Venta::getCantidadVendida).sum();

            ventasTotales += ventasPorMes;
            unidadesVendidas += unidadesPorMes;

            String mesFormateado = mes.format(formatter);
            reporte += mesFormateado + "\n Total Unidades: " + unidadesPorMes + "\n Total de ventas $" + ventasPorMes +  "\n";
        }
        reporte += "Total Unidades Vendidas: " + unidadesVendidas + " Ventas Totales: $" + ventasTotales + ".";
        return reporte;
   }

}