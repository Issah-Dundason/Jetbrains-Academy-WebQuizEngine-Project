package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.security.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class CompletedQuiz {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonIgnore
    private int completionId;

    private int id;

    private LocalDateTime completedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User solver;

    @PrePersist
    private void updateDate() {
        completedAt = LocalDateTime.now();
    }
}
