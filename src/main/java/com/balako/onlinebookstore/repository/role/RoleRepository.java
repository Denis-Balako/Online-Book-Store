package com.balako.onlinebookstore.repository.role;

import com.balako.onlinebookstore.enums.RoleName;
import com.balako.onlinebookstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(RoleName roleName);
}
