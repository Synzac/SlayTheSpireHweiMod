package Hweimod.cards;

import Hweimod.actions.OptionalQuXiaAction;
import Hweimod.cards.colorless.HuoQiu;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

public class YeHuo extends MouldCard {
    public static final String ID = ModHelper.makePath(YeHuo.class.getSimpleName());
    public YeHuo() {
        super(YeHuo.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
        this.exhaust = true;
        this.cardsToPreview = new HuoQiu();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int num = XuanZhiQu.size();
        AbstractCard card = new HuoQiu();
        if(this.upgraded)
            card.upgrade();
        IntStream.range(0, num).forEach(i -> {
            addToBot(new OptionalQuXiaAction(false, false, true));
            addToBot(new MakeTempCardInHandAction(card, 1));
        });
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new YeHuo();
    }
}
