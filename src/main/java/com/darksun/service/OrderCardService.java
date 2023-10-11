package com.darksun.service;

import com.darksun.model.OrderCard;
import com.darksun.model.OrderItem;
import com.darksun.repository.OrderCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderCardService {
	@Autowired
	OrderCardRepository repository;

	public OrderCard create( Integer table ) {
		OrderCard orderCard = new OrderCard( 0L, table, false, true, new ArrayList<>( ) );
		return repository.save( orderCard );
	}

	public List< OrderCard > readAll( ) {
		return repository.findAll( );
	}

	public OrderCard readById( Long id ) {
		return repository.findById( id )
						 .orElseThrow( ( ) -> new EntityNotFoundException(
								 "Order card not found with ID: " + id ) );
	}

	public BigDecimal getFinalPrice( Long id ) {
		OrderCard  orderCard  = readById( id );
		BigDecimal finalPrice = BigDecimal.ZERO;

		for ( OrderItem item : orderCard.getItems( ) ) {
			finalPrice = finalPrice.add( item.getProduct( )
											 .getPrice( )
											 .multiply(
													 BigDecimal.valueOf( item.getQuantity( ) ) ) );
		}

		return finalPrice;
	}

	public OrderCard updateTable( Long id, Integer table ) {
		OrderCard orderCard = readById( id );
		orderCard.setTable( table );
		return repository.save( orderCard );
	}

	public OrderCard pay( Long id, Boolean wasCredit ) {
		OrderCard orderCard = readById( id );
		if ( Boolean.TRUE.equals( orderCard.getWasPaid( ) ) ) {
			throw new IllegalArgumentException( "Order card already paid" );
		}
		orderCard.setWasPaid( true );
		orderCard.setWasCredit( wasCredit );
		return repository.save( orderCard );
	}
}
