package server.api;

import commons.JokerAlert;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokerAlertController {
    @MessageMapping("/jokerAlert/{id}") //app/jokerAlert
    @SendTo("/topic/jokerAlert/{id}")
    public JokerAlert addJokerAlert(JokerAlert j){

        return j;
    }
}
