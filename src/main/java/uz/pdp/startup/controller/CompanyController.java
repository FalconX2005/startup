//package uz.pdp.startup.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import uz.pdp.startup.filter.SearchService;
//import uz.pdp.startup.payload.ApiResult;
//import uz.pdp.startup.payload.CompanyDTO;
//import uz.pdp.startup.service.CompanyService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/company")
//@RequiredArgsConstructor
//public class CompanyController {
//
//    private final CompanyService companyService;
//    private final SearchService searchService;

//    @GetMapping
//    public List<CompanyDTO> findAll() {
//        List<CompanyDTO> all = companyService.findAll();
//        return all;
//    }
//
//    @GetMapping("/{id}")
//    public CompanyDTO getById(@PathVariable Long id) {
//        CompanyDTO byId = companyService.findById(id);
//        return byId;
//    }

//    @PostMapping("/create")
//    public CompanyDTO create(@RequestBody CompanyDTO companyDto) {
//        CompanyDTO save = companyService.save(companyDto);
//        return save;
//    }

//    @DeleteMapping("/{id}")
//    public boolean delete(@PathVariable Long id) {
//        CompanyDTO delete = companyService.delete(id);
//        return true;
//    }


//    @PutMapping("/{id}")
//    public CompanyDTO update(@PathVariable Long id, @RequestBody CompanyDTO companyDto) {
//        CompanyDTO update = companyService.update(companyDto);
//
//            return update;
//    }


//    @GetMapping("/search")
//    public ApiResult<List<CompanyDTO>> search(@RequestParam String name) {
//        List<CompanyDTO> companyDTOS = searchService.searchCompany(name);
//        return ApiResult.success(companyDTOS);
//    }
//}
