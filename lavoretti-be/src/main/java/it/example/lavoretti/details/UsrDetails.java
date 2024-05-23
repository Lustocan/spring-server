package it.example.lavoretti.details;

import it.example.lavoretti.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsrDetails implements UserDetails {
    private String username ;
    private String password ;
    private boolean accountExpired ;
    private boolean locked ;
    private boolean credentialsExpired ;
    private boolean enabled;
    private List<GrantedAuthority> authorities ;

    public UsrDetails(User user){
        this.username = user.username();
        this.password = user.password();
        this.accountExpired = user.accountExpired();
        this.locked = user.locked();
        this.enabled = user.enabled();
        this.authorities = Arrays.stream(user.role().split(",")).
                           map(SimpleGrantedAuthority::new).
                           collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.authorities ;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked(){
        return this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return this.credentialsExpired;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }
}
