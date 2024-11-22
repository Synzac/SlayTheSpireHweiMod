package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DuoMu extends MouldCard {
    public static final String ID = ModHelper.makePath(DuoMu.class.getSimpleName());

    public DuoMu(){
        super(DuoMu.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.block = this.baseBlock = 8;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
        this.tags.add(HweiCardTagsEnum.SHIELD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(Color_DisasterPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new Subject_DisasterPower(p)));
            addToBot(new GainBlockAction(p, this.block));
        } else if (p.hasPower(Color_SerenityPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new Subject_SerenityPower(p)));
            addToBot(new GainBlockAction(p, this.block));
        } else if (p.hasPower(Color_TormentPower.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new Subject_TormentPower(p)));
            addToBot(new GainBlockAction(p, this.block));
        } else if (p.hasPower(Color_DespairPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new Subject_DespairPower(p)));
            addToBot(new GainBlockAction(p, this.block));
        }
        else
            addToBot(new GainEnergyAction(this.magicNumber));

        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new DuoMu();
    }
}
