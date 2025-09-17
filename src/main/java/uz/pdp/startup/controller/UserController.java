package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.ClientDTO;
import uz.pdp.startup.payload.EmployeeDTO;
import uz.pdp.startup.payload.withoutId.EmployeeDto;
import uz.pdp.startup.service.ClientService;
import uz.pdp.startup.service.CompanyClientService;
import uz.pdp.startup.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class UserController {
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final CompanyClientService companyClientService;

    @GetMapping("/{companyId}/clients")
    public ApiResult<List<ClientDTO>> getAllClients(@PathVariable Long companyId) {
        List<ClientDTO> allClients = companyClientService.findAllClientsWithUser(companyId);
        return ApiResult.success(allClients);
    }
    @PostMapping("/{companyId}/clients")
    public ApiResult<ClientDTO> createClient(@RequestBody ClientDTO client, @PathVariable Long companyId) {
        return clientService.add(client, companyId);
    }
    @GetMapping("/clients/{clientId}")
    public ClientDTO getClientById(@PathVariable Long clientId) {
        ClientDTO clientById = clientService.getById(clientId);
        return clientById;
    }
    @PutMapping("/clients/{clientId}")
    public ClientDTO updateClient(@PathVariable Long clientId, @RequestBody ClientDTO client) {
        ClientDTO update = clientService.update(client);
        return update;
    }
    @DeleteMapping("/clients/{clientId}")
    public ClientDTO deleteClient(@PathVariable Long clientId) {
        ClientDTO delete = clientService.delete(clientId);
        return delete;
    }
    @GetMapping("/employees")
    public ApiResult<List<EmployeeDTO>> getAllEmployees() {
        return employeeService.findAll();
    }
    @PostMapping("/employees")
    public ApiResult<EmployeeDTO> createEmployee(@RequestBody EmployeeDto employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }
    @GetMapping("/employees/{employeeId}")
    public ApiResult<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.findById(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    public ApiResult<EmployeeDTO> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDTO) {
        return employeeService.update(employeeId, employeeDTO);
    }
    @DeleteMapping("/employees/{employeeId}")
    public ApiResult<EmployeeDTO> deleteEmployee(@PathVariable Long employeeId) {
        return employeeService.delete(employeeId);
    }

}
