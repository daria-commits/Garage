package com.example.GarageCarsavvyProject.Repository;

import com.example.GarageCarsavvyProject.Enums.TypeDeRole;
import com.example.GarageCarsavvyProject.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Integer> {

    Role findByLibelle(TypeDeRole name);

}
