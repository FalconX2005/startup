package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Employee;
import uz.pdp.startup.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByCompanyId(Long companyId);

    Optional<Employee> findByUser(User user);

}