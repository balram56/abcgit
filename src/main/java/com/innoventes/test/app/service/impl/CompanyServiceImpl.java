package com.innoventes.test.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.innoventes.test.app.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.error.ApplicationErrorCodes;
import com.innoventes.test.app.exception.ResourceNotFoundException;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.repository.CompanyRepository;
import com.innoventes.test.app.service.CompanyService;
import com.innoventes.test.app.util.ServiceHelper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ServiceHelper serviceHelper;

	@Override
	public List<Company> getAllCompanies() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		companyRepository.findAll().forEach(companyList::add);
		return companyList;
	}

	@Override
	public Company addCompany(Company company) throws ValidationException {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) throws ValidationException {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		company.setId(existingCompanyRecord.getId());
		return companyRepository.save(company);
	}

	@Override
	public void deleteCompany(Long id) {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		companyRepository.deleteById(existingCompanyRecord.getId());
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
	}

	@Override
	public Company getCompanyByCode(String companyCode) {
		return companyRepository.findByCompanyCode(companyCode);
	}

	@Override
	public Company partialUpdateCompany(Long id, CompanyDTO companyDTO) {
		Company existingCompany = companyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

		// Apply partial updates from companyDTO to the existing Company entity
		if (companyDTO.getCompanyName() != null) {
			existingCompany.setCompanyName(companyDTO.getCompanyName());
		}
		if (companyDTO.getEmail() != null) {
			existingCompany.setEmail(companyDTO.getEmail());
		}
		if (companyDTO.getStrength() != null) {
			existingCompany.setStrength(companyDTO.getStrength());
		}
		if (companyDTO.getWebSiteURL() != null) {
			existingCompany.setWebSiteURL(companyDTO.getWebSiteURL());
		}

		// Validate the updated Company entity
		validateCompany(existingCompany);

		// Save the updated Company entity to the database
		return companyRepository.save(existingCompany);
	  }


	private void validateCompany(Company company) {
		// Create a validator instance using Hibernate Validator
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		// Validate the Company entity
		Set<ConstraintViolation<Company>> violations = validator.validate(company);

		// Check if there are any violations
		if (!violations.isEmpty()) {
			// Construct error message from violations
			StringBuilder errorMessage = new StringBuilder();
			for (ConstraintViolation<Company> violation : violations) {
				errorMessage.append(violation.getPropertyPath())
						.append(": ")
						.append(violation.getMessage())
						.append("; ");
			}

			// Throw ValidationException with error message
			throw new RuntimeException("Company validation failed: " + errorMessage);
		}
	}
}
