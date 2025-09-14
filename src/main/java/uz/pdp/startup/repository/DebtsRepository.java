package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Debts;

@Repository
public interface DebtsRepository extends JpaRepository<Debts,Long> {
}
