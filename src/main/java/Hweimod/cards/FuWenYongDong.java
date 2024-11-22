package Hweimod.cards;

import Hweimod.actions.OptionalQuXiaAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.powers.Color_DisasterPower;
import Hweimod.powers.Color_SerenityPower;
import Hweimod.powers.Color_TormentPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FuWenYongDong extends MouldCard {
    public static final String ID = ModHelper.makePath(FuWenYongDong.class.getSimpleName());
    public FuWenYongDong() {
        super(FuWenYongDong.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 11;
        this.magicNumber = this.baseMagicNumber = 2;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for (int i = 0; i < this.magicNumber; ++i)
            if (!p.drawPile.isEmpty())
                MouldCard.xuanzhiquReceiveCard(p.drawPile.getTopCard(), p.drawPile);
        addToBot(new OptionalQuXiaAction(false, true, false, m));

        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FuWenYongDong();
    }
}
