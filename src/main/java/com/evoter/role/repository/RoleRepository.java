package com.evoter.role.repository;

import com.evoter.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    boolean existsByName(String name);

//    @Query(value = "select r from Role r where r.columnsName not in :columnNames")
//    Page<ColumnTable> findAllByColumnNames(@Param("columnNames") String columnNames, Pageable pageable);

//    List<Role> findByOrderByLastModifiedDateDesc();

}
