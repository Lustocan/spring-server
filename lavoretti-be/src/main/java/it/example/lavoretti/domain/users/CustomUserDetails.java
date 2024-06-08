package it.example.lavoretti.domain.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final String username;
    private final String password;
    private final boolean accountExpired;
    private final boolean locked;
    private final boolean credentialsExpired;
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.username = user.username();
        this.password = user.password();
        this.accountExpired = user.accountExpired();
        this.locked = user.locked();
        this.enabled = user.enabled();
        this.authorities = Arrays.stream(user.role().name().split(",")).
                                 map(SimpleGrantedAuthority::new).
                                 collect(Collectors.toList());
        credentialsExpired = user.locked() || !user.enabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
