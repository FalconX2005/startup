package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Debts;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DebtsRepository extends JpaRepository<Debts, Long> {

    List<Debts> getAllByCompanyId(Long companyId);

    List<Debts> findByCompanyIdAndClientId(Long companyId, Long clientId);

    @Query("SELECT MONTH(d.fromDate), SUM(d.debtAmount) " +
            "FROM Debts d " +
            "WHERE FUNCTION('DATE', d.fromDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY MONTH(d.fromDate) " +
            "ORDER BY MONTH(d.fromDate)")
    List<Object[]> getDebtSumByDayBetween(LocalDate startDate, LocalDate endDate);


    @Query("SELECT d.priority, SUM(d.debtAmount) FROM Debts d GROUP BY d.priority")
    List<Object[]> getDebtSumByCategory();



    @Query("SELECT MONTH(d.fromDate), SUM(d.debtAmount) " +
            "FROM Debts d " +
            "WHERE d.fromDate BETWEEN :startDate AND :endDate " +
            "GROUP BY MONTH(d.fromDate) " +
            "ORDER BY MONTH(d.fromDate)")
    List<Object[]> getDebtSumByDayBet(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    // Jami qarzdorlar
    @Query("SELECT COUNT(DISTINCT d.client.id) FROM Debts d WHERE d.debtAmount > 0")
    long countAllDebtors();

    // Muddati o‘tgan qarzdorlar
    @Query("SELECT COUNT(DISTINCT d.client.id) FROM Debts d WHERE d.debtAmount > 0 AND d.toDate < CURRENT_DATE")
    long countExpiredDebtors();


        /*@Query("""
                    SELECT SUM(d.debtAmount)
                    FROM Debts d
                    JOIN CompanyClient cc ON d.client.id = cc.client.id
                    WHERE cc.company.id = :companyId
                      AND d.fromDate <= :today
                      AND d.toDate >= :startOfMonth
                """)
        Long getCurrentMonthDebt(@Param("companyId") Long companyId,
                                 @Param("startOfMonth") LocalDate startOfMonth,
                                 @Param("today") LocalDate today);*/




}
