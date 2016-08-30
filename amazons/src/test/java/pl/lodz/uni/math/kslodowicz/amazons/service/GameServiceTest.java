package pl.lodz.uni.math.kslodowicz.amazons.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.lodz.uni.math.kslodowicz.amazons.config.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfiguration.class})
public class GameServiceTest {

    @Autowired
    private GameService objectUnderTest;
    
    @Test
    public void startGameTest(){
        
    }
}
