package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Client;
import uz.pdp.startup.entity.CompanyClient;

import java.util.List;

@Repository
public interface CompanyClientRepository extends JpaRepository<CompanyClient,Long> {
    List<CompanyClient> findByCompanyId(Long companyId);

    @Query("SELECT cc.client FROM CompanyClient cc " +
            "JOIN FETCH cc.client.user " +
            "WHERE cc.company.id = :companyId")
    List<Client> findClientsByCompanyIdWithUser(@Param("companyId") Long companyId);


    List<CompanyClient> getByClientId(Long clientId);
}
