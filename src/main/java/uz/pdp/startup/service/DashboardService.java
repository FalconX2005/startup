//package uz.pdp.startup.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import uz.pdp.startup.payload.ClientDTO;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class DashboardService {
//
//    private final CompanyService companyService;
//    private final ClientService clientService;
//    private final CompanyClientService companyClientService;
//    private final DebtsService debtsService;
//
//    public Long amountOfClient(Long id) {
//        List<ClientDTO> allClients = companyClientService.findAllClients(id);
//
//        return (long) allClients.size();
//
//    }
//    private Long amountOfDebtors(Long id) {
//        debtsService.findAll();
//    }
//
//
//}
