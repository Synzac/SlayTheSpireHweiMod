package Hweimod.cards;

import Hweimod.actions.MengLongAction;
import Hweimod.actions.SuLianFengShuangAction;
import Hweimod.cards.colorless.Ying;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MengLongGuangBiaoZuo extends MouldCard {
    public static final String ID = ModHelper.makePath(MengLongGuangBiaoZuo.class.getSimpleName());

    public MengLongGuangBiaoZuo(){
        super(MengLongGuangBiaoZuo.class.getSimpleName(), 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber, new MengLongAction()));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new MengLongGuangBiaoZuo();
    }
}
