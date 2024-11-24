package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.QianBianZhePower1;
import Hweimod.powers.QianBianZhePower2;
import Hweimod.powers.XuanNongPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QianBianZheJiaXiu extends MouldCard {
    public static final String ID = ModHelper.makePath(QianBianZheJiaXiu.class.getSimpleName());

    public QianBianZheJiaXiu(){
        super(QianBianZheJiaXiu.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new QianBianZhePower1(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new QianBianZhePower2(p, this.upgraded ? 2 : 1)));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new QianBianZheJiaXiu();
    }
}
