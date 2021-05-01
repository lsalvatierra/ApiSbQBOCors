package com.qbo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qbo.exception.ResourceNotFoundException;
import com.qbo.model.Estado;
import com.qbo.service.*;

@RestController
@RequestMapping(path = "api/v1/estado")
//@CrossOrigin(origins = {"http://ricardogeek.com:8383", "http://rgeek.pro:4200"}) 
//Se puede especificar los orígenes quienes quieren hacer la petición.
//Se puede especificar que métodos HTTP queremos que accedan
//@CrossOrigin(origins = "*", methods= {RequestMethod.POST, RequestMethod.GET})
public class EstadoController {

	@Autowired
	protected EstadoService servicio;

	//@CrossOrigin(origins = "*") //CrossOrigin a nivel de API
	@GetMapping("")
	public ResponseEntity<List<Estado>> obtenerTodo() {
		List<Estado> estados = new ArrayList<Estado>();
		servicio.obtenerTodo().forEach(estados::add);
		if (estados.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(estados, HttpStatus.OK);
	}

	  @GetMapping("/{id}")
	  public ResponseEntity<Estado> obtenerEstadoPorId(@PathVariable("id") long id) {
		  Estado tutorial = servicio.obtenerPorId(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
	    return new ResponseEntity<>(tutorial, HttpStatus.OK);
	  }

	  @PostMapping("")
	  public ResponseEntity<Estado> crearEstado(@RequestBody Estado estado) {
		Estado _tutorial = servicio.guardar(new Estado(estado.getDescestado()));
	    return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	  }

	  @PutMapping("/{id}")
	  public ResponseEntity<Estado> actualizarEstado(@PathVariable("id") long id, @RequestBody Estado estado) {
		  Estado _estado = servicio.obtenerPorId(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
		  _estado.setDescestado(estado.getDescestado());
	    return new ResponseEntity<>(servicio.guardar(_estado), HttpStatus.OK);
	  }

	  @DeleteMapping("/{id}")
	  public ResponseEntity<?> eliminarEstado(@PathVariable("id") long id) {
		servicio.obtenerPorId(id)
			        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));		  
	    return ResponseEntity.status(HttpStatus.OK).body(servicio.eliminarPorId(id));
	  }


	
	
}
