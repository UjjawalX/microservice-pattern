package com.ujjawal.registration.repository;

import com.ujjawal.registration.model.User;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface UserJpaRepository extends CrudRepository<User, String> {
}
