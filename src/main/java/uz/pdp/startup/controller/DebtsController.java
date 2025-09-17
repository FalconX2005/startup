package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.DebtsDTO;
import uz.pdp.startup.payload.withoutId.CategoryDTO;
import uz.pdp.startup.payload.withoutId.DebtChartDTO;
import uz.pdp.startup.payload.withoutId.DebtsDto;
import uz.pdp.startup.service.DebtsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debts")
@RequiredArgsConstructor
public class DebtsController {
    private final DebtsService  debtsService;

    @GetMapping
    public ApiResult<List<DebtsDTO>> getAllDebts() {
        return debtsService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResult<DebtsDTO> getDebtById(@PathVariable Long id) {
        return debtsService.findById(id);
    }
    @PostMapping("/create")
    public ApiResult<DebtsDTO> createDebt(@RequestBody DebtsDto dto) {
        return debtsService.save(dto);
    }
    @PutMapping("/update")
    public ApiResult<DebtsDTO> updateDebtById(@RequestBody DebtsDto dto, @PathVariable Long id) {
        return debtsService.update(id,dto);
    }
    @DeleteMapping("/{id}")
    public ApiResult<DebtsDTO> deleteDebtById(@PathVariable Long id) {
        return debtsService.delete(id);
    }

    @GetMapping("/chart/month/between")
    public ApiResult<List<DebtChartDTO>> getByMonthBetween(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("toDate")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return debtsService.getDebtsByMonthBetween(startDate, endDate);
    }


    @GetMapping("/category-chart")
    public ApiResult<List<CategoryDTO>> getDebtsByCategory() {
        return debtsService.getDebtsByCategory();
    }

    @GetMapping("/chart/all-time")
    public ResponseEntity<?> getAllTimeChart() {
        return ResponseEntity.ok(Map.of(
                "data", debtsService.getAllTimeCategoryData(),
                "success", true
        ));
    }
}
