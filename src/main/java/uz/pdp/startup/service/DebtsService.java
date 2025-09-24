/*
package uz.pdp.startup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.Client;
import uz.pdp.startup.entity.Debts;
import uz.pdp.startup.exception.RestException;
import uz.pdp.startup.payload.ApiResult;

import uz.pdp.startup.payload.DebtsDTO2;
import uz.pdp.startup.payload.withoutId.CategoryDTO;
import uz.pdp.startup.payload.withoutId.DebtChartDTO;
import uz.pdp.startup.payload.withoutId.DebtsDto;
import uz.pdp.startup.payload.withoutId.DebtsDto2;
import uz.pdp.startup.repository.ClientRepository;
import uz.pdp.startup.repository.CompanyRepository;
import uz.pdp.startup.repository.DebtsRepository;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DebtsService {

    private final DebtsRepository debtsRepository;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

//    public ApiResult<DebtsDTO> findDebtorsByCompanyId(Long companyId ){
//
//    }

    public ApiResult<DebtsDTO2> findById(Long id) {
        Optional<Debts> byId = debtsRepository.findById(id);
        if (!byId.isPresent()) {
            throw RestException.notFound("object doesn't exists", id);
        }
        Debts debts = byId.get();
        DebtsDTO2 build = DebtsDTO2.builder()
                .id(debts.getId())
                .debtAmount(debts.getDebtAmount())
                .clientId(debts.getClient().getId())
                .toDate(debts.getToDate())
                .fromDate(debts.getFromDate())
                .priority(debts.getPriority())
                .build();
        return ApiResult.success(build);

    }

    public ApiResult<List<DebtsDTO2>> findAll() {
        List<Debts> all = debtsRepository.findAll();
        List<DebtsDTO2> result = new ArrayList<>();
        if (all.isEmpty()) {
            throw RestException.notFound("object doesn't exists", all.size());
        }
        for (Debts debts : all) {

            result.add(DebtsDTO2.builder()
                    .id(debts.getId())
                    .debtAmount(debts.getDebtAmount())
                    .clientId(debts.getClient().getId())
                    .toDate(debts.getToDate())
                    .fromDate(debts.getFromDate())
                    .priority(debts.getPriority())
                    .build());
        }
        return ApiResult.success(result);
    }

    public ApiResult<DebtsDTO2> save(DebtsDto2 dto) {
        Optional<Client> byId = clientRepository.findById(dto.getClientId());
        if (!byId.isPresent()) {
            throw RestException.notFound("client not found", dto.getClientId());
        }
        Client client = byId.get();

        Debts build = Debts.builder()
                .priority(dto.getPriority())
                .debtAmount(dto.getDebtAmount())
                .toDate(dto.getToDate())
                .fromDate(dto.getFromDate())
                .client(client).build();
        Debts debts = debtsRepository.save(build);

        return ApiResult.success(DebtsDTO2.builder()
                .id(debts.getId())
                .fromDate(debts.getFromDate())
                .toDate(debts.getToDate())
                .priority(debts.getPriority())
                .clientId(client.getId())
                .debtAmount(debts.getDebtAmount())
                .build()
        );
    }

    public ApiResult<DebtsDTO2> update(Long id,DebtsDto2 dto) {
        Optional<Client> byId = clientRepository.findById(dto.getClientId());

        if (!byId.isPresent()) {
            throw RestException.notFound("client not found", dto.getClientId());
        }
        Client client = byId.get();
        Optional<Debts> debtsOptional = debtsRepository.findById(id);
        if (!debtsOptional.isPresent()) {
            throw RestException.notFound("object doesn't exists", id);
        }
        Debts debts = debtsOptional.get();
        debts.setDebtAmount(dto.getDebtAmount());
        debts.setPriority(dto.getPriority());
        debts.setClient(client);
        debts.setToDate(dto.getToDate());
        debts.setFromDate(dto.getFromDate());
        Debts save = debtsRepository.save(debts);

        return ApiResult.success(DebtsDTO2.builder()
                .id(save.getId())
                .debtAmount(save.getDebtAmount())
                .toDate(save.getToDate())
                .fromDate(save.getFromDate())
                .priority(save.getPriority())
                .clientId(save.getClient().getId())
                .build());
    }
    public ApiResult<DebtsDTO2> delete(Long id) {
        Debts debts = debtsRepository.findById(id).orElseThrow(() ->
                RestException.notFound("object doesn't exists", id));

        debtsRepository.delete(debts);
        return ApiResult.success(DebtsDTO2.builder()
                .id(debts.getId())
                .debtAmount(debts.getDebtAmount())
                .toDate(debts.getToDate())
                .fromDate(debts.getFromDate())
                .priority(debts.getPriority())
                .clientId(debts.getClient().getId())
                .build()
        );
    }

    public ApiResult<List<DebtChartDTO>> getDebtsByMonthBetween(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = debtsRepository.getDebtSumByDayBetween(startDate, endDate);

        String[] oylar = {"Yan","Fev","Mar","Apr","May","Iyn","Iyl","Avg","Sen","Okt","Noy","Dek"};
        List<DebtChartDTO> response = new ArrayList<>();

        for (Object[] row : results) {
            Integer month = (Integer) row[0];
            Long total = (Long) row[1];
            response.add(new DebtChartDTO(oylar[month-1], total));
        }

        return ApiResult.success(response);
    }


    public ApiResult<List<CategoryDTO>> getDebtsByCategory() {
        List<Object[]> results = debtsRepository.getDebtSumByCategory();

        List<CategoryDTO> response = new ArrayList<>();
        long totalSum = 0;

        for (Object[] row : results) {
            totalSum += (Long) row[1];
        }

        for (Object[] row : results) {
            String priority = row[0].toString();
            Long total = (Long) row[1];

            double percent = (totalSum > 0) ? (total * 100.0 / totalSum) : 0.0;

            String name = priority + " toifa";

            response.add(new CategoryDTO(name, total, percent));
        }

        return ApiResult.success(response);
    }

    public List<Map<String, Object>> getAllTimeCategoryData() {
        List<Object[]> results = debtsRepository.getDebtSumByCategory();
        long total = results.stream()
                .mapToLong(r -> (Long) r[1])
                .sum();

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            String priority = row[0].toString();
            Long sum = (Long) row[1];
            double percent = total > 0 ? (sum * 100.0 / total) : 0;

            Map<String, Object> map = new HashMap<>();
            map.put("name", priority + " toifa");
            map.put("value", percent);
            response.add(map);
        }
        return response;
    }



    public ApiResult<Map<String, Object>> getDebtTrendByInterval(String interval) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        switch (interval.toLowerCase()) {
            case "30d":
                startDate = endDate.minusDays(30);
                break;
            case "90d":
                startDate = endDate.minusDays(90);
                break;
            case "6m":
                startDate = endDate.minusMonths(6);
                break;
            case "9m":
                startDate = endDate.minusMonths(9);
                break;
            default:
                throw RestException.badRequest("interval noto‘g‘ri kiritilgan");
        }

        List<Object[]> results = debtsRepository.getDebtSumByDayBetween(startDate, endDate);

        Map<Integer, Long> monthlySums = new HashMap<>();
        long total = 0;

        for (Object[] row : results) {
            Integer month = (Integer) row[0];
            Long sum = (Long) row[1];
            monthlySums.put(month, sum);
            total += sum;
        }

        int nowMonth = endDate.getMonthValue();
        long lastMonth = monthlySums.getOrDefault(nowMonth, 0L);
        long prevMonth = monthlySums.getOrDefault(nowMonth - 1, 0L);
        double percent = (prevMonth > 0) ? ((lastMonth - prevMonth) * 100.0 / prevMonth) : 0;

        String[] oylar = {"Yan","Fev","Mar","Apr","May","Iyn","Iyl","Avg","Sen","Okt","Noy","Dek"};
        List<Map<String, Object>> chartData = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", oylar[i - 1]);
            map.put("total", monthlySums.getOrDefault(i, 0L));
            chartData.add(map);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("value", total);
        response.put("percent", percent);
        response.put("chart", chartData);

        return ApiResult.success(response);
    }
}
*/
