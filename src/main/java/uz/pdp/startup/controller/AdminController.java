package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.filter.SearchService;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.ClientDTO;
import uz.pdp.startup.payload.CompanyDTO;
import uz.pdp.startup.service.ClientService;
import uz.pdp.startup.service.CompanyClientService;
import uz.pdp.startup.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/admin/companies")
@RequiredArgsConstructor
public class AdminController {


    private final CompanyService companyService;
    private final SearchService searchService;
    private final CompanyClientService companyClientService;
    private final ClientService clientService;

    @GetMapping
    public ApiResult<List<CompanyDTO>> findAll() {
        return ApiResult.success(companyService.findAll());
    }

    @GetMapping("/{companyId}")
    public ApiResult<CompanyDTO> getById(@PathVariable Long companyId) {
        return ApiResult.success(companyService.findById(companyId));
    }

    @PostMapping
    public ApiResult<CompanyDTO> create(@RequestBody CompanyDTO companyDto) {
        return ApiResult.success(companyService.save(companyDto));
    }

    @DeleteMapping("/{companyId}")
    public ApiResult<CompanyDTO> delete(@PathVariable Long companyId) {
        return ApiResult.success(companyService.delete(companyId));
    }

    @PutMapping("/{companyId}")
    public ApiResult<CompanyDTO> update(@PathVariable Long companyId, @RequestBody CompanyDTO companyDto) {
        return ApiResult.success(companyService.update(companyDto));

    }

    @GetMapping("/search")
    public ApiResult<List<CompanyDTO>> search(@RequestParam String name) {
        return searchService.searchCompany(name);
    }

    @GetMapping("/{companyId}/clients")
    public ApiResult<List<ClientDTO>> findAllClientsByCompanyId(@PathVariable Long companyId) {
        List<ClientDTO> allClients = companyClientService.findAllClientsWithUser(companyId);
        return ApiResult.success(allClients);
    }

    @PostMapping("/{companyId}/clients")
    public ApiResult<ClientDTO> createClient(@RequestBody ClientDTO client, @PathVariable Long companyId) {
        return clientService.add(client, companyId);
    }

    @GetMapping("/client/{clientId}")
    public ClientDTO getClientById(@PathVariable Long clientId) {
        ClientDTO clientById = clientService.getById(clientId);
        return clientById;
    }

    @PutMapping("/updateClient/{clientId}")
    public ClientDTO updateClient(@PathVariable Long clientId, @RequestBody ClientDTO client) {
        ClientDTO update = clientService.update(client);
        return update;
    }

    @DeleteMapping("/deleteClient/{clientId}")
    public ClientDTO deleteClient(@PathVariable Long clientId) {
        ClientDTO delete = clientService.delete(clientId);
        return delete;
    }

    @GetMapping("/{companyId}/clientSearch")
    public ApiResult<List<ClientDTO>> searchClient(@RequestParam String name,@PathVariable Long companyId) {

        return searchService.searchClient(companyId,name);
    }

}
