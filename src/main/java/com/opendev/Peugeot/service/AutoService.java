package com.opendev.Peugeot.service;

import com.opendev.Peugeot.excepciones.AutoExistenteException;
import com.opendev.Peugeot.excepciones.AutoInexistenteException;
import com.opendev.Peugeot.excepciones.CantidadInvalidaException;
import com.opendev.Peugeot.excepciones.MontoInvalidoException;
import com.opendev.Peugeot.model.Auto;
import com.opendev.Peugeot.repository.IAutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutoService {
    private final IAutoRepository repositorio;
    @Autowired
    public AutoService(IAutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public String agregarAuto(Auto auto) throws AutoExistenteException{
        String mensaje = "Se pudo agregar el vehiculo.";
        List<Auto> autosExistentes = this.traerTodos();
        if (autosExistentes.contains(auto)) {
            throw new AutoExistenteException();
        } else {
            repositorio.save(auto);
        }
        return mensaje;
    }

    public List<Auto> traerTodosConStock(String orden, String tipo) {
        List<Auto> listaAutos = null;
        if(tipo.equalsIgnoreCase("ORM")){
            if (orden.equalsIgnoreCase("ASC")) {
                listaAutos = repositorio.findAll(Sort.by(Sort.Direction.ASC, "id"));
            } else if (orden.equalsIgnoreCase("DSC")) {
                listaAutos = repositorio.findAll(Sort.by(Sort.Direction.DESC, "id"));
            }
        }else if (tipo.equalsIgnoreCase("stream")) {
            List<Auto> listaDesordenada = repositorio.findAll();
            if (orden.equalsIgnoreCase("ASC")) {
                listaAutos = listaDesordenada.stream().sorted(Comparator.comparing(Auto::getId)).collect(Collectors.toList());
            } else if (orden.equalsIgnoreCase("DSC")) {
                listaAutos = listaDesordenada.stream().sorted((x, y) -> (y.getId().compareTo(x.getId()))).collect(Collectors.toList());
            }
        }
        return filtrarConStock(listaAutos);
    }

    public List<Auto> traerTodos() {
        return repositorio.findAll();
    }

    public Auto traerPorId(Long id) throws AutoInexistenteException {
        Optional<Auto> lista = repositorio.findById(id);
        if(lista.isEmpty()){
            throw new AutoInexistenteException(id);
        }else {
            return lista.get();
        }
    }
    public List<Auto> listaNoRep(List<Auto> lista) {
        List<Auto> listaOrdenada = lista.stream()
                .distinct()
                .collect(Collectors.toList());
        return listaOrdenada;
    }

    public String borrarAuto(Long id) throws AutoInexistenteException {
        String mensaje = "El auto con el id " + id + " fue borrado.";
        Optional<Auto> autoBuscado = repositorio.findById(id);
        if (autoBuscado.isEmpty()){
            throw new AutoInexistenteException(id);
        } else {
            repositorio.deleteById(id);
        }
        return mensaje;
    }

    public String modificarAuto(Auto auto) throws AutoInexistenteException {
        String mensaje = "Auto modificado con exito";
        Optional<Auto> autoAModificar = repositorio.findById(auto.getId());
        if(autoAModificar.isEmpty()){
            throw new AutoInexistenteException(auto.getId());
        } else {
            repositorio.save(auto);
        }
        return mensaje;
    }

    public String modificarStock(Long id, int cantidad) throws AutoInexistenteException, CantidadInvalidaException {
        String mensaje = "Stock modificado con exito";
        Optional<Auto> autoAModificar = repositorio.findById(id);
        if(autoAModificar.isEmpty()){
            throw new AutoInexistenteException(id);
        } else if (cantidad < 0){
            throw new CantidadInvalidaException();
        }else{
            Auto autoModificado = autoAModificar.get();
            autoModificado.setUnidadesEnStock(cantidad);
            repositorio.save(autoModificado);
        }
        return mensaje;
    }

    public String modificarPrecio(Long id, double monto) throws AutoInexistenteException, MontoInvalidoException {
        String mensaje = "Precio modificado con exito";
        Optional<Auto> autoAModificar = repositorio.findById(id);
        if(autoAModificar.isEmpty()){
            throw new AutoInexistenteException(id);
        } else if (monto < 0){
            throw new MontoInvalidoException();
        }else{
            Auto autoModificado = autoAModificar.get();
            autoModificado.setPrecio(monto);
            repositorio.save(autoModificado);
        }
        return mensaje;
    }
    private List<Auto> filtrarConStock(List<Auto> listaAutos) {
        List<Auto> listaStock = listaAutos.stream()
                .filter(x -> x.getUnidadesEnStock() > 0)
                .collect(Collectors.toList());
        return listaStock;
    }
    private String stringFormatter(List<Auto> listaAutos){
        String autos = listaAutos.stream().map(e -> e.toString() + "\n").reduce("", String::concat);
        return autos;
    }


}