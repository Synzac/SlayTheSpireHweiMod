package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class YuChangAction extends AbstractGameAction {

    private int block;

    public YuChangAction(int block){
        this.block = block;
    }

    @Override
    public void update() {
        MouldCard.XuanZhiQu.group.stream()
                .map(c -> new GainBlockAction(AbstractDungeon.player, this.block))
                .forEach(this::addToTop);
        this.isDone = true;
    }
}
