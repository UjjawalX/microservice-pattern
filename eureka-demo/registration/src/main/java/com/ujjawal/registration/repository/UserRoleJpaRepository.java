package com.ujjawal.registration.repository;

import com.ujjawal.registration.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleJpaRepository extends CrudRepository<UserRole,String> {
}
