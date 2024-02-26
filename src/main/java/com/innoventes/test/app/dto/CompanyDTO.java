package com.innoventes.test.app.dto;

import javax.validation.constraints.*;

import com.innoventes.test.app.service.EvenNumberOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyDTO {

	private Long id;

	@NotEmpty(message = "Company name is mandatory")
	@Size(min = 5, message = "Company name should be at least 5 characters long")
	private String companyName;

	@NotEmpty(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@PositiveOrZero(message = "Strength should be a positive number or zero")
	@NotNull
	@EvenNumberOrZero
	private Integer strength;

	private String webSiteURL;

	@Pattern(regexp = "^[a-zA-Z]{2}\\d{2}[EN]$", message = "Company code format is invalid")
	private String companyCode;
}
