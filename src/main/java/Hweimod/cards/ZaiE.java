package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ZaiE extends MouldCard {
    public static final String ID = ModHelper.makePath(ZaiE.class.getSimpleName());
    public ZaiE() {
        super(ZaiE.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.damage = this.baseDamage = 9;
        this.magicNumber = this.baseMagicNumber = 1;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
        return new ZaiE();
    }
}
