package Hweimod.cards;

import Hweimod.actions.IncreaseFaliAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FaLiLiuXiDai extends MouldCard {
    public static final String ID = ModHelper.makePath(FaLiLiuXiDai.class.getSimpleName());

    public FaLiLiuXiDai(){
        super(FaLiLiuXiDai.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.misc = 0;
        this.magicNumber = this.baseMagicNumber = 0;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new IncreaseFaliAction(this.uuid, this.upgraded));

        signature(p, p);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if(this.misc>=3){
                this.baseMagicNumber += 1;
                this.misc = 0;
            }
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new FaLiLiuXiDai();
    }
}
