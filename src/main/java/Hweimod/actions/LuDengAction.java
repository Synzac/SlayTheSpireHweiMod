package Hweimod.actions;

import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LuDengAction extends AbstractGameAction {
    private DamageInfo info;

    private AbstractMonster M;

    public LuDengAction(DamageInfo info, AbstractMonster M){
        this.info = info;
        setValues(M, info);
        this.M = M;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            if (AbstractDungeon.player.hasPower(APPower.POWER_ID)) {
                info.output += AbstractDungeon.player.getPower(APPower.POWER_ID).amount;
            }
            boolean LuDeng = false;

            int output = info.output;
            if (output > 0 && M.hasPower("IntangiblePlayer"))
                output = 1;
            int damageAmount = output;

            if (!(M.isDying || M.isEscaping)) {
                if (damageAmount < 0)
                    damageAmount = 0;
                if (damageAmount > M.currentBlock) {
                    damageAmount -= M.currentBlock;
                } else {
                    damageAmount = 0;
                }
                DamageInfo If = new DamageInfo(AbstractDungeon.player, output, HweiDamageTypeEnum.MAGIC);
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    damageAmount = r.onAttackToChangeDamage(If, damageAmount);
                for (AbstractPower p : AbstractDungeon.player.powers)
                    damageAmount = p.onAttackToChangeDamage(If, damageAmount);
                for (AbstractPower p : M.powers)
                    damageAmount = p.onAttackedToChangeDamage(If, damageAmount);
                for (AbstractPower p : M.powers)
                    damageAmount = p.onAttacked(If, damageAmount);


                if (damageAmount > M.currentHealth) {
                    damageAmount -= M.currentHealth;
                    LuDeng = true;
                }
            }

            M.damage(this.info);

            if ((this.target.isDying || this.target.currentHealth <= 0) && LuDeng){
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if(!mo.escaped && !mo.isDead && !mo.halfDead)
                        addToTop(new LuDengAction(new DamageInfo(AbstractDungeon.player, damageAmount, HweiDamageTypeEnum.MAGIC), mo));
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }
}
