package Hweimod.cards;

import Hweimod.actions.JianWoSuoJianAction;
import Hweimod.cards.colorless.Chong;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShaXingHuanXiang extends MouldCard {
    public static final String ID = ModHelper.makePath(ShaXingHuanXiang.class.getSimpleName());

    public ShaXingHuanXiang(){
        super(ShaXingHuanXiang.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.block = this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(HweiCardTagsEnum.SHIELD);
        this.tags.add(HweiCardTagsEnum.SIGNATURE_TORMENT);
        this.cardsToPreview = new Chong();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInDrawPileAction(new Chong(), this.magicNumber, true, true));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new ShaXingHuanXiang();
    }
}
