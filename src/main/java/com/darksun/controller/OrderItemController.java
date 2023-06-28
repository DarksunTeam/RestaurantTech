package com.darksun.controller;

import com.darksun.model.OrderItem;
import com.darksun.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/order-items" )
public class OrderItemController {
	@Autowired
	OrderItemService service;

	@GetMapping
	public ResponseEntity< List< OrderItem > > readAll( ) {
		return new ResponseEntity<>( service.readAll( ), HttpStatus.OK );
	}

	@GetMapping( "/{id}" )
	public ResponseEntity< List< OrderItem > > getOrderCardItems( @PathVariable Long id ) {
		return new ResponseEntity<>( service.readByOrderCardId( id ), HttpStatus.OK );
	}

	@PutMapping( "/add" )
	public ResponseEntity< OrderItem > addItem( @RequestParam Long productId,
												@RequestParam Long orderCardId ) {
		return new ResponseEntity<>( service.addItem( productId, orderCardId ), HttpStatus.OK );
	}

	@PutMapping( "/remove" )
	public ResponseEntity< OrderItem > removeItem( @RequestParam Long productId,
												   @RequestParam Long orderCardId ) {
		return new ResponseEntity<>( service.removeItem( productId, orderCardId ), HttpStatus.OK );
	}
}
