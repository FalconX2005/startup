package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Debts;

import java.util.List;

@Repository
public interface DebtsRepository extends JpaRepository<Debts,Long> {

    List<Debts> getAllByCompanyId(Long companyId);

    List<Debts> findByCompanyIdAndClientId(Long companyId, Long clientId);
}
