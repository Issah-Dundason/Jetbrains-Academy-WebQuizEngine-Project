package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.security.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor()
@Data
@Entity(name = "Quizzes")
@NoArgsConstructor
public class Quiz {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;


    @JoinColumn(name = "UserID")
    @JsonIgnore
    @ManyToOne
    private User creator;

    @ElementCollection
    @NotEmpty
    @Size(min = 2)
    private List<String> options;

    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;
}
