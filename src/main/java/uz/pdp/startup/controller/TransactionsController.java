package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.TransactionDTO;
import uz.pdp.startup.service.TransactionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionService transactionService;


    @GetMapping("/company/{companyId}")
    public ApiResult<List<TransactionDTO>> findByCompanyId(@PathVariable Long companyId){

        List<TransactionDTO> all = transactionService.getAll(companyId);

        if (all.isEmpty()){
            return ApiResult.error("List is empty");
        }
        return ApiResult.success(all);

    }

    @GetMapping("/client/{clientId}")
    public ApiResult<List<TransactionDTO>> findByClientId(@PathVariable Long clientId){

        List<TransactionDTO> all = transactionService.getByClientId(clientId);
        if (all.isEmpty()){
            return ApiResult.error("List is empty");
        }
        return ApiResult.success(all);
    }

}
