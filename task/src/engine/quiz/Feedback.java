package engine.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Feedback {
    private boolean success;
    private String feedback;
}
