package com.ujjawal.registration.repository;

import com.ujjawal.registration.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository <Employee, Integer> {
    List<Employee> findEmployeewithLastname(String lastname);
    List<Employee> findEmployeewithFirstname(String firstnames);
    @Query("select e from Employee e where firstname = ?1 or lastname = ?2")
    List<Employee> findEmplyeewithFirstorlastname(String firstname,String lastname);
}