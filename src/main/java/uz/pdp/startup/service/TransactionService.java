package uz.pdp.startup.service;

import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.Transactions;
import uz.pdp.startup.exception.RestException;
import uz.pdp.startup.payload.TransactionDTO;
import uz.pdp.startup.repository.TransactionsRepository;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository transactionsRepository;

    public List<TransactionDTO> getAll(Long companyId){

        List<Transactions> byCompanyId = transactionsRepository.findByCompanyId(companyId);

        if (byCompanyId.isEmpty()){
            throw RestException.notFound("transactions not found " , companyId);
        }


        return byCompanyId.stream()
                .map(transaction -> convertToDTO(transaction))
                .toList();

    }

    public List<TransactionDTO> getByClientId(Long clientId){
        List<Transactions> byClientId = transactionsRepository.findByClientId(clientId);

        return byClientId.stream().map(transaction -> convertToDTO(transaction))
                .toList();
    }


    public TransactionDTO create(Transactions transaction) {
        if (Objects.isNull(transaction)) {
            throw RestException.badRequest("transaction is null");
        }

        // Transactionni saqlash
        Transactions savedTransaction = transactionsRepository.save(transaction);

        // Saqlangan entityni DTO ga convert qilish
        return convertToDTO(savedTransaction);
    }


    private TransactionDTO convertToDTO(Transactions transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setClientId(transaction.getClient().getId());
        dto.setCompanyId(transaction.getCompany().getId());
        // boshqa kerakli fieldlarni qo'shing
        return dto;
    }
}
