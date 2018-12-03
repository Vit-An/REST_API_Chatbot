package devlight.chatbot.controller;

import devlight.chatbot.model.Answer;
import devlight.chatbot.model.Chatbot;
import devlight.chatbot.model.Question;
import devlight.chatbot.model.dto.AnswerDto;
import devlight.chatbot.repository.AnswerRepository;
import devlight.chatbot.repository.ChatbotRepository;
import devlight.chatbot.repository.QuestionRepository;
import devlight.chatbot.repository.UserRepository;
import devlight.chatbot.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adminapi/v1/admins/{id}/chatbots/{idChatbot}/questions/{idQuestion}/answers")

public class AnswerController {
    @Autowired
    AnswerService answerService;


    @PostMapping
    public Answer createAnswer(@RequestBody AnswerDto answerDto,
                               @PathVariable(name = "idChatbot") Long idChatbot,
                               @PathVariable(name = "idQuestion") Long idQuestion
    ){
       return answerService.createAnswer(answerDto, idQuestion, idChatbot);
    }

    @PatchMapping("/{idAnswer}")
    public Answer updateAnswer(@RequestBody AnswerDto answerDto,
                               @PathVariable(name = "idAnswer") Long idAnswer
    ){
        return answerService.updateAnswer(answerDto, idAnswer);
    }

    @DeleteMapping("/{idAnswer}")
    public boolean deleteAnswer(@PathVariable(name = "idAnswer") Long idAnswer,
                               @PathVariable(name = "idChatbot") Long idChatbot
    ){
        return answerService.deleteAnswer(idAnswer, idChatbot);
    }
}
