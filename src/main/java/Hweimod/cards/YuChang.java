package Hweimod.cards;

import Hweimod.actions.HangAction;
import Hweimod.actions.YuChangAction;
import Hweimod.actions.YuRenAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YuChang extends MouldCard {
    public static final String ID = ModHelper.makePath(YuChang.class.getSimpleName());

    public YuChang(){
        super(YuChang.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.block = this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
        this.tags.add(HweiCardTagsEnum.SHIELD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i)
            if (!p.drawPile.isEmpty())
                MouldCard.xuanzhiquReceiveCard(p.drawPile.getTopCard(), p.drawPile);
        addToBot(new YuChangAction(this.block));

        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new YuChang();
    }
}
