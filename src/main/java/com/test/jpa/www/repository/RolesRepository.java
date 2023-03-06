package com.test.jpa.www.repository;

import com.test.jpa.www.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(value = "SELECT * FROM roles " +
            "LEFT JOIN users_roles ON roles.r_id = users_roles.users_id " +
            "LEFT JOIN users ON users.u_id = users_roles.users_id " +
            "WHERE roles.role = :roleName ", nativeQuery = true)
    public Roles getRoleByName(@Param("roleName") String roleName);

    @Query(value = "SELECT * FROM roles WHERE roles.r_id = :id", nativeQuery = true)
    public Roles getRoleById(@Param("id") Long id);
}
