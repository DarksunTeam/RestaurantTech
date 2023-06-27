package com.darksun.controller;

import com.darksun.model.OrderCard;
import com.darksun.service.OrderCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping( "/order-cards" )
public class OrderCardController {
	@Autowired
	OrderCardService service;

	@PostMapping
	public ResponseEntity< OrderCard > create( @RequestParam Integer tableNumber ) {
		return new ResponseEntity<>( service.create( tableNumber ), HttpStatus.CREATED );
	}

	@GetMapping
	public ResponseEntity< List< OrderCard > > readAll( ) {
		return new ResponseEntity<>( service.readAll( ), HttpStatus.OK );
	}

	@GetMapping( "/{id}" )
	public ResponseEntity< OrderCard > readById( @PathVariable Long id ) {
		return new ResponseEntity<>( service.readById( id ), HttpStatus.OK );
	}

	@GetMapping( "/{id}/price" )
	public ResponseEntity< BigDecimal > getFinalPrice( @PathVariable Long id ) {
		return new ResponseEntity<>( service.getFinalPrice( id ), HttpStatus.OK );
	}

	@PutMapping( "/{id}/update-table" )
	public ResponseEntity< OrderCard > updateTable( @PathVariable Long id,
													@RequestParam Integer tableNumber ) {
		return new ResponseEntity<>( service.updateTable( id, tableNumber ), HttpStatus.OK );
	}

	@PutMapping( "/{id}/pay" )
	public ResponseEntity< OrderCard > pay( @PathVariable Long id,
											@RequestParam Boolean wasCredit ) {
		return new ResponseEntity<>( service.pay( id, wasCredit ), HttpStatus.OK );
	}
}
