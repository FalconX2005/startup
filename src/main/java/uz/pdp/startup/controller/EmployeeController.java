//package uz.pdp.startup.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import uz.pdp.startup.filter.SearchService;
//import uz.pdp.startup.payload.ApiResult;
//import uz.pdp.startup.payload.EmployeeDTO;
//import uz.pdp.startup.payload.withoutId.EmployeeDto;
//import uz.pdp.startup.service.EmployeeService;
//
//import java.util.List;
//
///**
// * Created by: Umar
// * DateTime: 7/16/2025 4:42 PM
// */
//@RestController
//@RequestMapping("/employee")
//@RequiredArgsConstructor
//public class EmployeeController {
//
//    private final EmployeeService employeeService;
//    private final SearchService searchService;
//
//    @GetMapping("/read")
//    public ApiResult<List<EmployeeDTO>> getAllEmployees() {
//        return employeeService.findAll();
//    }
//
//    @GetMapping("/read/{id}")
//    public ApiResult<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
//        return employeeService.findById(id);
//    }
//
//    @PostMapping("/create")
//    public ApiResult<EmployeeDTO> createEmployee(@RequestBody EmployeeDto employeeDTO) {
//        return employeeService.createEmployee(employeeDTO);
//    }
//
//
//    @PutMapping("/update/{id}")
//    public ApiResult<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDTO) {
//        return employeeService.update(id, employeeDTO);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ApiResult<EmployeeDTO> deleteEmployee(@PathVariable Long id) {
//        return employeeService.delete(id);
//    }
//
//    @GetMapping("/search")
//    public ApiResult<List<EmployeeDTO>> searchEmployee(@RequestParam String name) {
//        List<EmployeeDTO> employeeDTOS = searchService.searchEmployee(name);
//        return ApiResult.success(employeeDTOS);
//    }
//}
