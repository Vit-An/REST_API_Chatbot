package devlight.chatbot.service;

import devlight.chatbot.model.Chatbot;
import devlight.chatbot.model.Question;
import devlight.chatbot.model.dto.QuestionDto;
import devlight.chatbot.repository.ChatbotRepository;
import devlight.chatbot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Service
public class QuestionService {
    @Autowired
    ChatbotRepository chatbotRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Question createQuestion(@RequestBody QuestionDto questionDto,
                                   @PathVariable(name = "idChatbot") Long idChatbot
    ){
        Question question = new Question(questionDto.getText());
        questionRepository.save(question);

        Chatbot bot = chatbotRepository.findOneById(idChatbot);
        bot.getQuestions().add(question);

        int countQuestion = bot.getQuestionsCount();
        countQuestion++;
        bot.setQuestionsCount(countQuestion);
        chatbotRepository.save(bot);

        return question;
    }

    public Question updateQuestion(@RequestBody QuestionDto questionDto,
                                   @PathVariable(name = "idQuestion") Long idQuestion
    ){
        Question question = questionRepository.findOneById(idQuestion);

        question.setText(questionDto.getText());
        questionRepository.save(question);
        return question;
    }

    public boolean deleteQuestion(@PathVariable(name = "idQuestion") Long idQuestion,
                                  @PathVariable(name = "idChatbot") Long idChatbot
    ){
        Question question = questionRepository.findOneById(idQuestion);

        questionRepository.delete(question);
        questionRepository.save(question);

        Chatbot bot = chatbotRepository.findOneById(idChatbot);
        int countQuestion = bot.getQuestionsCount();
        countQuestion--;
        bot.setQuestionsCount(countQuestion);
        chatbotRepository.save(bot);

        return true;
    }

    public Set<Question> getAllQuestions(@PathVariable(name = "idChatbot") Long idChatbot){
        Chatbot chatbot = chatbotRepository.findOneById(idChatbot);
        Set<Question> questions = chatbot.getQuestions();

        return questions;
    }

    public Question getOneQuestion(@PathVariable(name = "idQuestion") Long idQuestion){
        Question question = questionRepository.findOneById(idQuestion);
        return question;
    }
}
