package Hweimod.cards;

import Hweimod.actions.HangAction;
import Hweimod.actions.NiuZhuanMingYunAction;
import Hweimod.actions.YuRenAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NiuZhuanMingYun extends MouldCard {
    public static final String ID = ModHelper.makePath(NiuZhuanMingYun.class.getSimpleName());

    public NiuZhuanMingYun(){
        super(NiuZhuanMingYun.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NiuZhuanMingYunAction(this.upgraded, 3, 0));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new NiuZhuanMingYun();
    }
}
