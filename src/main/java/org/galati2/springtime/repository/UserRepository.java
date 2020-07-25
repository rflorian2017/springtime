package org.galati2.springtime.repository;

import org.galati2.springtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
