package Hweimod.cards;

import Hweimod.actions.QingXieAction;
import Hweimod.actions.ZhenFenKuiJiaAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QingXie extends MouldCard {
    public static final String ID = ModHelper.makePath(QingXie.class.getSimpleName());

    public QingXie(){
        super(QingXie.class.getSimpleName(), -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new QingXieAction(p, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
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
    public AbstractCard makeCopy(){
        return new QingXie();
    }
}
