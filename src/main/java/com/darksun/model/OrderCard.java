package com.darksun.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
	@Column( name = "tableCode" )
	private Integer table;
	private Boolean wasPaid;
	private Boolean wasCredit;

	@JsonBackReference
	@OneToMany( mappedBy = "orderCard" )
	private List< OrderItem > items;
}
