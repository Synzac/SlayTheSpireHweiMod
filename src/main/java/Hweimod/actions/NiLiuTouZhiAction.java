package Hweimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class NiLiuTouZhiAction extends AbstractGameAction {
    private AbstractMonster m;

    public NiLiuTouZhiAction(int vulnerableAmt, AbstractMonster m) {
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.amount = vulnerableAmt;
        this.m = m;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0)
            addToTop(new ApplyPowerAction(this.m, AbstractDungeon.player, new VulnerablePower(this.m, this.amount, false), this.amount));
        this.isDone = true;
    }
}
