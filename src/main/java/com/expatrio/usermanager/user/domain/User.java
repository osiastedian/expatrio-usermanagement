package com.expatrio.usermanager.user.domain;


import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @NoArgsConstructor
    @Data
    public class UserAuthority implements GrantedAuthority {
        UserRole role;
        UserAuthority(UserRole role)
        {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            String authority = "No Access";
            switch (this.role) {
                case ADMIN: authority = "Admin"; break;
                case CUSTOMER:authority = "Customer"; break;
            }
            return authority;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String username;

    @NonNull
    String password;
    String firstName;
    String lastName;
    UserRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new UserAuthority(this.role));
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
        return true;
    }


}
