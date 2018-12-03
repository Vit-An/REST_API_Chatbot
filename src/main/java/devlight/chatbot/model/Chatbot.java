package devlight.chatbot.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "chatbot")
public class Chatbot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    @Size(max = 40)
    private String name;

    @NonNull
    @NotBlank
    @Size(max = 200)
    private String helloMessage;

    @NonNull
    private boolean active;

    private int questionsCount;

    private  int answersCount;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_to_chatbot",
            joinColumns = @JoinColumn(name ="chatbot_id"),
            inverseJoinColumns = @JoinColumn(name ="question_id"))
    private Set<Question> questions = new HashSet<>();

}
