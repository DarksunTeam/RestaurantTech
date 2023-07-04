package com.darksun.util;

import com.darksun.model.OrderCard;
import com.darksun.model.OrderItem;
import com.darksun.model.OrderItemId;
import com.darksun.model.Product;
import com.darksun.model.type.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

	public static List generate( String type ) {
		OrderCard order1 = new OrderCard( 1L, 1, false, null, new ArrayList<>( ) );

		Product product1 = new Product( 1L, "Soda", new BigDecimal( "2" ),
										"A good drink to refresh", Category.DRINK );
		OrderItem item1  = new OrderItem( new OrderItemId( 1L, null ), 2, product1, null );
		OrderCard order2 = new OrderCard( 2L, 2, false, null, List.of( item1 ) );
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

		Product product3 = new Product( 3L, "Beer", new BigDecimal( "3.5" ),
										"Neither strong nor weak", Category.ALCOHOLIC_DRINK );
		Product product4 = new Product( 4L, "Shrimp stroganoff", new BigDecimal( "10" ),
										"The best meal of your life", Category.MEAL );

		List list = null;

		if ( type.equals( Product.class.getSimpleName( ) ) ) {
			list = new ArrayList< Product >( );
			list.add( product1 );
			list.add( product2 );
			list.add( product3 );
			list.add( product4 );
		}

		if ( type.equals( OrderCard.class.getSimpleName( ) ) ) {
			list = new ArrayList< OrderCard >( );
			list.add( order1 );
			list.add( order2 );
			list.add( order3 );
		}

		if ( type.equals( OrderItem.class.getSimpleName( ) ) ) {
			list = new ArrayList< OrderItem >( );
			list.add( item1 );
			list.add( item2 );
			list.add( item3 );
		}
		return list;
	}
}
