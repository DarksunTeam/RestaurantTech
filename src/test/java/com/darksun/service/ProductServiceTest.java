package com.darksun.service;

import com.darksun.model.Product;
import com.darksun.model.type.Category;
import com.darksun.repository.ProductRepository;
import com.darksun.util.TestUtils;
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
		productList = TestUtils.generate( Product.class.getSimpleName( ) );
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
	void findByNameContaining_Success( ) {
		List< Product > listPostFind = new ArrayList<>( );
		listPostFind.add( productList.get( 3 ) );
		when( repository.findByNameContainingIgnoreCase( any( ) ) ).thenReturn( listPostFind );
		List< Product > response = service.readAllByNameContaining( "stroganoff" );
		Assertions.assertEquals( response.get( 0 ), productList.get( 3 ) );
		Assertions.assertEquals( 1, response.size( ) );
		verify( repository, times( 1 ) ).findByNameContainingIgnoreCase( any( ) );
	}

	@Test
	void findByNameContaining_Fail( ) {
		List< Product > listPostFind = new ArrayList<>( );
		when( repository.findByNameContainingIgnoreCase( any( ) ) ).thenReturn( listPostFind );
		List< Product > products = service.readAllByNameContaining( "mozzarella" );
		Assertions.assertEquals( 0, products.size( ) );
		verify( repository, times( 1 ) ).findByNameContainingIgnoreCase( any( ) );
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
