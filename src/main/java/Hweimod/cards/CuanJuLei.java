package Hweimod.cards;

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

public class CuanJuLei extends MouldCard {
    public static final String ID = ModHelper.makePath(CuanJuLei.class.getSimpleName());
    public CuanJuLei() {
        super(CuanJuLei.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 12;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = this.damage;
        if (m.hasPower(Color_SerenityPower.POWER_ID) || m.hasPower(Color_DisasterPower.POWER_ID)) {
            addToBot(new GainEnergyAction(1));
            addToBot(new DamageAction(m, new DamageInfo(p, dmg, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        } else if (m.hasPower(Color_TormentPower.POWER_ID)) {
            addToBot(new GainEnergyAction(1));
            addToBot(new DamageAction(m, new DamageInfo(p, dmg*2, HweiDamageTypeEnum.DOUBLE_MAGIC), AbstractGameAction.AttackEffect.LIGHTNING));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, dmg, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CuanJuLei();
    }
}
