package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.DebtsDTO;
import uz.pdp.startup.payload.withoutId.DebtsDto;
import uz.pdp.startup.service.DebtsService;

import java.util.List;

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

}
