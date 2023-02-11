import penguin_game.*;
public class strategy  {
    public static final double penguinAmountPrecent = 1.25;
    
    public void defend(Game game)
    {
        for (PenguinGroup pg : game.getEnemyPenguinGroups())
        {
            Iceberg closestIceberg = null;
            for (Iceberg myIceberg : game.getMyIcebergs())
            {
                if (closestIceberg == null)
                {
                    closestIceberg = myIceberg;
                }
                else
                {
                    if (myIceberg.getTurnsTillArrival(pg.destination) < closestIceberg.getTurnsTillArrival(pg.destination))
                    {
                        closestIceberg = myIceberg;
                    }
                }
            }
        }

    }
    public void attack(Game game)
    {
        //use closest iceberg to attack from utils
        //send penguins to closest iceberg

        Iceberg closestIceberg = null;
        for (Iceberg myIceberg : game.getMyIcebergs())
        {
            if (closestIceberg == null)
            {
                closestIceberg = myIceberg;
            }
            else
            {
                if (myIceberg.getTurnsTillArrival(game.getEnemyIcepitalIcebergs()[0]) < closestIceberg.getTurnsTillArrival(game.getEnemyIcepitalIcebergs()[0]))
                {
                    closestIceberg = myIceberg;
                }
            }
        }
        if (closestIceberg != null)
        {
            closestIceberg.sendPenguins(game.getEnemyIcepitalIcebergs()[0], closestIceberg.penguinAmount);
        }
        

    }
    

    public void neutral(Game game)
    {
        //check if i can upgrade my icebergs or if i have sent oenguins see if i cna accelerate them
        for (Iceberg myIceberg : game.getMyIcebergs())
        {
            if (myIceberg.canUpgrade())
            {
                myIceberg.upgrade();
            }
            else if (myIceberg.penguinAmount*penguinAmountPrecent < myIceberg.upgradeCost)
            {
                Iceberg closestIceberg = null;
                for (Iceberg myIceberg2 : game.getMyIcebergs())
                {
                    if (closestIceberg == null)
                    {
                        closestIceberg = myIceberg2;
                    }
                    else
                    {
                        if (myIceberg2.getTurnsTillArrival(myIceberg) < myIceberg.getTurnsTillArrival(closestIceberg))
                        {
                            closestIceberg = myIceberg2;
                        }
                    }
                }
                if (closestIceberg != null)
                {
                    myIceberg.sendPenguins(closestIceberg, myIceberg.upgradeCost - myIceberg.penguinAmount);
                }
            }
        }
    }



}
