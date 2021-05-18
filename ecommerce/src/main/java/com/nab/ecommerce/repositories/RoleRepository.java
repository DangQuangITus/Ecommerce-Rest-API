package com.nab.ecommerce.repositories;

import com.nab.ecommerce.enums.RoleName;
import com.nab.ecommerce.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
