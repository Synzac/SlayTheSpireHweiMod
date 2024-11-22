package Hweimod.cards;

import Hweimod.actions.ZhenFenKuiJiaAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ZhenFenKuiJia extends MouldCard {
    public static final String ID = ModHelper.makePath(ZhenFenKuiJia.class.getSimpleName());

    public ZhenFenKuiJia(){
        super(ZhenFenKuiJia.class.getSimpleName(), -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.block = this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(HweiCardTagsEnum.SHIELD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ZhenFenKuiJiaAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new ZhenFenKuiJia();
    }
}
