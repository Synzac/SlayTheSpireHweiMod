package Hweimod.actions;

import Hweimod.powers.InkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class FeiRanAction extends AbstractGameAction {

    private int amount;
    public FeiRanAction(int amount){
        this.amount = amount;
    }

    @Override
    public void update() {
        int a = 0;
        if(AbstractDungeon.player.hasPower(InkPower.POWER_ID))
            a += AbstractDungeon.player.getPower(InkPower.POWER_ID).amount;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
            addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, a + this.amount, false)));
        this.isDone = true;
    }
}
