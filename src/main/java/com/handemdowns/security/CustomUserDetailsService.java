package com.handemdowns.security;

import com.handemdowns.common.util.StringUtil;
import com.handemdowns.persistence.dao.UserRepository;
import com.handemdowns.persistence.model.Permission;
import com.handemdowns.persistence.model.Role;
import com.handemdowns.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;
    private HttpServletRequest request;

    @Autowired
	public CustomUserDetailsService(UserRepository userRepository, LoginAttemptService loginAttemptService, HttpServletRequest request) {
		this.userRepository = userRepository;
		this.loginAttemptService = loginAttemptService;
		this.request = request;
	}

    @Override
    public UserDetails loadUserByUsername(String email) {
        String ip = StringUtil.getUserIp(request);
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("IP is blocked");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPermissions(roles));
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        permissions.addAll(collection.stream().map(Permission::getName).collect(Collectors.toList()));
        return permissions;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
		return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}