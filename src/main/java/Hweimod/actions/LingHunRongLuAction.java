package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LingHunRongLuAction extends AbstractGameAction {

    private int block;

    public LingHunRongLuAction(int block){
        this.block = block;
    }

    @Override
    public void update() {
        AbstractDungeon.player.hand.group.stream()
                .filter(c -> c.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER))
                .map(c -> new GainBlockAction(AbstractDungeon.player, this.block))
                .forEach(this::addToTop);
        this.isDone = true;
    }
}
