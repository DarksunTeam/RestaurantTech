package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderCard implements Serializable {
	@Serial
	private static final long serialVersionUID = -2244137535711693694L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long    id;
	private Integer table;
	private Boolean wasPaid;
	private Boolean wasCredit;
}
