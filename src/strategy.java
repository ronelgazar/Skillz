import penguin_game.*;
public class strategy  {
    public static final double penguinAmountPrecent = 1.25;
    
    public void defend(Game game){
        int missingPenguins =0;
        //going through all of the enemy's pengiun groups
        for(PenguinGroup pg: game.getEnemyPenguinGroups()){
            Iceberg pgDestination = pg.destination;
            
            //going through all of our island
            for(Iceberg myIceberg: game.getMyIcebergs()){
                if(pgDestination  == myIceberg){
                    //the amount of penguins our island is going to have when the enemy group arrives
                    int penguinsWhenArriving = myIceberg.penguinAmount + 
                    (myIceberg.penguinsPerTurn * (pg.turnsTillArrival +1));

                    //checking if the enemy penguin group is larger than the amount of penguins on the island they're attacking
                    if(pg.penguinAmount > penguinsWhenArriving){
                        Iceberg closest = Utils.findClosest(game.getMyIcebergs(),myIceberg);

                        //checking how many more penguins they will have once arriving at our island
                        missingPenguins = pg.penguinAmount - penguinsWhenArriving;
                        
                            int turnsDiffrences = (pg.turnsTillArrival - 
                            closest.getTurnsTillArrival(myIceberg));

                            if(turnsDiffrences > 0){
                                missingPenguins += turnsDiffrences*myIceberg.penguinsPerTurn;
                            }

                            //if closesnt[i] has enough penguins to defend myisland alone, he'll do it
                            if(closest.penguinAmount > missingPenguins && !Utils.isUnderAttack(game, closest)){
                                closest.sendPenguins(myIceberg, missingPenguins);
                            }
                            // //if not other islands will help with the help (aviv told me to type that)
                            // else if(!utils.isUnerAttack(game, closest[i])){
                            //     int allMyPs = 0;
                            //     for(Iceberg IcebergPA: game.getMyIcebergs()){
                            //         allMyPs+= IcebergPA.penguinAmount;
                            //     }

                            //     if(allMyPs > missingPenguins){
                            //         missingPenguins -= closest[i].penguinAmount;
                            //         closest[i].sendPenguins(myIceberg, closest[i].penguinAmount);
                            //     }
                            // }
                        
                    }
                }
            }
        }
    }



    public void gradingAttack(Game game) {
        int grading = 0;
        int[] returningArr = new int[game.getEnemyIcebergs().length];

        int tempTheirIsland = 0;
        int theirIsland = 0;
        int ourIsland = 0;

        int maxGrading = -100000000;
        int maxmaxGrading = -100000000;
        // going through all of my islands
        for (int p = 0; p < game.getMyIcebergs().length; p++) {
            Iceberg myIceberg = game.getMyIcebergs()[p];
            // going throught all of the enemy's islands
            for (int q = 0; q < game.getEnemyIcebergs().length; q++) {
                Iceberg destination = game.getEnemyIcebergs()[q];
                int till = myIceberg.getTurnsTillArrival(destination);

                tempTheirIsland = q;
                //the amount of penguins the desenation will have once we 
                // arrive IF they dont upgrade while were on the way
                int penguinsWhenArriving = destination.penguinAmount + till * destination.penguinsPerTurn;

                //checking if our island has more than the amountof penguins their island will have when we arrive
                if (myIceberg.penguinAmount > penguinsWhenArriving) {
                    grading = 50;

                    int pengGoingToDestination = 0;

                    // checking if we can attack after adding the amount of penguins going to our
                    // destination
                    for (int i = 0; i < game.getEnemyPenguinGroups().length; i++) {
                        if (game.getEnemyPenguinGroups()[i].destination == destination) {
                            pengGoingToDestination += game.getEnemyPenguinGroups()[i].penguinAmount;

                            grading -= game.getEnemyPenguinGroups()[i].penguinAmount;
                        }
                    }
                    // if we cant grading = -1000
                    if (pengGoingToDestination + penguinsWhenArriving > myIceberg.penguinAmount) {
                        grading -= 1000000;
                    }

                } else { // if not then FUCK THAT SHIT LMFAOOOOOOOOOO please kill me
                    grading -= 1000000;
                }

                // adding to grading the amount of penguins my island has - by the amount of
                // penguins they have * 10
                grading += (myIceberg.penguinAmount - penguinsWhenArriving) * 10;
                // adding how many more or less islands we have on them * 50
                grading += (game.getMyIcebergs().length - game.getEnemyIcebergs().length) * -50;
                // grading based on the level of the destination
                grading += destination.level * 10;
                // deducting points based on how far the island we're attacking from the
                // attacking island
                grading -= 50 - myIceberg.getTurnsTillArrival(destination);
                //adding points if destenation is their capital
                if(destination == game.getEnemyIcepitalIcebergs()[0]){
                    grading += 50;   
                }
                returningArr[q] = grading;
                grading = 0;
            }

            for (int i = 0; i < returningArr.length; i++) {
                if (maxGrading > maxmaxGrading) {
                    theirIsland = tempTheirIsland;
                    ourIsland = p;
        
                    maxmaxGrading = maxGrading;
                }
            }
        }

        // calculating how many penguins we need to send, assuming they are'nt going to
        // send more after we've sent this attack

        Iceberg destination = game.getEnemyIcebergs()[theirIsland];
        Iceberg myIceberg = game.getMyIcebergs()[ourIsland];

        int amountSending = destination.penguinAmount;

        for (int i = 0; i < game.getEnemyPenguinGroups().length; i++) {
            if (game.getEnemyPenguinGroups()[i].destination == destination) {
                amountSending += game.getEnemyPenguinGroups()[i].penguinAmount;
            }
        }


        if(maxmaxGrading > -1000){
            myIceberg.sendPenguins(destination, amountSending);
        }
        else{
            return;
        }
    }



    public void attackNeutral(Game game) {
        int grading = 0;
        int[] returningArr = new int[game.getNeutralIcebergs().length];

        int tempTheirIsland = 0;
        int theirIsland = 0;
        int ourIsland = 0;

        int maxmaxGrading = -10000000;
        // going through all of my islands
        for (int p = 0; p < game.getMyIcebergs().length; p++) {
            Iceberg myIceberg = game.getMyIcebergs()[p];
            // going throught all of the enemy's islands
            for (int q = 0; q < game.getNeutralIcebergs().length; q++) {
                Iceberg destination = game.getNeutralIcebergs()[q];
                tempTheirIsland = q;

                // checking if the enemy has penguins pointed onto that island
                for (int j = 0; j < game.getEnemyPenguinGroups().length; j++) {
                    if (game.getEnemyPenguinGroups()[j].destination == destination) {
                        if (game.getEnemyPenguinGroups()[j].penguinAmount <= destination.penguinAmount) {
                            grading += game.getEnemyPenguinGroups()[j].penguinAmount;
                        } else {
                            grading -= game.getEnemyPenguinGroups()[j].penguinAmount;
                        }
                    }
                }
//deducting if I'm already sending penguins to that island
                for (int i = 0; i < game.getMyPenguinGroups().length; i++) {
                    if(game.getMyPenguinGroups()[i]. destination == destination){
                        grading -=game.getMyPenguinGroups()[i].penguinAmount;
                    }
                }

                // deducting based on how many penguins we need to get the island
                grading -= game.getNeutralIcebergs()[q].penguinAmount;
                System.out.println("grading = "+ grading);
                returningArr[q] = grading;
                grading = 0;
            }


        int maxGrading = returningArr[0];
            for (int i = 1; i < returningArr.length; i++) {
                if (maxGrading < returningArr[i]) {
                    maxGrading = returningArr[i];

                    if (maxGrading > maxmaxGrading) {
                        theirIsland = tempTheirIsland;
                        ourIsland = p;

                        maxmaxGrading = maxGrading;
                    }
                }
            }
        }

        // calculating how many penguins we need to send, assuming they are'nt going to
        // send more after we've sent this attack

        Iceberg destination = game.getNeutralIcebergs()[theirIsland];
        Iceberg myIceberg = game.getMyIcebergs()[ourIsland];

        int amountSending = destination.penguinAmount + 1;

        for (int i = 0; i < game.getEnemyPenguinGroups().length; i++) {
            if (game.getEnemyPenguinGroups()[i].destination == destination) {
                amountSending += game.getEnemyPenguinGroups()[i].penguinAmount;
            }
        }
        System.out.println("maxmaxgrading = " +maxmaxGrading);
        myIceberg.sendPenguins(destination, amountSending);
    }

    public void neutral(Game game){
        double capitalUpgradeFactor = 1.3;
        double icebergsUpgradeFact = 1.25;

        Iceberg myIceberg = game.getMyIcepitalIcebergs()[0];

        if(myIceberg.penguinAmount > myIceberg.upgradeCost * capitalUpgradeFactor){
            myIceberg.upgrade();
        }
        //started from 1 because capital is game.getMYIcebergs()[0]
        for(int i = 1; i <game.getMyIcebergs().length; i++){
            myIceberg = game.getMyIcebergs()[i];
            if(myIceberg.penguinAmount > myIceberg.upgradeCost * icebergsUpgradeFact){
                myIceberg.upgrade();
            }    
        }
    }


    public static void counterAttack(Iceberg myIceberg, Iceberg source, int enemyAmount, Iceberg[] icebergs) {
        int penguinAmount = myIceberg.penguinAmount;
        if (enemyAmount < myIceberg.penguinAmount) {
            Iceberg neighbor = Utils.findClosest(icebergs, myIceberg);
            if (myIceberg.canSendPenguins(source, myIceberg.penguinAmount - 1)) {
                myIceberg.sendPenguins(source, myIceberg.penguinAmount - 1);
            }
            if (neighbor.canSendPenguins(myIceberg, enemyAmount - penguinAmount)) {
                neighbor.sendPenguins(myIceberg, enemyAmount - penguinAmount);
            }
        } else {
            Iceberg neighbor = Utils.findClosest(icebergs, myIceberg);
            int myAmount = source.getTurnsTillArrival(myIceberg) * myIceberg.penguinsPerTurn + myIceberg.penguinAmount;
            if (neighbor.canSendPenguins(myIceberg, enemyAmount - myAmount + 1)) {
                neighbor.sendPenguins(myIceberg, enemyAmount - myAmount + 1);
            }
        }
        
    }
    

}
