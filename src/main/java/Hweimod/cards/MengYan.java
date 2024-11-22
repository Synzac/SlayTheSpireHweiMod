package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.powers.HanHaiPower;
import Hweimod.powers.MengYanPower;
import Hweimod.powers.Subject_SerenityPower;
import Hweimod.powers.Subject_TormentPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MengYan extends MouldCard {
    public static final String ID = ModHelper.makePath(MengYan.class.getSimpleName());

    public MengYan(){
        super(MengYan.class.getSimpleName(), 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Subject_TormentPower(p)));
        addToBot(new ApplyPowerAction(p, p, new MengYanPower(p, this.magicNumber)));
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
        return new MengYan();
    }
}
