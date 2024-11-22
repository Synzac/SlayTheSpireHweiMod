package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.powers.HanHaiPower;
import Hweimod.powers.HaoJiePower;
import Hweimod.powers.Subject_DisasterPower;
import Hweimod.powers.Subject_SerenityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HanHai extends MouldCard {
    public static final String ID = ModHelper.makePath(HanHai.class.getSimpleName());

    public HanHai(){
        super(HanHai.class.getSimpleName(), 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Subject_SerenityPower(p)));
        addToBot(new ApplyPowerAction(p, p, new HanHaiPower(p, this.magicNumber)));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new HanHai();
    }
}
