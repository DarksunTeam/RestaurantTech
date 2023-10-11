package com.darksun.service;

import com.darksun.model.OrderCard;
import com.darksun.model.OrderItem;
import com.darksun.model.OrderItemId;
import com.darksun.model.Product;
import com.darksun.repository.OrderItemRepository;
import com.darksun.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderItemServiceTest {

	@InjectMocks
	OrderItemService service;

	@Mock
	OrderItemRepository repository;

	@Mock
	OrderCardService orderCardService;

	@Mock
	ProductService productService;

	List< OrderItem > orderItemList;
	List< Product >   productList;
	List< OrderCard > orderCardList;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );
		orderItemList = TestUtils.generate( OrderItem.class.getSimpleName( ) );
	}

	@Test
	void readAll( ) {
		when( repository.findAll( ) ).thenReturn( orderItemList );
		List< OrderItem > response = service.readAll( );
		Assertions.assertEquals( orderItemList, response );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void readByOrderCardId_oneItem( ) {
		when( repository.findByOrderCardId( any( ) ) ).thenReturn(
				List.of( orderItemList.get( 0 ) ) );
		List< OrderItem > response = service.readByOrderCardId( 2L );
		Assertions.assertEquals( 2L, response.get( 0 ).getId( ).getOrderCardId( ) );
		Assertions.assertEquals( 1, response.size( ) );
		verify( repository, times( 1 ) ).findByOrderCardId( any( ) );
	}

	@Test
	void readByOrderCardId_twoItems( ) {
		when( repository.findByOrderCardId( any( ) ) ).thenReturn( orderItemList.subList( 1, 3 ) );
		List< OrderItem > response = service.readByOrderCardId( 3L );
		for ( OrderItem orderItem : response ) {
			Assertions.assertEquals( 3L, orderItem.getId( ).getOrderCardId( ) );
		}
		Assertions.assertEquals( 2, response.size( ) );
		verify( repository, times( 1 ) ).findByOrderCardId( any( ) );
	}

	@Test
	void addItem_existent( ) {
		when( repository.findByOrderCardId( any( ) ) ).thenReturn( orderItemList );
		when( repository.save( any( ) ) ).thenReturn( orderItemList.get( 0 ) );
		OrderItem response = service.addItem( 1L, 2L );
		Assertions.assertEquals( 3, response.getQuantity( ) );
		Assertions.assertEquals( 1L, response.getId( ).getProductId( ) );
		Assertions.assertEquals( 2L, response.getId( ).getOrderCardId( ) );
		verify( repository, times( 1 ) ).findByOrderCardId( any( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void addItem_notExistent( ) {
		productList   = TestUtils.generate( Product.class.getSimpleName( ) );
		orderCardList = TestUtils.generate( OrderCard.class.getSimpleName( ) );
		when( repository.findByOrderCardId( any( ) ) ).thenReturn( new ArrayList<>( ) );
		OrderItem item = new OrderItem( new OrderItemId( 1L, 1L ), 1, productList.get( 0 ),
										orderCardList.get( 0 ) );
		when( repository.save( any( ) ) ).thenReturn( item );
		when( productService.readById( any( ) ) ).thenReturn( productList.get( 0 ) );
		when( orderCardService.readById( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderItem response = service.addItem( 1L, 1L );
		Assertions.assertEquals( 1, response.getQuantity( ) );
		Assertions.assertEquals( 1L, response.getId( ).getProductId( ) );
		Assertions.assertEquals( 1L, response.getId( ).getOrderCardId( ) );
		verify( repository, times( 1 ) ).findByOrderCardId( any( ) );
		verify( repository, times( 1 ) ).save( any( ) );
		verify( productService, times( 1 ) ).readById( any( ) );
		verify( orderCardService, times( 1 ) ).readById( any( ) );
	}

	@Test
	void removeItem_notUnique( ) {
		when( repository.findByOrderCardId( any( ) ) ).thenReturn( orderItemList );
		when( repository.save( any( ) ) ).thenReturn( orderItemList.get( 0 ) );
		OrderItem response = service.removeItem( 1L, 2L );
		Assertions.assertEquals( 1, response.getQuantity( ) );
		Assertions.assertEquals( 1L, response.getId( ).getProductId( ) );
		Assertions.assertEquals( 2L, response.getId( ).getOrderCardId( ) );
		verify( repository, times( 1 ) ).findByOrderCardId( any( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void removeItem_unique( ) {
		when( repository.findByOrderCardId( any( ) ) ).thenReturn(
				List.of( orderItemList.get( 1 ) ) );
		doNothing( ).when( repository ).delete( any( ) );
		OrderItem response = service.removeItem( 1L, 3L );
		Assertions.assertEquals( 0, response.getQuantity( ) );
		Assertions.assertEquals( 1L, response.getId( ).getProductId( ) );
		Assertions.assertEquals( 3L, response.getId( ).getOrderCardId( ) );
		verify( repository, times( 1 ) ).findByOrderCardId( any( ) );
		verify( repository, times( 1 ) ).delete( any( ) );
	}
}
