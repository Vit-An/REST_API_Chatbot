package devlight.chatbot.controller;

import devlight.chatbot.model.Chatbot;
import devlight.chatbot.model.Question;
import devlight.chatbot.model.dto.QuestionDto;
import devlight.chatbot.repository.ChatbotRepository;
import devlight.chatbot.repository.QuestionRepository;
import devlight.chatbot.repository.UserRepository;
import devlight.chatbot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/adminapi/v1/admins/{id}/chatbots/{idChatbot}/questions")

public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping
    public Question createQuestion(@RequestBody QuestionDto questionDto,
                                   @PathVariable(name = "idChatbot") Long idChatbot
    ){
        return questionService.createQuestion(questionDto, idChatbot);
    }

    @PatchMapping("/{id}")
    public Question updateQuestion(@RequestBody QuestionDto questionDto,
                                   @PathVariable(name = "idQuestion") Long idQuestion
    ){
        return questionService.updateQuestion(questionDto, idQuestion);
    }

    @DeleteMapping("/{idQuestion}")
    public boolean deleteQuestion(@PathVariable(name = "idQuestion") Long idQuestion,
                                   @PathVariable(name = "idChatbot") Long idChatbot
    ){
       return questionService.deleteQuestion(idQuestion, idChatbot);
    }

    @GetMapping
    public Set<Question> getAllQuestions(@PathVariable(name = "idChatbot") Long idChatbot){
        return questionService.getAllQuestions(idChatbot);
    }

    @GetMapping("/{idQuestion}")
    public Question getOneQuestion(@PathVariable(name = "idQuestion") Long idQuestion){
        return questionService.getOneQuestion(idQuestion);
    }
}
