package com.opendev.Peugeot.controller;

import com.opendev.Peugeot.excepciones.AutoExistenteException;
import com.opendev.Peugeot.excepciones.AutoInexistenteException;
import com.opendev.Peugeot.model.Auto;
import com.opendev.Peugeot.service.AutoService;
import com.opendev.Peugeot.service.VentaService;
import com.opendev.Peugeot.validation.AutoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;


@CrossOrigin
@RestController
public class PeugeotController {

    Logger logger = LoggerFactory.getLogger(PeugeotController.class);
    private final AutoService autoService;
    private final VentaService ventaService;
    private AutoValidator autoValidator;

    public PeugeotController(AutoService autoService, VentaService ventaService,AutoValidator autoValidator){
        this.autoService = autoService;
        this.ventaService = ventaService;
        this.autoValidator = autoValidator;
    }

    @GetMapping("/autos/get/{orden}/{tipo}")
    public ResponseEntity<String> traerTodosConStock(@PathVariable("orden") String orden, @PathVariable("tipo") String tipo){
        String autos = autoService.traerTodosConStock(orden,tipo);
        logger.info("orden: " + orden + " y utilizando: " + tipo);
        return new ResponseEntity<>(autos, HttpStatus.OK);
    }

    @GetMapping("/autos/get")
    public ResponseEntity<List<Auto>> traerTodos(){
        List<Auto> autos = autoService.traerTodos();
        logger.info("Lista Desordenada");
        return new ResponseEntity<>(autos,HttpStatus.OK);
    }

    @PostMapping("/autos/add")
    public ResponseEntity<String> agregarAuto(@RequestBody Auto auto, BindingResult resultado){
        autoValidator.validate(auto, resultado);
        if(resultado.hasErrors()) {
            logger.error("Error: el atributo " + resultado.getFieldError().getField() + " no puede estar vacio o ser nulo.");
            return new ResponseEntity<>("Error: el atributo " + resultado.getFieldError().getField() + " no puede estar vacio o ser nulo.",HttpStatus.BAD_REQUEST);
        } else {
            try {
                String mensaje = autoService.agregarAuto(auto);
                logger.info(auto.toString());
                return new ResponseEntity<>(mensaje, HttpStatus.OK);
            } catch (AutoExistenteException e) {
                logger.error(e.mensaje());
                return new ResponseEntity<>(e.mensaje(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/autos/listaNoRep")
    public ResponseEntity<List<Auto>> listaSinRepeticiones(@RequestBody List<Auto>listaRep){
        logger.info("Lista con Objetos Repetidos");
        List<Auto> listaSinRep = autoService.listaNoRep(listaRep);
        return new ResponseEntity<>(listaSinRep,HttpStatus.OK);
    }

    @PostMapping("/venta/{idAuto}/{unidades}")
    public ResponseEntity<String> hacerVenta(@PathVariable("idAuto") Long id, @PathVariable("unidades") int unidades){
        Auto autoBuscado = null;
        try {
            autoBuscado = autoService.traerPorId(id);
            String mensaje = ventaService.hacerVenta(autoBuscado,unidades);
            logger.info("hacerVenta IdAuto: " + autoBuscado.getId() + " " + unidades);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (AutoInexistenteException e) {
            logger.error("hacerVenta" + e.mensaje());
            return new ResponseEntity<>(e.mensaje(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("ventas/get")
    public ResponseEntity<String> traerVentas(){
        String ventas = ventaService.traerVentas();
        return new ResponseEntity<>(ventas,HttpStatus.OK);
    }

    @GetMapping("ventas/anuales")
    public ResponseEntity<String> traerVentasAnuales(){
        String ventasAnuales = ventaService.traerVentasAnuales();
        return new ResponseEntity<>(ventasAnuales,HttpStatus.OK);
    }

    @GetMapping("ventas/reporte")
    public ResponseEntity<String> imprimirReporteVentasAnuales(){
        String reporte = ventaService.reporteDeVentasAnual();
        return new ResponseEntity<>(reporte,HttpStatus.OK);
    }
}