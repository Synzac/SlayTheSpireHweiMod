package Hweimod.actions;

import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DoubleMagicDamageAllEnemiesAction extends AbstractGameAction {

    private int baseDamage;

    public DoubleMagicDamageAllEnemiesAction(AbstractCreature source, int baseDamage, DamageInfo.DamageType type, AttackEffect effect) {
        if(source.hasPower(APPower.POWER_ID)) {
            baseDamage += 2*source.getPower(APPower.POWER_ID).amount;
        }
        this.source = source;
        this.baseDamage = baseDamage;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        addToTop(new DamageAllEnemiesAction((AbstractPlayer)source, baseDamage, damageType, attackEffect));
        this.isDone = true;
    }
}
