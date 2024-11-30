package Hweimod.cards;

import Hweimod.actions.IncreaseFaliAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static java.lang.Math.pow;

public class FaLiLiuXiDai extends MouldCard {
    public static final String ID = ModHelper.makePath(FaLiLiuXiDai.class.getSimpleName());

    public FaLiLiuXiDai(){
        super(FaLiLiuXiDai.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.misc = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(getMisc2(this.misc)));
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
            if(getMisc1(this.misc)>=3){
                this.misc /= (int) pow(3, FaLiLiuXiDai.getMisc1(this.misc));
                this.misc *= 2;
            }
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new FaLiLiuXiDai();
    }

    public static int getMisc1(int misc){
        int misc1 = 0, m = misc;
        while (m%3 == 0 && m>0){
            ++misc1;
            m/=3;
        }
        return misc1;
    }

    public static int getMisc2(int misc){
        int misc2 = 0, m = misc;
        m /= (int) pow(3, getMisc1(m));
        while (m > 1){
            ++misc2;
            m = m/2;
        }
        return misc2;
    }
}
