package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.payload.ApiResult;

import uz.pdp.startup.payload.DebtsDTO2;
import uz.pdp.startup.payload.withoutId.CategoryDTO;
import uz.pdp.startup.payload.withoutId.DebtChartDTO;
import uz.pdp.startup.payload.withoutId.DebtsDto2;
import uz.pdp.startup.service.DebtsService2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debts")
@RequiredArgsConstructor
public class DebtsController {
    private final DebtsService2 debtsService2;

    @GetMapping("/{company_id}")
    public ApiResult<List<DebtsDTO2>> getAllDebts(@PathVariable Long company_id) {
        List<DebtsDTO2> debts = debtsService2.getDebts(company_id);
        return ApiResult.success(debts);
    }

    @GetMapping("/{companyId}/{clientId}")
    public ApiResult<List<DebtsDTO2>> getDebtById(@PathVariable Long companyId, @PathVariable Long clientId) {
        List<DebtsDTO2> byId = debtsService2.getById(companyId, clientId);
        return ApiResult.success(byId);
    }

    @PostMapping("/create")
    public ApiResult<DebtsDTO2> createDebt(@RequestBody DebtsDto2 dto) {
        return ApiResult.success(debtsService2.createDebt(dto));
    }

    @PutMapping("/update")
    public ApiResult<DebtsDTO2> updateDebtById(@RequestBody DebtsDTO2 dto) {
        return ApiResult.success(debtsService2.update(dto));
    }

    @DeleteMapping("/{id}")
    public ApiResult<DebtsDTO2> deleteDebtById(@PathVariable Long id) {
        return ApiResult.success(debtsService2.deleteById(id));
    }

    @GetMapping("/chart/month/between")
    public ApiResult<List<DebtChartDTO>> getByMonthBetween(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return debtsService2.getDebtsByMonthBetween(startDate, endDate);
    }


    @GetMapping("/category-chart")
    public ApiResult<List<CategoryDTO>> getDebtsByCategory() {
        return debtsService2.getDebtsByCategory();
    }

    @GetMapping("/chart/all-time")
    public ResponseEntity<?> getAllTimeChart() {
        return ResponseEntity.ok(Map.of(
                "data", debtsService2.getAllTimeCategoryData(),
                "success", true
        ));
    }

    @GetMapping("/chart/interval")
    public ApiResult<Map<String, Object>> getDebtTrendByInterval(
            @RequestParam("interval") String interval
    ) {
        return debtsService2.getDebtTrendByInterval(interval);
    }

    @GetMapping("/total-debtors")
    public ApiResult<Map<String, Object>> getTotalDebtors() {
        return debtsService2.getTotalDebtors();
    }

    @GetMapping("/expired-debtors")
    public ApiResult<Map<String, Object>> getExpiredDebtors() {
        return debtsService2.getExpiredDebtors();
    }
/*
    @GetMapping("/current-month-income/{companyId}")
    public ApiResult<Map<String, Object>> getCurrentMonthIncome(@PathVariable Long companyId) {
        return debtsService2.getCurrentMonthIncome(companyId);
    }*/


}
