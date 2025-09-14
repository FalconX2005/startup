package uz.pdp.startup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.startup.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}