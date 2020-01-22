package com.expatrio.usermanager.user.repositories;


import com.expatrio.usermanager.user.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User getUserByUsernameAndPassword(String username, String password);
    User getById(UUID uuid);
    User getUserByUsername(String userName);
}
