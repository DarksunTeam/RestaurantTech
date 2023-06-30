package com.darksun.service;

import com.darksun.model.OrderCard;
import com.darksun.model.OrderItem;
import com.darksun.model.OrderItemId;
import com.darksun.model.Product;
import com.darksun.model.type.Category;
import com.darksun.repository.OrderCardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderCardServiceTest {

	@InjectMocks
	private OrderCardService service;

	@Mock
	private OrderCardRepository repository;

	List< OrderCard > orderCardList;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );

		OrderCard order1 = new OrderCard( 1L, 1, false, null, new ArrayList<>( ) );

		Product product1 = new Product( 1L, "Soda", new BigDecimal( "2" ),
										"A good drink to refresh", Category.DRINK );
		OrderItem item1  = new OrderItem( new OrderItemId( 1L, null ), 2, product1, null );
		OrderCard order2 = new OrderCard( 2L, 2, false, null, Arrays.asList( item1 ) );
		item1.setOrderCard( order2 );
		item1.getId( ).setOrderCardId( 2L );

		Product product2 = new Product( 2L, "Pizza", new BigDecimal( "15.5" ), "Tastes so good",
										Category.SNACK );
		OrderItem item2  = new OrderItem( new OrderItemId( 1L, null ), 1, product1, null );
		OrderItem item3  = new OrderItem( new OrderItemId( 2L, null ), 1, product2, null );
		OrderCard order3 = new OrderCard( 3L, 2, false, null, Arrays.asList( item2, item3 ) );
		item2.setOrderCard( order3 );
		item2.getId( ).setOrderCardId( 3L );
		item3.setOrderCard( order3 );
		item3.getId( ).setOrderCardId( 3L );

		orderCardList = new ArrayList<>( );
		orderCardList.add( order1 );
		orderCardList.add( order2 );
		orderCardList.add( order3 );
	}

	@Test
	void create( ) {
		when( repository.save( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderCard response = service.create( 2 );
		Assertions.assertEquals( response.getTable( ), 1 );
		Assertions.assertFalse( response.getWasPaid( ) );
		Assertions.assertNull( response.getWasCredit( ) );
		Assertions.assertTrue( response.getItems( ).isEmpty( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void readAll( ) {
		when( repository.findAll( ) ).thenReturn( orderCardList );
		List< OrderCard > orderCards = service.readAll( );
		Assertions.assertEquals( orderCards, orderCardList );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void readById( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		OrderCard orderCard = service.readById( 1L );
		Assertions.assertEquals( orderCard, orderCardList.get( 0 ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void getFinalPrice_1Product( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 1 ) ) );
		BigDecimal response = service.getFinalPrice( 2L );
		Assertions.assertEquals( response, new BigDecimal( "4" ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void getFinalPrice_2Products( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 2 ) ) );
		BigDecimal response = service.getFinalPrice( 3L );
		Assertions.assertEquals( response, new BigDecimal( "17.5" ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void getFinalPriceWith_NoProduct( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		BigDecimal response = service.getFinalPrice( 1L );
		Assertions.assertEquals( response, new BigDecimal( "0" ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void updateTable( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		when( repository.save( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderCard response = service.updateTable( 1L, 5 );
		Assertions.assertEquals( response.getTable( ), 5 );
		verify( repository, times( 1 ) ).findById( any( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void pay_credit( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		when( repository.save( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderCard response = service.pay( 1L, true );
		Assertions.assertTrue( response.getWasPaid( ) );
		Assertions.assertTrue( response.getWasCredit( ) );
		verify( repository, times( 1 ) ).findById( any( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void pay_notCredit( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		when( repository.save( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderCard response = service.pay( 1L, false );
		Assertions.assertTrue( response.getWasPaid( ) );
		Assertions.assertFalse( response.getWasCredit( ) );
		verify( repository, times( 1 ) ).findById( any( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}
}