package com.ameren.eis.integration_demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRepository
    extends CrudRepository<UserEvent, Long> {

    //@Query("SELECT id, email, rating, active FROM UserEvent where email=?")
    List<UserEvent> findByEmail(String email);
}
