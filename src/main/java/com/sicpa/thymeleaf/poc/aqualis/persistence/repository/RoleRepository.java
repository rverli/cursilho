package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	/**
	 * Get the roles related to the user through the profiles
	 * @param userId
	 * @return
	 */
	@Query(value = "select DISTINCT r.DS_ROLE "
			 + "from TB_ROLE r, TB_PROFILE_ROLE pr, TB_USER_PROFILE up, TB_USER ur "	
			 + " where ur.bl_user_active = 1 "
			 + " and (:userId is not null and ur.id_user = :userId) "
			 + " and up.xid_user = ur.id_user "
			 + " and pr.xid_profile = up.xid_profile "
			 + " and r.id_role = pr.xid_role ORDER BY 1"
			 , nativeQuery = true)
	public List<String> findUserRoleByUserAndUserIsActive(@Param("userId") Long userId);

}
