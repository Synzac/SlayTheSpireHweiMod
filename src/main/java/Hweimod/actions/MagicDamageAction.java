package Hweimod.actions;

import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MagicDamageAction extends AbstractGameAction {
    private DamageInfo info;

    private boolean skipWait = false, muteSfx = false;

    public MagicDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        if(info.owner.hasPower(APPower.POWER_ID)) {
            info.output += info.owner.getPower(APPower.POWER_ID).amount;
        }
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1F;
    }

    public MagicDamageAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AbstractGameAction.AttackEffect.NONE);
    }

    public MagicDamageAction(AbstractCreature target, DamageInfo info, boolean superFast) {
        this(target, info, AbstractGameAction.AttackEffect.NONE);
        this.skipWait = superFast;
    }

    public MagicDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, boolean superFast) {
        this(target, info, effect);
        this.skipWait = superFast;
    }

    public MagicDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, boolean superFast, boolean muteSfx) {
        this(target, info, effect, superFast);
        this.muteSfx = muteSfx;
    }

    public void update() {
        addToTop(new DamageAction(target, info, attackEffect, skipWait, muteSfx));
        this.isDone = true;
    }

}
