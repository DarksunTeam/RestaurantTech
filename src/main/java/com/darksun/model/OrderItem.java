package com.darksun.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem implements Serializable {
	@Serial
	private static final long serialVersionUID = -5571597112917389177L;

	@EmbeddedId
	private OrderItemId id;
	private Integer     quantity;

	@ManyToOne
	@MapsId( "productId" )
	private Product product;

	@ManyToOne
	@JsonIgnoreProperties( "items" )
	@MapsId( "orderCardId" )
	private OrderCard orderCard;
}
