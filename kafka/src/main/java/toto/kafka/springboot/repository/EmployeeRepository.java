package toto.kafka.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toto.kafka.springboot.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
