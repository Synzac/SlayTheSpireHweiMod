package Hweimod.cards;

import Hweimod.actions.SanShengWanWuAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.powers.Color_DespairPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SanShengWanWu extends MouldCard {
    public static final String ID = ModHelper.makePath(SanShengWanWu.class.getSimpleName());

    public SanShengWanWu(){
        super(SanShengWanWu.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 5;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new SanShengWanWuAction());
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new SanShengWanWu();
    }
}
