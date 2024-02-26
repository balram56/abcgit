package com.innoventes.test.app.controller;

import com.innoventes.test.app.dto.CompanyDTO;
import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.mapper.CompanyMapper;
import com.innoventes.test.app.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/companies")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
		List<Company> companyList = companyService.getAllCompanies();
		
		List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();
		
		for (Company entity : companyList) {
			companyDTOList.add(companyMapper.getCompanyDTO(entity));
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.status(HttpStatus.OK).location(location).body(companyDTOList);
	}

	@PostMapping("/companies")
	public ResponseEntity<CompanyDTO> addCompany(@Valid @RequestBody CompanyDTO companyDTO)
			throws ValidationException {
		Company company = companyMapper.getCompany(companyDTO);
		Company newCompany = companyService.addCompany(company);
		CompanyDTO newCompanyDTO = companyMapper.getCompanyDTO(newCompany);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCompany.getId())
				.toUri();
		return ResponseEntity.created(location).body(newCompanyDTO);
	}

	@PutMapping(value = "/companies/{id}")
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CompanyDTO companyDTO) throws ValidationException {
		Company company = companyMapper.getCompany(companyDTO);
		Company updatedCompany = companyService.updateCompany(id, company);
		CompanyDTO updatedCompanyDTO = companyMapper.getCompanyDTO(updatedCompany);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedCompanyDTO);
	}

	@DeleteMapping(value = "/companies/{id}")
	public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable(value = "id") Long id) {
		companyService.deleteCompany(id);
		return ResponseEntity.noContent().build();
	}

	public String getMessage(String exceptionCode) {
		return messageSource.getMessage(exceptionCode, null, LocaleContextHolder.getLocale());
	}

	@GetMapping("/companies/{id}")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
		Company company = companyService.getCompanyById(id);
		if (company != null) {
			CompanyDTO companyDTO = companyMapper.getCompanyDTO(company);
			return ResponseEntity.ok(companyDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@GetMapping("/api/v1/companies/byCode/{companyCode}")
	public ResponseEntity<CompanyDTO> getCompanyByCode(@PathVariable String companyCode) {
		Company company = companyService.getCompanyByCode(companyCode);
		if (company != null) {
			CompanyDTO companyDTO = companyMapper.getCompanyDTO(company);
			return ResponseEntity.ok(companyDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@PatchMapping("/api/v1/companies/{id}")
	public ResponseEntity<CompanyDTO> partialUpdateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
		try {
			Company updatedCompany = companyService.partialUpdateCompany(id, companyDTO);
			CompanyDTO updatedCompanyDTO = companyMapper.getCompanyDTO(updatedCompany);
			return ResponseEntity.ok(updatedCompanyDTO);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new CompanyDTO()); // Return empty DTO or handle error as needed
		}
	}
}
