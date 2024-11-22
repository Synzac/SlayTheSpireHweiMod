package Hweimod.cards;

import Hweimod.actions.HuiHaoAction;
import Hweimod.actions.OptionalQuXiaAction;
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

public class ZheZhi extends MouldCard {
    public static final String ID = ModHelper.makePath(ZheZhi.class.getSimpleName());

    public ZheZhi(){
        super(ZheZhi.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 10;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for (int i = 0; i < this.magicNumber; ++i)
            addToBot(new OptionalQuXiaAction(true,false, false));
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
    public AbstractCard makeCopy(){
        return new ZheZhi();
    }
}
