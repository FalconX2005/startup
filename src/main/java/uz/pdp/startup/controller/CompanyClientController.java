//package uz.pdp.startup.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import uz.pdp.startup.payload.ApiResult;
//import uz.pdp.startup.payload.ClientDTO;
//import uz.pdp.startup.payload.CompanyClientDTO;
//import uz.pdp.startup.payload.CompanyDTO;
//import uz.pdp.startup.service.CompanyClientService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/companyClient")
//@RequiredArgsConstructor
//public class CompanyClientController {
//    private final CompanyClientService companyClientService;
//
//    @GetMapping("/{id}")
//    public ApiResult<List<ClientDTO>> findAllClientsByCompanyId(@PathVariable Long companyId) {
//        List<ClientDTO> allClients = companyClientService.findAllClients(companyId);
//        return ApiResult.success(allClients);
//    }
//
//    @GetMapping("/{clientId}")
//    public ApiResult<List<CompanyDTO>> findAllCompaniesByClientId(@PathVariable Long clientId) {
//        List<CompanyDTO> companyByClientId = companyClientService.findCompanyByClientId(clientId);
//        return ApiResult.success(companyByClientId);
//    }
//    @PostMapping("/add")
//    public ApiResult<CompanyClientDTO> addClient(@RequestBody CompanyClientDTO companyClientDTO) {
//        ApiResult<CompanyClientDTO> add = companyClientService.add(companyClientDTO);
//        return add;
//    }
//    @DeleteMapping("/{id}")
//    public ApiResult<CompanyClientDTO> deleteClient(@PathVariable Long id){
//        ApiResult<CompanyClientDTO> delete = companyClientService.delete(id);
//        return delete;
//    }
//}
