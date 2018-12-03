package devlight.chatbot.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfile {
    private String username;
    private String name;
    private String password;

}


