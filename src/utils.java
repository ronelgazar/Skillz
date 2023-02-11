import penguin_game.*;

public class utils {
    public static boolean isAttacked(Game game)
    {
        for (PenguinGroup pg : game.getEnemyPenguinGroups())
        {
            if (pg.destination == game.getMyIcepitalIcebergs()[0])
            {
                return true;
            }
            else
            {
                for (Iceberg myIceberg : game.getMyIcebergs())
                {
                    if (pg.destination == myIceberg)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

public Iceberg closest(Game game, Iceberg Iceberg){
    Iceberg closest = game.getAllIcebergs()[0];
    for(Iceberg curent : game.getAllIcebergs()){
        if(curent == Iceberg){
            continue;
        }else{
        if( curent.getTurnsTillArrival(Iceberg)< closest.getTurnsTillArrival(Iceberg)){
            closest = curent;
        }
    }
    }
    return closest;
}
}