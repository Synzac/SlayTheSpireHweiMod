package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static java.lang.Math.round;

public class LangLingZhuiLie extends MouldCard {
    public static final String ID = ModHelper.makePath(LangLingZhuiLie.class.getSimpleName());

    public LangLingZhuiLie(){
        super(LangLingZhuiLie.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 1;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        int d = (int) round((1.0*m.maxHealth - 1.0*m.currentHealth) * (6 + 3*this.magicNumber) / 100.0);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage + d)));

        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new LangLingZhuiLie();
    }
}
