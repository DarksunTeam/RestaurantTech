package com.darksun.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
@Embeddable
public class OrderItemId implements Serializable {
	@Serial
	private static final long serialVersionUID = 174585823816931866L;

	private Long productId;
	private Long orderCardId;
}
