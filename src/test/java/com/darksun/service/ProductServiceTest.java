package com.darksun.service;

import com.darksun.model.Product;
import com.darksun.model.type.Category;
import com.darksun.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	List< Product > productList;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );
		Product product1 = new Product( 1L, "Soda", new BigDecimal( "2" ),
										"A good drink to refresh", Category.DRINK );
		Product product2 = new Product( 2L, "Beer", new BigDecimal( "3.5" ),
										"Neither strong nor weak", Category.ALCOHOLIC_DRINK );
		Product product3 = new Product( 3L, "Shrimp stroganoff", new BigDecimal( "10" ),
										"The best meal of your life", Category.MEAL );

		productList = new ArrayList<>( );
		productList.add( product1 );
		productList.add( product2 );
		productList.add( product3 );
	}

	@Test
	void create( ) {
		Product product = new Product( 0L, "Soda", new BigDecimal( "2" ), "A good drink to refresh",
									   Category.DRINK );

		when( repository.save( product ) ).thenReturn( productList.get( 0 ) );
		Product response = service.create( product );
		Assertions.assertEquals( response, productList.get( 0 ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void ReadAll( ) {
		when( repository.findAll( ) ).thenReturn( productList );
		List< Product > products = service.readAll( );
		Assertions.assertEquals( products, productList );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void findById( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( productList.get( 0 ) ) );
		Product product = service.readById( 1L );
		Assertions.assertEquals( product, productList.get( 0 ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void update( ) {
		Product product        = productList.get( 1 );
		String  newDescription = "For happy moments";
		product.setDescription( newDescription );
		when( repository.save( product ) ).thenReturn( product );
		Product response = service.update( product );
		Assertions.assertEquals( response.getDescription( ), newDescription );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void delete( ) {
		doNothing( ).when( repository ).deleteById( any( ) );
		service.delete( 1L );
		verify( repository, times( 1 ) ).deleteById( any( ) );
	}
}
