package com.darksun.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
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

	@OneToMany( mappedBy = "orderCard", fetch = FetchType.EAGER )
	@JsonIgnoreProperties( "orderCard" )
	private List< OrderItem > items;
}
