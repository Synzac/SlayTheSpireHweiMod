package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.InkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JingMing extends MouldCard {
    public static final String ID = ModHelper.makePath(JingMing.class.getSimpleName());
    public JingMing() {
        super(JingMing.class.getSimpleName(), 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        this.block = this.baseBlock = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
        this.tags.add(HweiCardTagsEnum.SHIELD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new InkPower(p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new JingMing();
    }
}
