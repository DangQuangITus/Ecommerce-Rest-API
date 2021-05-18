package com.nab.ecommerce.repositories;

import com.nab.ecommerce.models.user.UserProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);

    Optional<UserProfile> findByUsernameOrEmail(String username, String email);

    List<UserProfile> findByIdIn(List<Long> userIds);

    Optional<UserProfile> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
