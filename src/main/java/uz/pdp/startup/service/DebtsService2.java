package uz.pdp.startup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.Client;
import uz.pdp.startup.entity.Company;
import uz.pdp.startup.entity.Debts;
import uz.pdp.startup.entity.Transactions;
import uz.pdp.startup.exception.RestException;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.DebtsDTO2;
import uz.pdp.startup.payload.withoutId.CategoryDTO;
import uz.pdp.startup.payload.withoutId.DebtChartDTO;
import uz.pdp.startup.payload.withoutId.DebtsDto2;
import uz.pdp.startup.repository.ClientRepository;
import uz.pdp.startup.repository.CompanyRepository;
import uz.pdp.startup.repository.DebtsRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DebtsService2 {

    private final DebtsRepository debtsRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository  clientRepository;
    private final TransactionService transactionService;

    public List<DebtsDTO2> getDebts(Long companyId){
        List<Debts> allByCompanyId = debtsRepository.getAllByCompanyId(companyId);
        if(allByCompanyId.isEmpty()){
            throw  RestException.notFound("object not found ", companyId);
        }
        List<DebtsDTO2> resultList = new ArrayList<>();

        for(Debts debts : allByCompanyId){
           resultList.add( DebtsDTO2.builder()
                           .id(debts.getId())
                    .companyId(debts.getCompany().getId())
                    .clientId(debts.getClient().getId())
                    .debtAmount(debts.getDebtAmount())
                    .toDate(debts.getToDate())
                    .fromDate(debts.getFromDate())
                    .priority(debts.getPriority())
                    .build());
        }
        return resultList;
    }

    public List<DebtsDTO2> getById(Long companyId, Long clientId){
        List<Debts> byClientId = debtsRepository.findByCompanyIdAndClientId(companyId, clientId);
        List<DebtsDTO2> resultList = new ArrayList<>();
        if(byClientId.isEmpty()){
            throw RestException.notFound("qarzdorlik topilmadi " , clientId);
        }

        for (Debts debts : byClientId){
            resultList.add(DebtsDTO2.builder()
                            .id(debts.getId())
                    .companyId(debts.getCompany().getId())
                    .clientId(debts.getClient().getId())
                    .debtAmount(debts.getDebtAmount())
                    .toDate(debts.getToDate())
                    .fromDate(debts.getFromDate())
                    .priority(debts.getPriority())
                    .build());
        }
        return resultList;

    }

    public DebtsDTO2 createDebt (DebtsDto2 debtsDto2){
        if (Objects.isNull(debtsDto2)){
            throw RestException.error("object is null");
        }

        Optional<Company> byId = companyRepository.findById(debtsDto2.getCompanyId());
        if (!byId.isPresent()){
            throw  RestException.notFound("company not found " , debtsDto2.getCompanyId());
        }

        Company company = byId.get();
        Optional<Client> byId1 = clientRepository.findById(debtsDto2.getClientId());
         if(!byId1.isPresent()){
             throw RestException.notFound("client not found " , debtsDto2.getClientId());
         }
        Client client = byId1.get();


        Debts build = Debts.builder()
                .client(client)
                .company(company)
                .debtAmount(debtsDto2.getDebtAmount())
                .fromDate(debtsDto2.getFromDate())
                .toDate(debtsDto2.getToDate())
                .priority(debtsDto2.getPriority())
                .build();
        Debts save = debtsRepository.save(build);

        client.setBalance(client.getBalance()+debtsDto2.getDebtAmount());
        clientRepository.save(client);

        transactionService.create(Transactions.builder()
                .amount(debtsDto2.getDebtAmount())
                .transactionDate(LocalDate.now())
                .client(client)
                .company(company)
                .build()
        );

        DebtsDTO2 result = DebtsDTO2.builder()
                .id(save.getId())
                .clientId(save.getClient().getId())
                .debtAmount(save.getDebtAmount())
                .fromDate(save.getFromDate())
                .priority(save.getPriority())
                .companyId(save.getCompany().getId())
                .toDate(save.getToDate())
                .build();

        return result;

    }

    public DebtsDTO2 update (DebtsDTO2 debtsDTO){
        Optional<Debts> byId = debtsRepository.findById(debtsDTO.getId());

        if (!byId.isPresent()){
            throw  RestException.notFound("object not found ", debtsDTO.getId());
        }

        Debts debts = byId.get();

        debts.setDebtAmount(debts.getDebtAmount()-debtsDTO.getDebtAmount());
        debts.setFromDate(debtsDTO.getFromDate());
        debts.setPriority(debtsDTO.getPriority());
        debts.setToDate(debtsDTO.getToDate());
        Client client = debts.getClient();
        client.setBalance(client.getBalance()-debtsDTO.getDebtAmount());
        clientRepository.save(client);
        Debts save = debtsRepository.save(debts);

        transactionService.create(Transactions.builder()
                .amount(debtsDTO.getDebtAmount())
                .transactionDate(LocalDate.now())
                .client(client)
                .company(debts.getCompany())
                .build()
        );
        return DebtsDTO2.builder()
                .id(save.getId())
                .toDate(save.getToDate())
                .debtAmount(save.getDebtAmount())
                .clientId(save.getClient().getId())
                .companyId(save.getCompany().getId())
                .fromDate(save.getFromDate())
                .priority(save.getPriority()).build();
    }
    public DebtsDTO2 deleteById(Long id){
        Optional<Debts> byId = debtsRepository.findById(id);
        if (!byId.isPresent()){
            throw  RestException.notFound("object not found ", id);
        }
        Debts debts = byId.get();
        debtsRepository.delete(debts);
        return DebtsDTO2.builder().id(id)
                .priority(debts.getPriority())
                .fromDate(debts.getFromDate())
                .companyId(debts.getCompany().getId())
                .clientId(debts.getClient().getId())
                .debtAmount(debts.getDebtAmount())
                .toDate(debts.getToDate()).build();
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


    public ApiResult<Map<String, Object>> getTotalDebtors() {
        long total = debtsRepository.countAllDebtors();
        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        return ApiResult.success(response);
    }

    public ApiResult<Map<String, Object>> getExpiredDebtors() {
        long total = debtsRepository.countExpiredDebtors();
        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        return ApiResult.success(response);
    }


    /*public ApiResult<Map<String, Object>> getCurrentMonthIncome(Long companyId) {
        LocalDate today = LocalDate.now(); // bugungi sana
        LocalDate startOfMonth = today.withDayOfMonth(1); // oy boshidan

        Long totalDebt = debtsRepository.getCurrentMonthDebt(companyId, startOfMonth, today);
        if (totalDebt == null) totalDebt = 0L;

        Map<String, Object> response = new HashMap<>();
        response.put("totalIncome", totalDebt);

        return ApiResult.success(response);
    }*/




}
