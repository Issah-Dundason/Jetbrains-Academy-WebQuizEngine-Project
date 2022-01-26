package engine;

import engine.quiz.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@Validated
public class RestController {
    @Resource
    private QuizService service;

    @PostMapping("/api/quizzes/{id}/solve")
    public Feedback markAnswer(@PathVariable int id, @RequestBody Answer answer,
                               @AuthenticationPrincipal UserDetails userDetails) {
        Quiz quiz = service.getQuizWithId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Integer> actualAnswers = Optional.ofNullable(quiz.getAnswer()).orElse(List.of());
        List<Integer> providedAnswers = Optional.ofNullable(answer.getAnswer()).orElse(List.of());
       if(!Set.of(actualAnswers).equals(Set.of(providedAnswers))) {
           return new Feedback(false, "Wrong answer! Please, try again.");
       }
        service.addQuizToCompletedQuizzesOfUser(id, userDetails.getUsername());
        return new Feedback(true, "Congratulations, you're right!");
    }

    @PostMapping("/api/quizzes")
    public Quiz saveQuiz(@RequestBody @Valid Quiz quiz, @AuthenticationPrincipal UserDetails userDetails) {
        return service.saveQuiz(quiz, userDetails.getUsername());
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        return service.getQuizWithId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") @Min(value = 0) int page) {
        Pageable paging = PageRequest.of(page, 10);
        return service.getQuizzes(paging);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CompletedQuiz> getCompletedQuizzes(@RequestParam(defaultValue = "0") @Min(value = 0) int page,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        Pageable paging = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return service.getCompletedQuizzes(paging, userDetails.getUsername());
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        service.deleteQuizWithId(id, userDetails.getUsername());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
