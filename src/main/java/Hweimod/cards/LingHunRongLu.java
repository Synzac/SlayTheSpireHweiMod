package Hweimod.cards;

import Hweimod.actions.LingHunRongLuAction;
import Hweimod.actions.OptionalQuXiaAction;
import Hweimod.cards.colorless.HuoQiu;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LingHunRongLu extends MouldCard {
    public static final String ID = ModHelper.makePath(LingHunRongLu.class.getSimpleName());

    public LingHunRongLu(){
        super(LingHunRongLu.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.block = this.baseBlock = 4;
        this.tags.add(HweiCardTagsEnum.SHIELD);
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i)
            addToBot(new OptionalQuXiaAction(true, false, false, HweiCardTagsEnum.SIGNATURE_DISASTER));
        addToBot(new LingHunRongLuAction(this.block));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new LingHunRongLu();
    }
}
