package com.innoventes.test.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company")
@Entity
public class Company extends BaseEntity {

	@Id
	@SequenceGenerator(sequenceName = "company_seq", allocationSize = 1, name = "company_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
	private Long id;

	@NotEmpty(message = "Company name is mandatory")
	@Size(min = 5, message = "Company name should be at least 5 characters long")
	@Column(name = "company_name")
	private String companyName;

	@NotEmpty(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	@Column(name = "email")
	private String email;

	@PositiveOrZero(message = "Strength should be a positive number or zero")
	@Column(name = "strength")
	private Integer strength;

	@Column(name = "website_url")
	private String webSiteURL;

	@Pattern(regexp = "^[a-zA-Z]{2}\\d{2}[EN]$", message = "Company code format is invalid")
	@Column(name = "company_code", unique = true)
	private String companyCode;

}
