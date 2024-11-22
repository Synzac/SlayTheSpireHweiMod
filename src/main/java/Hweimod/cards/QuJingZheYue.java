package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.powers.PengPaiPower;
import Hweimod.powers.QuJingZheYuePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuJingZheYue extends MouldCard {
    public static final String ID = ModHelper.makePath(QuJingZheYue.class.getSimpleName());

    public QuJingZheYue(){
        super(QuJingZheYue.class.getSimpleName(), 1, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new QuJingZheYuePower(p, this.magicNumber)));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new QuJingZheYue();
    }
}
