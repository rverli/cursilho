package com.sicpa.thymeleaf.poc.aqualis.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;

public class CustomUserDetails extends User implements UserDetails {
	private static final long serialVersionUID = 1L;
	private List<String> userRoles;

	public CustomUserDetails(User user, List<String> userRoles) {
		super(user);
		this.userRoles = userRoles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.isActive();
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userRoles == null) ? 0 : userRoles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if( obj == null )
			return false;
		if (!super.equals(obj))
			return false;
		CustomUserDetails other = (CustomUserDetails) obj;
		if (userRoles == null) {
			if (other.userRoles != null)
				return false;
		} else if (!userRoles.equals(other.userRoles))
			return false;
		return true;
	}
	
	
}
