package Hweimod.cards;

import Hweimod.actions.HangAction;
import Hweimod.actions.HuiHaoAction;
import Hweimod.actions.OptionalQuXiaAction;
import Hweimod.actions.YuRenAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HuiHao extends MouldCard {
    public static final String ID = ModHelper.makePath(HuiHao.class.getSimpleName());

    public HuiHao(){
        super(HuiHao.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 11;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for (int i = 0; i < this.magicNumber; ++i)
            addToBot(new OptionalQuXiaAction(true,false, false));
        addToBot(new HuiHaoAction(this));
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new HuiHao();
    }
}
