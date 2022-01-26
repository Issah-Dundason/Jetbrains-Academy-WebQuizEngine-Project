package engine.quiz;

import engine.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Integer> {
    Page<CompletedQuiz> findAllBySolver(User solver, Pageable paging);
}
