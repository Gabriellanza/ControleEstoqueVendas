package br.edu.toledoprudente.pojo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@EntityScan
@Table(name="Users")
public class Users extends AbstractEntity<Integer> implements UserDetails {
    @Column(length = 150, nullable = false)
    private String username;
    @Column(length = 350, nullable = false)
    private String password;
    @Column( nullable = false)
    private Boolean enabled;
    @Column(name="isAdmin")

    @JsonIgnore
    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.PERSIST)
    private List<Clientes> clientes;

    private boolean isAdmin;
    public boolean isAdmin() {
        return isAdmin;
    }

    public List<Clientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<Clientes> clientes) {
        this.clientes = clientes;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Set<AppAuthority> getAppAuthorities() {
        return appAuthorities;
    }
    public void setAppAuthorities(Set<AppAuthority> appAuthorities) {
        this.appAuthorities = appAuthorities;
    }
    @OneToMany(
            fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy = "appUsers"
    )
    private Set<AppAuthority> appAuthorities;
    public Users(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,

            Collection<? extends AppAuthority> authorities//,// PersonalInformation personalInformation
    ) {
        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        // this.accountNonExpired = accountNonExpired;
        // this.credentialsNonExpired = credentialsNonExpired;
        // this.accountNonLocked = accountNonLocked;
        // this.appAuthorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        // this.personalInformation = personalInformation;
    }
    public Users() {
    }

    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
}
