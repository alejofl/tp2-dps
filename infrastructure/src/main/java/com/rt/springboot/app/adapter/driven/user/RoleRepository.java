package com.rt.springboot.app.adapter.driven.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleRelationalEntity, Long> {
}
