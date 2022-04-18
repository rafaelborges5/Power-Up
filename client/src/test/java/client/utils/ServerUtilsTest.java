package client.utils;

import commons.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServerUtilsTest {

    @Disabled
    @Test
    void addPlayer() {
        ServerUtils su= new ServerUtils();
        Activity act1 = new Activity("00-shower",
                "00/shower.png",
                "Question 1",
                100,
                "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower");
        Player p = new Player("asdasd",213);
        Question q1 = new MultipleChoiceQuestion(act1,1000,"EASY",1);
        JokerCard j1 = new AdditionalPointsJoker("AdditionalPointsJoker","Description",
                false,
                p,q1);
        JokerCard j2 = new QuestionChangeJoker("QuestionChangeJoker","Description",false);
        JokerCard j3 = new EliminateOptionJoker("EliminateOptionJoker","Description",
                false,(MultipleChoiceQuestion) q1);

        ArrayList<JokerCard> jokerCards = new ArrayList<>(Arrays.asList(j1,j2,j3));
        p.setJokerCards(jokerCards);
        p.setJokerCards(null);
        su.addPlayer(p);
    }

    @Test
    @Disabled
    void testSocket() {
            ServerUtils sut = new ServerUtils();
        MultiPlayerGame game = sut.getCurrentMultiplayerGame();
        assertNotNull(game);
        sut.registerForNewPlayers("/topic/updateLobby", System.out::println);
    }

    @Test
    @Disabled
    void testFileUpload() throws IOException {
        ServerUtils sut = new ServerUtils();
        File image = new File("/home/rafael/TUDelft/cse/year1/q3/oopp/quizzz/docs/pictures/multiplayer.jpeg");
        MockMultipartFile file = new MockMultipartFile("test",
                "originalTest", "jpeg",
                Files.readAllBytes(Paths.get(image.getAbsolutePath())));
        //sut.uploadImage(file);
    }

    @Disabled
    @Test
    void testScore() {
        ServerUtils sut = new ServerUtils();
        sut.registerForScoreUpdates("/topic/updateScores/3", q -> System.out.println(q.getUsername()));
        sut.updatePlayerScore(new Player("some", 55), 3);
    }
}