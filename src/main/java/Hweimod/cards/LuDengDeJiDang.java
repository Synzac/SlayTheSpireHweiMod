package Hweimod.cards;

import Hweimod.actions.LuDengAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LuDengDeJiDang extends MouldCard {
    public static final String ID = ModHelper.makePath(LuDengDeJiDang.class.getSimpleName());

    public LuDengDeJiDang(){
        super(LuDengDeJiDang.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 18;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LuDengAction(new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), m));
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new LuDengDeJiDang();
    }
}
