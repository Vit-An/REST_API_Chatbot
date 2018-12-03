package devlight.chatbot.repository;

import devlight.chatbot.model.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {
    Chatbot findOneById (Long idChatbot);
    Chatbot findOneByQuestionsCount(Long questionsCount);

}