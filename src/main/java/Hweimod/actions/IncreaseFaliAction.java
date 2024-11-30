package Hweimod.actions;

import Hweimod.cards.FaLiLiuXiDai;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

import static java.lang.Math.pow;

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
            c.misc *= 3;
            if (isUpgraded) {
                if (FaLiLiuXiDai.getMisc1(c.misc)>=3) {
                    c.misc *= 2;
                    c.misc /= (int) pow(3, FaLiLiuXiDai.getMisc1(c.misc));
                }
            } else {
                if (FaLiLiuXiDai.getMisc1(c.misc)>=4) {
                    c.misc *= 2;
                    c.misc /= (int) pow(3, FaLiLiuXiDai.getMisc1(c.misc));
                }
            }
            c.initializeDescription();
        });
        GetAllInBattleInstances.get(this.uuid).forEach(c -> {
            c.misc *= 3;
            if (isUpgraded) {
                if (FaLiLiuXiDai.getMisc1(c.misc)>=3) {
                    c.misc *= 2;
                    c.misc /= (int) pow(3, FaLiLiuXiDai.getMisc1(c.misc));
                }
            } else {
                if (FaLiLiuXiDai.getMisc1(c.misc)>=4) {
                    c.misc *= 2;
                    c.misc /= (int) pow(3, FaLiLiuXiDai.getMisc1(c.misc));
                }
            }
            c.initializeDescription();
        });
        this.isDone = true;
    }
}
