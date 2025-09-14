package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
