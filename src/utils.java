import java.util.List;

import penguin_game.*;

public class Utils {
    public static Iceberg findClosest(Iceberg[] icebergs, Iceberg iceberg) {
        Iceberg closestIceberg = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Iceberg myIceberg : icebergs) {
            if (myIceberg.equals(iceberg)) {
                continue; // skip the same iceberg
            }
            
            int distance = myIceberg.getTurnsTillArrival(iceberg) + 1;
            
            if (distance < minDistance) {
                closestIceberg = myIceberg;
                minDistance = distance;
            }
        }
        
        return closestIceberg;
    }
    

    public static boolean isUnderAttack(Game game, Iceberg attackedIceberg) {
        if (game.getEnemyPenguinGroups().length > 0) {
            for (PenguinGroup pg : game.getEnemyPenguinGroups()) {
                if (pg.destination == attackedIceberg)
                    return true;
            }
        }
        return false;
    }
    
    public static  boolean alreadySent(Game game, Iceberg dest) {
        for (PenguinGroup group : game.getMyPenguinGroups()) {
            if (group.destination == dest) {
                return true;
            }
        }
        return false;
    }
    
    public static void sendEnemy(Game game,Iceberg source, Iceberg dest) {
        int amount = dest.penguinsPerTurn * source.getTurnsTillArrival(dest) + dest.penguinAmount + 1;
        if (source.canSendPenguins(dest, amount)) {
            source.sendPenguins(dest, amount);
            PenguinGroup pg = game.getMyPenguinGroups()[0];
            if (pg.penguinAmount > 5) {
                pg.accelerate();
            }
        }
    }
    
    public static void counterAttack(Game game,Iceberg myIceberg, Iceberg source, int enemyAmount, Iceberg[] icebergs) {
        int penguinAmount = myIceberg.penguinAmount;
        if (myIceberg == game.getAllIcepitalIcebergs()[0]) {
            if (myIceberg.canSendPenguins(source, myIceberg.penguinAmount - 1)) {
                myIceberg.sendPenguins(source, myIceberg.penguinAmount - 1);
            }
        }
        if (enemyAmount < myIceberg.penguinAmount) {
            Iceberg neighbor = findClosest(icebergs, myIceberg);
            if (myIceberg.canSendPenguins(source, myIceberg.penguinAmount - 1)) {
                myIceberg.sendPenguins(source, myIceberg.penguinAmount - 1);
            }
            if (neighbor.canSendPenguins(myIceberg, enemyAmount - penguinAmount)) {
                neighbor.sendPenguins(myIceberg, enemyAmount - penguinAmount);
            }
        } else {
            Iceberg neighbor = findClosest(icebergs, myIceberg);
            int myAmount = source.getTurnsTillArrival(myIceberg) * myIceberg.penguinsPerTurn + myIceberg.penguinAmount;
            if (neighbor.canSendPenguins(myIceberg, enemyAmount - myAmount + 1)) {
                neighbor.sendPenguins(myIceberg, Math.abs(enemyAmount - myAmount + 1));
            }
        }
    }
    

}