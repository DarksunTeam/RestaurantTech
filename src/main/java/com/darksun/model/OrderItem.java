package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem implements Serializable {
	@Serial
	private static final long serialVersionUID = 4972177093989809781L;

	@EmbeddedId
	private OrderItemId id;
	private Integer     quantity;
}
