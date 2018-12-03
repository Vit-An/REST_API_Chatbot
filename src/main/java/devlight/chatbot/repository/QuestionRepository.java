package devlight.chatbot.repository;

import devlight.chatbot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findOneById (Long idQuestion);
}
