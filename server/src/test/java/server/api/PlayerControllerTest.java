package server.api;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import server.sevice.PlayerService;

import java.util.ArrayList;
import java.util.List;

public class PlayerControllerTest {
    private TestPlayerRepository myRepo;
    private PlayerController controller;

    @BeforeEach
    public void setup(){
        myRepo =new TestPlayerRepository();
        controller = new PlayerController(new PlayerService(myRepo));

    }
    @Test
    public void databaseIsUSed() {
        controller.addNewPlayer(getPlayer());

        assertTrue( myRepo.calledMethods.contains("save"));
    }
    @Test
    public void testGetPlayers(){
        controller.addNewPlayer(getPlayer());
        controller.addNewPlayer(new Player(2L,"alex1234",2122));
        List<Player> localList  = List.of(getPlayer(), new Player(2L,"alex1234",2122));
        List<Player> repositoryList = controller.getPlayers();
        assertEquals(localList,repositoryList);
    }
    @Test
    public void testDeletePlayer(){
        controller.addNewPlayer(getPlayer());
        List<Player> localList  = new ArrayList<>();
        controller.deletePlayer(1L);
        List<Player> repositoryList = controller.getPlayers();
        assertEquals(localList,repositoryList);
    }
    public static Player getPlayer(){
        return new Player(1L,"rafael1234",4234);
    }

}
