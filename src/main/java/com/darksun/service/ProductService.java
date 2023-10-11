package com.darksun.service;

import com.darksun.model.Product;
import com.darksun.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;

	public Product create( Product product ) {
		if ( product.getId( ) == null ) {
			throw new IllegalArgumentException( "Product has no Id. Must be zero." );
		}
		if ( !product.getId( ).equals( 0L ) ) {
			throw new IllegalArgumentException( "This product is already registered" );
		}
		if ( product.getName( ) == null || product.getName( ).trim( ).equals( "" ) ) {
			throw new IllegalArgumentException( "Product has no name" );
		}
		if ( product.getPrice( ) == null
				|| product.getPrice( ).compareTo( BigDecimal.ZERO ) <= 0 ) {
			throw new IllegalArgumentException( "Product with invalid price" );
		}

		return repository.save( product );
	}

	public List< Product > readAll( ) {
		return repository.findAll( );
	}

	public Product readById( Long id ) {
		return repository.findById( id )
						 .orElseThrow( ( ) -> new EntityNotFoundException(
								 "Product not found with ID: " + id ) );
	}

	public List< Product > readAllByNameContaining( String name ) {
		return repository.findByNameContainingIgnoreCase( name );
	}

	public Product update( Product product ) {
		if ( product.getId( ) == null || product.getId( ) == 0L ) {
			throw new IllegalArgumentException( "This product has no ID" );
		}
		if ( product.getName( ) == null || product.getName( ).trim( ).equals( "" ) ) {
			throw new IllegalArgumentException( "Product has no name" );
		}
		if ( product.getPrice( ) == null
				|| product.getPrice( ).compareTo( BigDecimal.ZERO ) <= 0 ) {
			throw new IllegalArgumentException( "Product with invalid price" );
		}
		return repository.save( product );
	}

	public void delete( Long id ) {
		try {
			repository.deleteById( id );
		} catch ( EmptyResultDataAccessException ex ) {
			ex.printStackTrace( );
			throw new EntityNotFoundException( "Product not found with ID: " + id );
		}
	}
}
