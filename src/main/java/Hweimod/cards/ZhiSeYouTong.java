package Hweimod.cards;

import Hweimod.actions.ZhiSeYouTongAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThirdEyeEffect;

public class ZhiSeYouTong extends MouldCard {
    public static final String ID = ModHelper.makePath(ZhiSeYouTong.class.getSimpleName());

    public ZhiSeYouTong(){
        super(ZhiSeYouTong.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p != null)
            addToBot(new VFXAction(new ThirdEyeEffect(p.hb.cX, p.hb.cY)));
        int a = this.magicNumber;
        if (p != null && p.hasPower(APPower.POWER_ID)) {
            a += p.getPower(APPower.POWER_ID).amount;
        }
        addToBot(new ZhiSeYouTongAction(a));

        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new ZhiSeYouTong();
    }
}
