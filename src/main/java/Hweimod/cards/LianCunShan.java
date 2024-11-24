package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.ZhuoShaoPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LianCunShan extends MouldCard {
    public static final String ID = ModHelper.makePath(LianCunShan.class.getSimpleName());

    public LianCunShan(){
        super(LianCunShan.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new ZhuoShaoPower(m, this.magicNumber)));
            signature(p, mo);
        }
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
        return new LianCunShan();
    }
}
