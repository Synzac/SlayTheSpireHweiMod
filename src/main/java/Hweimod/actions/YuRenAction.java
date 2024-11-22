package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class YuRenAction extends AbstractGameAction {

    private int damage;

    private AbstractMonster m;

    public YuRenAction(int damage, AbstractMonster m){
        this.damage = damage;
        this.m = m;
    }

    @Override
    public void update() {
        for (AbstractCard c : MouldCard.XuanZhiQu.group) {
            addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.damage)));
            addToTop(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
        }
        this.isDone = true;
    }
}
