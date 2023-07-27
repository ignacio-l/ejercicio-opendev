package com.opendev.Peugeot.controller;

import com.opendev.Peugeot.excepciones.AutoExistenteException;
import com.opendev.Peugeot.excepciones.AutoInexistenteException;
import com.opendev.Peugeot.excepciones.CantidadInvalidaException;
import com.opendev.Peugeot.excepciones.MontoInvalidoException;
import com.opendev.Peugeot.model.Auto;
import com.opendev.Peugeot.model.Venta;
import com.opendev.Peugeot.service.AutoService;
import com.opendev.Peugeot.service.VentaService;
import com.opendev.Peugeot.validation.AutoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

import com.opendev.Peugeot.utils.Utils;


@CrossOrigin
@RestController
public class PeugeotController {

    Logger logger = LoggerFactory.getLogger(PeugeotController.class);
    private final AutoService autoService;
    private final VentaService ventaService;
    private AutoValidator autoValidator;

    public PeugeotController(AutoService autoService, VentaService ventaService, AutoValidator autoValidator) {
        this.autoService = autoService;
        this.ventaService = ventaService;
        this.autoValidator = autoValidator;
    }

    @GetMapping("/autos/get/{orden}/{tipo}")
    public ResponseEntity<Map<String, Object>> traerTodosConStock(@PathVariable("orden") String orden, @PathVariable("tipo") String tipo) {
        List<Auto> autos = autoService.traerTodosConStock(orden, tipo);
        logger.info(" orden: " + orden + " y utilizando: " + tipo);
        return new ResponseEntity<>(Utils.ok(autos), HttpStatus.OK);
    }

    @GetMapping("/autos/get")
    public ResponseEntity<Map<String, Object>> traerTodos() {
        List<Auto> autos = autoService.traerTodos();
        logger.info(" Lista Desordenada");
        return new ResponseEntity<>(Utils.ok(autos), HttpStatus.OK);
    }
    @GetMapping("/autos/get/{id}")
    public ResponseEntity<Map<String, Object>> traerAutoPorId(@PathVariable Long id) {
        try {
            Auto auto = autoService.traerPorId(id);
            logger.info("trajo auto con id " + id);
            return new ResponseEntity<>(Utils.ok(auto), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/autos/add")
    public ResponseEntity<Map<String, Object>> agregarAuto(@RequestBody Auto auto, BindingResult resultado) {
        autoValidator.validate(auto, resultado);
        if (resultado.hasErrors()) {
            String mensaje = " Atributos incompletos o nulos. ";
            logger.error(mensaje);
            return new ResponseEntity<>(Utils.missing(resultado), HttpStatus.BAD_REQUEST);
        } else {
            try {
                String mensaje = autoService.agregarAuto(auto);
                logger.info(auto.toString());
                return new ResponseEntity<>(Utils.ok(mensaje), HttpStatus.OK);
            } catch (AutoExistenteException e) {
                logger.error(e.getMessage());
                return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/autos/listaNoRep")
    public ResponseEntity<Map<String, Object>> listaSinRepeticiones(@RequestBody List<Auto> listaRep) {
        logger.info("Lista con Objetos Repetidos");
        List<Auto> listaSinRep = autoService.listaNoRep(listaRep);
        return new ResponseEntity<>(Utils.ok(listaSinRep), HttpStatus.OK);
    }

    @PutMapping("/autos/modificar")
    public ResponseEntity<Map<String, Object>> modificarAuto(@RequestBody Auto auto) {
        try {
            String mensaje = autoService.modificarAuto(auto);
            logger.info(mensaje);
            return new ResponseEntity<>(Utils.ok(mensaje), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/autos/modificarStock/{id}/{cantidad}")
    public ResponseEntity<Map<String, Object>> modificarStock(@PathVariable("id") Long id, @PathVariable("cantidad") int cantidad) {
        try {
            String mensaje = autoService.modificarStock(id, cantidad);
            logger.info(mensaje);
            return new ResponseEntity<>(Utils.ok(mensaje), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        } catch (CantidadInvalidaException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(Utils.error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/autos/modificarPrecio/{id}/{monto}")
    public ResponseEntity<Map<String, Object>> modificarPrecio(@PathVariable("id") Long id, @PathVariable("monto") Long monto) {
        try {
            String mensaje = autoService.modificarPrecio(id, monto);
            logger.info(mensaje);
            return new ResponseEntity<>(Utils.ok(mensaje), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(e.getLocalizedMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        } catch (MontoInvalidoException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("autos/borrar/{id}")
    public ResponseEntity<Map<String, Object>> borrarAutoPorId(@PathVariable Long id) {
        try {
            String mensaje = autoService.borrarAuto(id);
            logger.info(mensaje);
            return new ResponseEntity<>(Utils.ok(mensaje), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/venta/{idAuto}/{unidades}")
    public ResponseEntity<Map<String, Object>> hacerVenta(@PathVariable("idAuto") Long id, @PathVariable("unidades") int unidades) {
        Auto autoBuscado = null;
        try {
            autoBuscado = autoService.traerPorId(id);
            String mensaje = ventaService.hacerVenta(autoBuscado, unidades);
            logger.info(" IdAuto: " + autoBuscado.getId() + " " + unidades);
            return new ResponseEntity<>(Utils.ok(mensaje), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(" " + e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("ventas/get")
    public ResponseEntity<Map<String, Object>> traerVentas() {
        List<Venta> ventas = ventaService.traerVentas();
        logger.info("");
        return new ResponseEntity<>(Utils.ok(ventas), HttpStatus.OK);
    }

    @GetMapping("ventas/{idAuto}")
    public ResponseEntity<Map<String, Object>> traerVentasDeAuto(@PathVariable("idAuto") Long id) {
        try {
            List<Venta> ventasPorId = ventaService.traerVentasDeAuto(id);
            logger.info("Impresion ventas");
            return new ResponseEntity<>(Utils.ok(ventasPorId), HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(Utils.error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("ventas/anuales")
    public ResponseEntity<Map<String, Object>> traerVentasAnuales() {
        List<Venta> ventasAnuales = ventaService.traerListaVentasAnuales();
        logger.info("");
        return new ResponseEntity<>(Utils.ok(ventasAnuales), HttpStatus.OK);
    }

    @GetMapping("ventas/reporte")
    public ResponseEntity<String> imprimirReporteVentasAnuales() {
        String reporte = ventaService.reporteDeVentasAnual();
        logger.info("");
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }
}