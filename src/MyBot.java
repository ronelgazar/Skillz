//package bots;
import penguin_game.*;
import java.util.random.*;

public class MyBot implements SkillzBot {
    @Override
    public void doTurn(Game game)   {
        //do a random move from strategy
        //if the move is not valid do another random move
        //if the move is valid do it
        strategy strt = new strategy();
        
        int random = (int)(Math.random()*3);
        if (random == 0)
        {
            strt.attack(game);
        }
        else if (random == 1)
        {
            strt.defend(game);
        }
        else
        {
            strt.neutral(game);
        }
    }
}
