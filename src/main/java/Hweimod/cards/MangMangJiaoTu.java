package Hweimod.cards;

import Hweimod.actions.MangMangJiaoTuAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.InkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class MangMangJiaoTu extends MouldCard {
    public static final String ID = ModHelper.makePath(MangMangJiaoTu.class.getSimpleName());

    public MangMangJiaoTu(){
        super(MangMangJiaoTu.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MangMangJiaoTuAction());
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new MangMangJiaoTu();
    }
}
