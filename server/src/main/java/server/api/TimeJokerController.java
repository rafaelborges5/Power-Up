package server.api;

import commons.DecreaseTimeJoker;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeJokerController {
    @MessageMapping("/timeJoker/{id}") //app/timeJoker
    @SendTo("/topic/timeJoker/{id}")
    public DecreaseTimeJoker addEmoji(DecreaseTimeJoker j){
        return j;
    }
}
