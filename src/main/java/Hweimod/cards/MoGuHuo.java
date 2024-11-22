package Hweimod.cards;

import Hweimod.actions.YingYanAction;
import Hweimod.cards.colorless.HuoQiu;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MoGuHuo extends MouldCard {
    public static final String ID = ModHelper.makePath(MoGuHuo.class.getSimpleName());

    public MoGuHuo(){
        super(MoGuHuo.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
        this.cardsToPreview = new HuoQiu();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAction(1, false));
        addToBot(new MakeTempCardInHandAction(new HuoQiu(), this.magicNumber));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new MoGuHuo();
    }
}
