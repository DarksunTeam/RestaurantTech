package com.darksun.service;

import com.darksun.model.OrderCard;
import com.darksun.repository.OrderCardRepository;
import com.darksun.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
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
		orderCardList = TestUtils.generate( OrderCard.class.getSimpleName( ) );
	}

	@Test
	void create( ) {
		when( repository.save( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderCard response = service.create( 2 );
		Assertions.assertEquals( 1, response.getTable( ) );
		Assertions.assertFalse( response.getWasPaid( ) );
		Assertions.assertNull( response.getWasCredit( ) );
		Assertions.assertTrue( response.getItems( ).isEmpty( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void readAll( ) {
		when( repository.findAll( ) ).thenReturn( orderCardList );
		List< OrderCard > orderCards = service.readAll( );
		Assertions.assertEquals( orderCardList, orderCards );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void readById( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		OrderCard orderCard = service.readById( 1L );
		Assertions.assertEquals( orderCardList.get( 0 ), orderCard );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void getFinalPrice_1Product( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 1 ) ) );
		BigDecimal response = service.getFinalPrice( 2L );
		Assertions.assertEquals( new BigDecimal( "4" ), response );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void getFinalPrice_2Products( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 2 ) ) );
		BigDecimal response = service.getFinalPrice( 3L );
		Assertions.assertEquals( new BigDecimal( "17.5" ), response );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void getFinalPriceWith_NoProduct( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		BigDecimal response = service.getFinalPrice( 1L );
		Assertions.assertEquals( new BigDecimal( "0" ), response );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void updateTable( ) {
		when( repository.findById( any( ) ) ).thenReturn(
				Optional.ofNullable( orderCardList.get( 0 ) ) );
		when( repository.save( any( ) ) ).thenReturn( orderCardList.get( 0 ) );
		OrderCard response = service.updateTable( 1L, 5 );
		Assertions.assertEquals( 5, response.getTable( ) );
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