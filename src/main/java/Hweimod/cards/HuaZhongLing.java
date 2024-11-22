package Hweimod.cards;

import Hweimod.actions.HuaZhongLingAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.AnYanHuoJuPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HuaZhongLing extends MouldCard {
    public static final String ID = ModHelper.makePath(HuaZhongLing.class.getSimpleName());

    public HuaZhongLing(){
        super(HuaZhongLing.class.getSimpleName(), 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HuaZhongLingAction());
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new HuaZhongLing();
    }
}
