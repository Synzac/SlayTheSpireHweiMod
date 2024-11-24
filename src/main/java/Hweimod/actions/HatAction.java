package Hweimod.actions;

import Hweimod.cards.MieShiZheDeSiWangZhiMao;
import Hweimod.cards.mould.MouldCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HatAction extends AbstractGameAction {

    public AbstractCard card;
    public HatAction(AbstractCard card){
        this.card = card;
    }

    @Override
    public void update() {
        card.updateCost(1);
        AbstractDungeon.player.discardPile.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        AbstractDungeon.player.drawPile.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        MouldCard.XuanZhiQu.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        this.isDone = true;
    }
}
