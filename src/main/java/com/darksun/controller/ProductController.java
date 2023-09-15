package com.darksun.controller;

import com.darksun.model.Product;
import com.darksun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/products" )
public class ProductController {
	@Autowired
	ProductService service;

	@PostMapping
	public ResponseEntity< Product > create( @RequestBody Product product ) {
		return new ResponseEntity<>( service.create( product ), HttpStatus.CREATED );
	}

	@GetMapping
	public ResponseEntity< List< Product > > readAll( ) {
		return new ResponseEntity<>( service.readAll( ), HttpStatus.OK );
	}

	@GetMapping( "/{id}" )
	public ResponseEntity< Product > readById( @PathVariable Long id ) {
		return new ResponseEntity<>( service.readById( id ), HttpStatus.OK );
	}

	@GetMapping( "/name/{name}" )
	public ResponseEntity< List< Product > > readByNameContaining( @PathVariable String name ) {
		return new ResponseEntity<>( service.readAllByNameContaining( name ), HttpStatus.OK );
	}

	@PutMapping
	public ResponseEntity< Product > update( @RequestBody Product product ) {
		return new ResponseEntity<>( service.update( product ), HttpStatus.OK );
	}

	@DeleteMapping( "/{id}" )
	public ResponseEntity delete( @PathVariable Long id ) {
		service.delete( id );
		return new ResponseEntity( HttpStatus.NO_CONTENT );
	}
}
