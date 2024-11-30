package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.InkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
        AbstractDungeon.player.hand.group.stream().filter(c -> c.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)).forEach(c -> {
            addToTop(new GainBlockAction(AbstractDungeon.player, this.block));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new InkPower(AbstractDungeon.player, 1)));
        });
        this.isDone = true;
    }
}
