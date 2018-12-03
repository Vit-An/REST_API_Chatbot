package devlight.chatbot.controller;

import devlight.chatbot.model.Chatbot;
import devlight.chatbot.model.User;
import devlight.chatbot.model.dto.ChatbotDto;
import devlight.chatbot.repository.ChatbotRepository;
import devlight.chatbot.repository.UserRepository;
import devlight.chatbot.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/adminapi/v1/admins/{id}/chatbots")
public class ChatbotController {
    @Autowired
    ChatbotService chatbotService;

    @PostMapping
    public Chatbot CreateChatbot(@RequestBody ChatbotDto chatbotDto,
                                 @PathVariable(value = "id") Long id
    ){
        return chatbotService.CreateChatbot(chatbotDto, id);
    }

    @PatchMapping("/{idChatbot}")
    public Chatbot UpdateChatbot(@RequestBody ChatbotDto chatbotDto,
                                 @PathVariable(value = "idChatbot") Long idChatbot
    ){
        return chatbotService.UpdateChatbot(chatbotDto, idChatbot);
    }

    @DeleteMapping("/{idChatbot}")
    public boolean DeleteChatbot(@PathVariable(value = "idChatbot") Long idChatbot
    ){
        return chatbotService.DeleteChatbot(idChatbot);
    }

    @GetMapping
    public Set<Chatbot> getListAllChatbots(@PathVariable(value = "id") Long idUser){
        return chatbotService.getListAllChatbots(idUser);
    }

    @GetMapping("/{idChatbot}")
    public Chatbot getOneChatbot(@PathVariable(value = "idChatbot") Long idChatbot
    ){
        return chatbotService.getOneChatbot(idChatbot);
    }
}
