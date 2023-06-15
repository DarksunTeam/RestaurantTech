package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
@Embeddable
public class OrderItemId implements Serializable {
	@Serial
	private static final long serialVersionUID = 1528511812041574507L;

	@ManyToOne
	private Product   product;
	@ManyToOne
	private OrderCard orderCard;
}
