package Hweimod.cards;

import Hweimod.actions.HuaZhongLingAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.powers.HaoJiePower;
import Hweimod.powers.Subject_DisasterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HaoJie extends MouldCard {
    public static final String ID = ModHelper.makePath(HaoJie.class.getSimpleName());

    public HaoJie(){
        super(HaoJie.class.getSimpleName(), 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Subject_DisasterPower(p)));
        addToBot(new ApplyPowerAction(p, p, new HaoJiePower(p, this.magicNumber)));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new HaoJie();
    }
}
