package devlight.chatbot.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotDto {
    private String name;
    private String helloMessage;
    private boolean active;
    private Long idUser;
}
