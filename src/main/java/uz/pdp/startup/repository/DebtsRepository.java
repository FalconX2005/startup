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

    @Query("SELECT MONTH(d.fromDate), SUM(d.debtAmount) " +
            "FROM Debts d " +
            "WHERE FUNCTION('DATE', d.fromDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY MONTH(d.fromDate) " +
            "ORDER BY MONTH(d.fromDate)")
    List<Object[]> getDebtSumByDayBetween(LocalDate startDate, LocalDate endDate);




    @Query("SELECT d.priority, SUM(d.debtAmount) FROM Debts d GROUP BY d.priority")
    List<Object[]> getDebtSumByCategory();
}
