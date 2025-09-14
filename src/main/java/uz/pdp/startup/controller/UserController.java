package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.ClientDTO;
import uz.pdp.startup.payload.EmployeeDTO;
import uz.pdp.startup.payload.withoutId.EmployeeDto;
import uz.pdp.startup.service.ClientService;
import uz.pdp.startup.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class UserController {
    private final ClientService clientService;
    private final EmployeeService employeeService;

    @GetMapping("/clients")
    public ApiResult<List<ClientDTO>> getAllClients() {
        List<ClientDTO> allClients = clientService.getAll();
        return ApiResult.success(allClients);
    }
    @PostMapping("/clients")
    public ApiResult<ClientDTO> createClient(@RequestBody ClientDTO client, @PathVariable Long companyId) {
        return clientService.add(client, companyId);
    }
    @GetMapping("/clients/{clientId}")
    public ClientDTO getClientById(@PathVariable Long id) {
        ClientDTO clientById = clientService.getById(id);
        return clientById;
    }
    @PutMapping("/clients/{clientId}")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO client) {
        ClientDTO update = clientService.update(client);
        return update;
    }
    @DeleteMapping("/clients/{clientId}")
    public ClientDTO deleteClient(@PathVariable Long id) {
        ClientDTO delete = clientService.delete(id);
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
    public ApiResult<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PutMapping("/employees/{employeeId}")
    public ApiResult<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDTO) {
        return employeeService.update(id, employeeDTO);
    }
    @DeleteMapping("/employees/employeeId")
    public ApiResult<EmployeeDTO> deleteEmployee(@PathVariable Long id) {
        return employeeService.delete(id);
    }

}
