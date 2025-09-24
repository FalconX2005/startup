package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT COUNT(c) FROM Client c WHERE c.company.id = :companyId")
    long countByCompanyId(@Param("companyId") Long companyId);

}
