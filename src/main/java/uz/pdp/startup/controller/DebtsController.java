package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.DebtsDTO;
import uz.pdp.startup.payload.DebtsDTO2;
import uz.pdp.startup.payload.withoutId.DebtsDto;
import uz.pdp.startup.payload.withoutId.DebtsDto2;
import uz.pdp.startup.service.DebtsService;
import uz.pdp.startup.service.DebtsService2;

import java.util.List;

@RestController
@RequestMapping("/debts")
@RequiredArgsConstructor
public class DebtsController {
    private final DebtsService2 debtsService;

    @GetMapping("/{companyId}")
    public ApiResult<List<DebtsDTO2>> getAllDebts(@PathVariable Long companyId) {
        List<DebtsDTO2> debts = debtsService.getDebts(companyId);
        return ApiResult.success(debts);
    }

    @GetMapping("/{companyId}/{clientId}")
    public ApiResult<List<DebtsDTO2>> getDebtById(@PathVariable Long companyId,@PathVariable Long clientId) {
        List<DebtsDTO2> byId = debtsService.getById(companyId, clientId);
        return ApiResult.success(byId);
    }
    @PostMapping("/create")
    public ApiResult<DebtsDTO2> createDebt(@RequestBody DebtsDto2 dto) {
        return ApiResult.success(debtsService.createDebt(dto));
    }
    @PutMapping("/update")
    public ApiResult<DebtsDTO2> updateDebtById(@RequestBody DebtsDTO2 dto) {
        return ApiResult.success(debtsService.update(dto));
    }
    @DeleteMapping("/{id}")
    public ApiResult<DebtsDTO2> deleteDebtById(@PathVariable Long id) {
        return ApiResult.success(debtsService.deleteById(id));
    }

}
