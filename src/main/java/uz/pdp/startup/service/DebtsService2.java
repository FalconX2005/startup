package uz.pdp.startup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.Client;
import uz.pdp.startup.entity.Company;
import uz.pdp.startup.entity.Debts;
import uz.pdp.startup.exception.RestException;
import uz.pdp.startup.payload.DebtsDTO2;
import uz.pdp.startup.payload.withoutId.DebtsDto2;
import uz.pdp.startup.repository.ClientRepository;
import uz.pdp.startup.repository.CompanyRepository;
import uz.pdp.startup.repository.DebtsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebtsService2 {

    private final DebtsRepository debtsRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository  clientRepository;

    public List<DebtsDTO2> getDebts(Long companyId){
        List<Debts> allByCompanyId = debtsRepository.getAllByCompanyId(companyId);
        if(allByCompanyId.isEmpty()){
            throw  RestException.notFound("object not found ", companyId);
        }
        List<DebtsDTO2> resultList = new ArrayList<>();

        for(Debts debts : allByCompanyId){
           resultList.add( DebtsDTO2.builder()
                           .id(debts.getId())
                    .companyId(debts.getCompany().getId())
                    .clientId(debts.getClient().getId())
                    .debtAmount(debts.getDebtAmount())
                    .toDate(debts.getToDate())
                    .fromDate(debts.getFromDate())
                    .priority(debts.getPriority())
                    .build());
        }
        return resultList;
    }

    public List<DebtsDTO2> getById(Long companyId, Long clientId){
        List<Debts> byClientId = debtsRepository.findByCompanyIdAndClientId(companyId, clientId);
        List<DebtsDTO2> resultList = new ArrayList<>();
        if(byClientId.isEmpty()){
            throw RestException.notFound("qarzdorlik topilmadi " , clientId);
        }

        for (Debts debts : byClientId){
            resultList.add(DebtsDTO2.builder()
                            .id(debts.getId())
                    .companyId(debts.getCompany().getId())
                    .clientId(debts.getClient().getId())
                    .debtAmount(debts.getDebtAmount())
                    .toDate(debts.getToDate())
                    .fromDate(debts.getFromDate())
                    .priority(debts.getPriority())
                    .build());
        }
        return resultList;

    }

    public DebtsDTO2 createDebt (DebtsDto2 debtsDto2){
        if (Objects.isNull(debtsDto2)){
            throw RestException.error("object is null");
        }

        Optional<Company> byId = companyRepository.findById(debtsDto2.getCompanyId());
        if (!byId.isPresent()){
            throw  RestException.notFound("company not found " , debtsDto2.getCompanyId());
        }

        Company company = byId.get();
        Optional<Client> byId1 = clientRepository.findById(debtsDto2.getClientId());
         if(!byId1.isPresent()){
             throw RestException.notFound("client not found " , debtsDto2.getClientId());
         }
        Client client = byId1.get();

        Debts build = Debts.builder()
                .client(client)
                .company(company)
                .debtAmount(debtsDto2.getDebtAmount())
                .fromDate(debtsDto2.getFromDate())
                .toDate(debtsDto2.getToDate())
                .priority(debtsDto2.getPriority())
                .build();
        Debts save = debtsRepository.save(build);

        client.setBalance(client.getBalance()+debtsDto2.getDebtAmount());
        clientRepository.save(client);

        DebtsDTO2 result = DebtsDTO2.builder()
                .id(save.getId())
                .clientId(save.getClient().getId())
                .debtAmount(save.getDebtAmount())
                .fromDate(save.getFromDate())
                .priority(save.getPriority())
                .companyId(save.getCompany().getId())
                .toDate(save.getToDate())
                .build();

        return result;

    }

    public DebtsDTO2 update (DebtsDTO2 debtsDTO){
        Optional<Debts> byId = debtsRepository.findById(debtsDTO.getId());

        if (!byId.isPresent()){
            throw  RestException.notFound("object not found ", debtsDTO.getId());
        }

        Debts debts = byId.get();

        debts.setDebtAmount(debtsDTO.getDebtAmount());
        debts.setFromDate(debtsDTO.getFromDate());
        debts.setPriority(debtsDTO.getPriority());
        debts.setToDate(debtsDTO.getToDate());
        Debts save = debtsRepository.save(debts);
        return DebtsDTO2.builder()
                .id(save.getId())
                .toDate(save.getToDate())
                .debtAmount(save.getDebtAmount())
                .clientId(save.getClient().getId())
                .companyId(save.getCompany().getId())
                .fromDate(save.getFromDate())
                .priority(save.getPriority()).build();
    }
    public DebtsDTO2 deleteById(Long id){
        Optional<Debts> byId = debtsRepository.findById(id);
        if (!byId.isPresent()){
            throw  RestException.notFound("object not found ", id);
        }
        Debts debts = byId.get();
        debtsRepository.delete(debts);
        return DebtsDTO2.builder().id(id)
                .priority(debts.getPriority())
                .fromDate(debts.getFromDate())
                .companyId(debts.getCompany().getId())
                .clientId(debts.getClient().getId())
                .debtAmount(debts.getDebtAmount())
                .toDate(debts.getToDate()).build();
    }
}
