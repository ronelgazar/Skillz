package bots;
import penguin_game.*;

public class MyBot implements SkillzBot {
    @Override
    public void doTurn(Game game) {
        int myIcebergsCount = game.getMyIcebergs().length;
        for (Iceberg iceberg : game.getMyIcebergs()) {
            if (iceberg.canUpgrade() && iceberg.upgradeCost + 10 < iceberg.penguinAmount) {
                iceberg.upgrade();
            } else {
                if (myIcebergsCount < 3) {
                    for (Iceberg neutralIceberg : game.getNeutralIcebergs()) {
                        if (!Utils.alreadySent(game, neutralIceberg)) {
                            if (iceberg.canSendPenguins(neutralIceberg, neutralIceberg.penguinAmount + 1)) {
                                iceberg.sendPenguins(neutralIceberg, neutralIceberg.penguinAmount + 1);
                            }
                        }
                    }
                } else {
                    for (Iceberg enemyIceberg : game.getEnemyIcebergs()) {
                        Utils.sendEnemy(game,iceberg, enemyIceberg);
                    }
                    for (PenguinGroup enemyGroup : game.getEnemyPenguinGroups()) {
                        for (Iceberg myIceberg : game.getMyIcebergs()){
                        if (enemyGroup.destination == myIceberg) {
                            strategy.counterAttack(enemyGroup.destination, enemyGroup.source, enemyGroup.penguinAmount, game.getMyIcebergs());
                            
                        }
                        
                    }
                    }
                }
                
            }
        }
    }
}
