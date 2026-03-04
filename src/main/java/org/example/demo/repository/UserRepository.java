package org.example.demo.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.demo.entity.User;
import org.example.demo.entity.enums.Provider;
import org.example.demo.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT provider FROM User WHERE email = :email")
    Optional<Provider> getProviderByEmail(@Param("email") String email);

    @Query("SELECT id FROM User ORDER BY id DESC LIMIT 1")
    Optional<Long> findLastId();

    @Modifying
    @Query("UPDATE User SET provider = :provider WHERE email = :email")
    void setNewProviderByEmail(@Param("email") String email, @Param("provider") Provider provider);

    @Query("SELECT role FROM User WHERE email = :email")
    Optional<Role> getRoleByEmail(@Param("email") String email);
}
