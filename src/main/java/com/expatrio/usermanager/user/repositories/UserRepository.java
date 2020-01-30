package com.expatrio.usermanager.user.repositories;


import com.expatrio.usermanager.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
    User getUserByUsernameAndPassword(String username, String password);
    User getById(UUID uuid);
    User getUserByUsername(String userName);
}
