package Hweimod.cards;

import Hweimod.actions.QiaoDuoTianGongAction;
import Hweimod.actions.ZhiSeYouTongAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThirdEyeEffect;

public class QiaoDuoTianGong extends MouldCard {
    public static final String ID = ModHelper.makePath(QiaoDuoTianGong.class.getSimpleName());

    public QiaoDuoTianGong(){
        super(QiaoDuoTianGong.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new QiaoDuoTianGongAction(XuanZhiQu.size()));
        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new QiaoDuoTianGong();
    }
}
