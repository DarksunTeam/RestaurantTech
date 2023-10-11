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
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
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
	void create_Success( ) {
		Product product = new Product( 0L, "Soda", new BigDecimal( "2" ), "A good drink to refresh",
									   Category.DRINK );

		when( repository.save( product ) ).thenReturn( productList.get( 0 ) );
		Product response = service.create( product );
		Assertions.assertEquals( response, productList.get( 0 ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void create_Fail_Id( ) {
		Product product = new Product( 1L, "Soda", new BigDecimal( "2" ), "A good drink to refresh",
									   Category.DRINK );
		String message = "";

		try {
			service.create( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "This product is already registered", message );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void create_Fail_Name( ) {
		Product product = new Product( 0L, "   ", new BigDecimal( "2" ), "A good drink to refresh",
									   Category.DRINK );
		String message = "";

		try {
			service.create( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product has no name", message );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void create_Fail_Price( ) {
		Product product = new Product( 0L, "Soda", new BigDecimal( "-2" ),
									   "A good drink to refresh", Category.DRINK );
		String message = "";

		try {
			service.create( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product with invalid price", message );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void ReadAll( ) {
		when( repository.findAll( ) ).thenReturn( productList );
		List< Product > products = service.readAll( );
		Assertions.assertEquals( products, productList );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void findById_Success( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( productList.get( 0 ) ) );
		Product product = service.readById( 1L );
		Assertions.assertEquals( product, productList.get( 0 ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void findById_Fail( ) {
		when( repository.findById( any( ) ) ).thenReturn( Optional.empty( ) );
		boolean thrown = false;
		try {
			service.readById( 10L );
		} catch ( EntityNotFoundException ex ) {
			thrown = true;
		}
		Assertions.assertTrue( thrown );
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
	void update_Success( ) {
		Product product        = productList.get( 1 );
		String  newDescription = "For happy moments";
		product.setDescription( newDescription );
		when( repository.save( product ) ).thenReturn( product );
		Product response = service.update( product );
		Assertions.assertEquals( response.getDescription( ), newDescription );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void update_Fail_Id_null( ) {
		Product product = productList.get( 1 );
		product.setId( null );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "This product has no ID", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void update_Fail_Id_zero( ) {
		Product product = productList.get( 1 );
		product.setId( 0L );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "This product has no ID", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void update_Fail_Name_Null( ) {
		Product product = productList.get( 1 );
		product.setName( null );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product has no name", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void update_Fail_Name_Blank( ) {
		Product product = productList.get( 1 );
		product.setName( "" );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product has no name", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void update_Fail_Name_JustSpace( ) {
		Product product = productList.get( 1 );
		product.setName( "   " );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product has no name", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void update_Fail_Price_Null( ) {
		Product product = productList.get( 1 );
		product.setPrice( null );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product with invalid price", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void update_Fail_Price_NegativeValue( ) {
		Product product = productList.get( 1 );
		product.setPrice( BigDecimal.valueOf( -0.1 ) );
		String message = "";
		try {
			service.update( product );
		} catch ( IllegalArgumentException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product with invalid price", message );
		verify( repository, times( 0 ) );
	}

	@Test
	void delete_Success( ) {
		doNothing( ).when( repository ).deleteById( any( ) );
		service.delete( 1L );
		verify( repository, times( 1 ) ).deleteById( any( ) );
	}

	@Test
	void delete_Fail( ) {
		doThrow( EmptyResultDataAccessException.class ).when( repository ).deleteById( any( ) );
		String message = "";
		try {
			service.delete( 100L );
		} catch ( EntityNotFoundException ex ) {
			message = ex.getMessage( );
		}
		Assertions.assertEquals( "Product not found with ID: 100", message );
		verify( repository, times( 1 ) ).deleteById( any( ) );
	}
}
