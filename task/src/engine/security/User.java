package engine.security;

import engine.quiz.CompletedQuiz;
import engine.quiz.Quiz;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor(force = true)
public class User implements UserDetails {
    @Email(regexp = ".+@.+\\..+")
    @Id
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5)
    private String password;

    @OneToMany(mappedBy = "creator")
    private List<Quiz> uploadedQuizzes;

    @OneToMany(mappedBy = "solver", cascade = CascadeType.REMOVE)
    private List<CompletedQuiz> completedQuizzes;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
