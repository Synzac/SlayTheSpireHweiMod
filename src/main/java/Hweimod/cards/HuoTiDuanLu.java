package Hweimod.cards;

import Hweimod.actions.HuoTiDuanLuAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HuoTiDuanLu extends MouldCard {
    public static final String ID = ModHelper.makePath(HuoTiDuanLu.class.getSimpleName());

    public HuoTiDuanLu() {
        super(HuoTiDuanLu.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 5;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
        this.tags.add(HweiCardTagsEnum.SHIELD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new HuoTiDuanLuAction(this.upgraded));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HuoTiDuanLu();
    }
}
