package com.darksun.service;

import com.darksun.model.OrderCard;
import com.darksun.model.OrderItem;
import com.darksun.model.OrderItemId;
import com.darksun.model.Product;
import com.darksun.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderItemService {
	@Autowired
	OrderItemRepository repository;

	@Autowired
	OrderCardService orderCardService;

	@Autowired
	ProductService productService;

	public List< OrderItem > readAll( ) {
		return repository.findAll( );
	}

	public List< OrderItem > readByOrderCardId( Long id ) {
		return repository.findByOrderCardId( id );
	}

	public OrderItem addItem( Long productId, Long orderCardId ) {
		OrderItem         orderItem  = null;
		List< OrderItem > orderItems = readByOrderCardId( orderCardId );
		for ( OrderItem item : orderItems ) {
			if ( item.getProduct( ).getId( ).equals( productId ) ) {
				orderItem = item;
				orderItem.setQuantity( orderItem.getQuantity( ) + 1 );
				break;
			}
		}
		if ( orderItem == null ) {
			OrderItemId id        = new OrderItemId( productId, orderCardId );
			Product     product   = productService.readById( productId );
			OrderCard   orderCard = orderCardService.readById( orderCardId );
			orderItem = new OrderItem( id, 1, product, orderCard );
		}
		return repository.save( orderItem );
	}

	public OrderItem removeItem( Long productId, Long orderCardId ) {
		OrderItem         orderItem  = null;
		List< OrderItem > orderItems = readByOrderCardId( orderCardId );
		for ( OrderItem item : orderItems ) {
			if ( item.getProduct( ).getId( ).equals( productId ) ) {
				orderItem = item;
				orderItem.setQuantity( orderItem.getQuantity( ) - 1 );
				if ( orderItem.getQuantity( ) < 1 ) {
					repository.delete( item );
				} else {
					repository.save( orderItem );
				}
				break;
			}
		}
		if ( orderItem == null ) {
			throw new EntityNotFoundException( "This item has not been sold" );
		}
		return orderItem;
	}
}
