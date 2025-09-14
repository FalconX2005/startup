package uz.pdp.startup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.Company;
import uz.pdp.startup.exception.RestException;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.CompanyDTO;
import uz.pdp.startup.repository.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    private final CompanyRepository companyRepository;

    public List<CompanyDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> companiesDto = new ArrayList<>();
        if (companies.isEmpty()) {
            throw RestException.notFound("company not found",0);
        }
        for (Company company : companies) {
            if (company.isDeleted()) {
                continue;
            }
            CompanyDTO build = CompanyDTO.builder()
                    .id(company.getId())
                    .name(company.getName())
                    .email(company.getEmail())
                    .phone(company.getPhone())
                    .address(company.getLocation())
                    .build();
            companiesDto.add(build);
        }
        return companiesDto;
    }

    public CompanyDTO findById(Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        if (!byId.isPresent()) {
            throw RestException.notFound("company not found", id);
        }
        Company company = byId.get();
        CompanyDTO builder = CompanyDTO.builder()
                .email(company.getEmail())
                .name(company.getName())
                .phone(company.getPhone())
                .address(company.getLocation())
                .id(company.getId())
                .build();
        return builder;
    }

    public CompanyDTO save(CompanyDTO companyDto) {
        List<Company> byName = companyRepository.findByName(companyDto.getName());
        if (!byName.isEmpty()) {
            throw RestException.error("company already exists");
        }
        Company build = Company.builder()
                .name(companyDto.getName())
                .email(companyDto.getEmail())
                .location(companyDto.getAddress())
                .phone(companyDto.getPhone())
                .build();
        companyRepository.save(build);
        companyDto.setId(build.getId());
        return companyDto;
    }

    public CompanyDTO update(CompanyDTO companyDto) {
        Optional<Company> byId = companyRepository.findById(companyDto.getId());
        if (!byId.isPresent()) {
            throw RestException.notFound("company not found", companyDto.getId());
        }
        Company company = byId.get();
        company.setEmail(companyDto.getEmail());
        company.setPhone(companyDto.getPhone());
        company.setLocation(companyDto.getAddress());
        company.setName(companyDto.getName());
        companyRepository.save(company);
        companyDto.setId(company.getId());
        return companyDto;
    }

    public CompanyDTO delete(Long id) {
        Optional<Company> byId = companyRepository.findById(id);

        if (!byId.isPresent()) {
            throw RestException.notFound("company not found", id);
        }
        Company company = byId.get();
        companyRepository.delete(company);
        CompanyDTO build = CompanyDTO.builder()
                .address(company.getLocation())
                .name(company.getName())
                .email(company.getEmail())
                .phone(company.getPhone())
                .build();

        return build;
    }

}
