package Hweimod.cards.colorless;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class SuiLieDeHuBi extends MouldCard {
    public static final String ID = ModHelper.makePath(SuiLieDeHuBi.class.getSimpleName());

    public SuiLieDeHuBi(){
        super(SuiLieDeHuBi.class.getSimpleName(), -2, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void upgrade() {}

    public AbstractCard makeCopy() {
        return new SuiLieDeHuBi();
    }
}
