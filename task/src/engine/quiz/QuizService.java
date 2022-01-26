package engine.quiz;

import engine.security.User;
import engine.security.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class QuizService {
    @Resource
    private QuizRepository quizRepo;
    @Resource
    private UserService userService;
    @Resource
    private CompletedQuizRepository completedQuizRepo;

    public Quiz saveQuiz(Quiz quiz, String uploaderEmail) {
        User user = userService.findUserByEmail(uploaderEmail);
        quiz.setCreator(user);
        return quizRepo.save(quiz);
    }

    public Page<Quiz> getQuizzes(Pageable paging) {
        return quizRepo.findAll(paging);
    }

    public Page<CompletedQuiz> getCompletedQuizzes(Pageable paging, String username) {
        User user = userService.findUserByEmail(username);
        return completedQuizRepo.findAllBySolver(user, paging);
    }

    public Optional<Quiz> getQuizWithId(int id) {
        return quizRepo.findById(id);
    }

    public void deleteQuizWithId(int id, String uploaderEmail) {
        Quiz quiz = getQuizWithId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!quiz.getCreator().getEmail().equals(uploaderEmail))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        quizRepo.delete(quiz);
    }

    public void addQuizToCompletedQuizzesOfUser(int id, String username) {
        CompletedQuiz quiz = new CompletedQuiz();
        quiz.setId(id);
        User user = userService.findUserByEmail(username);
        quiz.setSolver(user);
        completedQuizRepo.save(quiz);
    }
}
