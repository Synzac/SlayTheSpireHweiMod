package Hweimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class IncreaseFaliAction extends AbstractGameAction {
    private UUID uuid;
    private boolean isUpgraded;

    public IncreaseFaliAction  (UUID targetUUID, boolean isUpgraded) {
        this.uuid = targetUUID;
        this.isUpgraded = isUpgraded;
    }

    @Override
    public void update() {
        AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.uuid.equals(this.uuid)).forEach(c -> {
            c.misc += 1;
            if (isUpgraded) {
                if (c.misc >= 3) {
                    c.baseMagicNumber += 1;
                    c.misc = 0;
                }
            } else {
                if (c.misc >= 4) {
                    c.baseMagicNumber += 1;
                    c.misc = 0;
                }
            }
            c.initializeDescription();
        });
        GetAllInBattleInstances.get(this.uuid).forEach(c -> {
            c.misc += 1;
            if (isUpgraded) {
                if (c.misc >= 3) {
                    c.baseMagicNumber += 1;
                    c.misc = 0;
                }
            } else {
                if (c.misc >= 4) {
                    c.baseMagicNumber += 1;
                    c.misc = 0;
                }
            }
            c.initializeDescription();
        });
        this.isDone = true;
    }
}
