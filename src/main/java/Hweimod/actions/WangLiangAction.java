package Hweimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class WangLiangAction extends AbstractGameAction {

    public AbstractMonster m;

    public WangLiangAction(int amount, AbstractMonster m){
        this.amount = amount;
        this.m = m;
    }

    @Override
    public void update() {
        if(m.hasPower(WeakPower.POWER_ID) || m.hasPower(VulnerablePower.POWER_ID))
            addToTop(new DrawCardAction(this.amount));
        this.isDone = true;
    }
}
