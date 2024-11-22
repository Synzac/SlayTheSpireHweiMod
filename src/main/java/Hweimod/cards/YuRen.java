package Hweimod.cards;

import Hweimod.actions.HangAction;
import Hweimod.actions.YuRenAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YuRen extends MouldCard {
    public static final String ID = ModHelper.makePath(YuRen.class.getSimpleName());

    public YuRen(){
        super(YuRen.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 4;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HangAction(this.magicNumber, true, true));
        addToBot(new YuRenAction(this.damage, m));
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new YuRen();
    }
}
