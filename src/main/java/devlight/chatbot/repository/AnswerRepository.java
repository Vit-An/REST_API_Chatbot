package devlight.chatbot.repository;

import devlight.chatbot.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findOneById(Long idAnswer);
}
