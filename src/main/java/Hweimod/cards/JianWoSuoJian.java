package Hweimod.cards;

import Hweimod.actions.JianWoSuoJianAction;
import Hweimod.actions.TiaoHeMingYunAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JianWoSuoJian extends MouldCard {
    public static final String ID = ModHelper.makePath(JianWoSuoJian.class.getSimpleName());

    public JianWoSuoJian(){
        super(JianWoSuoJian.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 7;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(HweiCardTagsEnum.SHIELD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DrawCardAction(p, 1));
        addToBot(new JianWoSuoJianAction(p, p, this.magicNumber));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new JianWoSuoJian();
    }
}
