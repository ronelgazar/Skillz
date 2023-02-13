import penguin_game.*;

public class utils {
    public static Iceberg[] ClosestIceberg(Game game, Iceberg lonelyIceberg) {
        Iceberg[] returningArr = new Iceberg[game.getMyIcebergs().length - 1];
        Iceberg myIceberg;
        int[] distanceArr = new int[game.getEnemyIcebergs().length - 1];
        int distance = 0;
        // going through all of my icebergs
        int tempJ = 0;
        for (int i = 0; i < game.getMyIcebergs().length; i++) {
            myIceberg = game.getMyIcebergs()[i];
            if (myIceberg != lonelyIceberg) {
                tempJ++;
                distanceArr[tempJ] = myIceberg.getTurnsTillArrival(lonelyIceberg);
            }
        }

        // sorting the array
        for (int q = 0; q < distanceArr.length; q++) {
            for (int p = 1; p < distanceArr.length; p++) {
                if (distanceArr[q] > distanceArr[p]) {
                    int temp = distanceArr[q];
                    distanceArr[q] = distanceArr[p];
                    distanceArr[p] = temp;
                }
            }
        }

        // compering distance to iceberg
        for (int i = 0; i < game.getMyIcebergs().length; i++) {
            Iceberg myIceberg2 = game.getMyIcebergs()[i];
            if (myIceberg2 != lonelyIceberg) {
                for (int frog = 0; frog < distanceArr.length; frog++) {
                    // checking if myIceberg2 is the same iceberg as game.getMyIcebergs()[frog]
                    if (distanceArr[frog] == myIceberg2.getTurnsTillArrival(lonelyIceberg)) {
                        returningArr[i] = myIceberg2;
                    }
                }
            }
        }
        return returningArr;
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

    public void accStart(Game game) {
        // checking if both me and the enemy have sent penguins
        if (game.getEnemyPenguinGroups().length != 0 &&
                game.getMyPenguinGroups().length != 0) {
            // going through all of the enemy's penguin groups
            for (PenguinGroup ePG : game.getEnemyPenguinGroups()) {
                // going through all of my penguin groups
                for (PenguinGroup mPG : game.getMyPenguinGroups()) {
                    if (ePG.destination == mPG.destination) {
                        // going through all of my icebergs
                        for (Iceberg myIceberg : game.getMyIcebergs()) {
                            if (mPG.destination == myIceberg) {
                                if (ePG.currentSpeed > mPG.currentSpeed) {
                                    mPG.accelerate();
                                }
                            }
                            // else if(){
                            // help our attacking penguin group
                        }

                    }
                }
            }
        }
    }
}