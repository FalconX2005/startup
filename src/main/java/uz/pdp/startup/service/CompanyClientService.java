package uz.pdp.startup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.Client;
import uz.pdp.startup.entity.Company;
import uz.pdp.startup.entity.CompanyClient;
import uz.pdp.startup.exception.RestException;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.ClientDTO;
import uz.pdp.startup.payload.CompanyClientDTO;
import uz.pdp.startup.payload.CompanyDTO;
import uz.pdp.startup.repository.ClientRepository;
import uz.pdp.startup.repository.CompanyClientRepository;
import uz.pdp.startup.repository.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyClientService {
    private final CompanyClientRepository companyClientRepository;
    private final ClientService clientService;
    private final CompanyService companyService;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

//    // companyga tegishli clientlarni company id bo'yicha sortlash
//    public List<ClientDTO> findAllClients(Long companyId){
//        List<CompanyClient> clients= companyClientRepository.findByCompanyId(companyId);
//        List<ClientDTO> result = new ArrayList<>();
//        for (CompanyClient client: clients) {
//            ClientDTO byId = clientService.getById(client.getClient().getId());
//            result.add(byId);
//        }
//        return result;
//    }

    // companyga tegishli clientlarni user bilan birga olib kelish
    public List<ClientDTO> findAllClientsWithUser(Long companyId) {
        List<Client> clients = companyClientRepository.findClientsByCompanyIdWithUser(companyId);

        return clients.stream()
                .map(client -> ClientDTO.builder()
                        .id(client.getId())
                        .firstName(client.getFirstName())
                        .lastName(client.getLastName())
                        .phoneNumber(client.getPhone())
                        .balance(client.getBalance())
                        .username(client.getUser().getUsername())
                        .role(client.getUser().getRole())
                        .build())
                .toList();
    }


    // companyni client orqali topish uchun  yozildi
    public List<CompanyDTO> findCompanyByClientId(Long clientId){
        List<CompanyClient> companies = companyClientRepository.getByClientId(clientId);
        List<CompanyDTO> result = new ArrayList<>();
        for (CompanyClient companyClient : companies) {
            CompanyDTO byId = companyService.findById(companyClient.getCompany().getId());
            result.add(byId);
        }
        return result;
    }

    public ApiResult<CompanyClientDTO> add (CompanyClientDTO companyClientDTO){

        List<CompanyClient> byCompanyId = companyClientRepository.findByCompanyId(companyClientDTO.getCompanyId());
        for (CompanyClient companyClient : byCompanyId){
            if (companyClient.getClient().getId().equals(companyClientDTO.getClientId())){
                throw RestException.error("this client already exists");
            }
        }

        Optional<Company> company = companyRepository.findById(companyClientDTO.getCompanyId());
        Optional<Client> client = clientRepository.findById(companyClientDTO.getClientId());
        if (client.isPresent() &&  company.isPresent() ) {
            CompanyClient build = CompanyClient.builder()
                    .client(client.get())
                    .company(company.get())
                    .build();
            CompanyClient save = companyClientRepository.save(build);
            CompanyClientDTO result = CompanyClientDTO.builder()
                    .companyId(save.getCompany().getId())
                    .clientId(save.getClient().getId())
                    .id(save.getId())
                    .build();
            return ApiResult.success(result);
        }
        throw RestException.error("company or client doesn't exists");

    }
    public ApiResult<CompanyClientDTO> delete (Long id ){
        CompanyClient companyClient = companyClientRepository.findById(id).orElseThrow(()
                -> RestException.error("company or client doesn't exists"));
        companyClientRepository.delete(companyClient);
        return ApiResult.success(CompanyClientDTO.builder()
                .clientId(companyClient.getCompany().getId())
                .companyId(companyClient.getCompany().getId())
                .id(companyClient.getId())
                .build());
    }
}




