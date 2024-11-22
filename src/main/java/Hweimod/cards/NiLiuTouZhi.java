package Hweimod.cards;

import Hweimod.actions.NiLiuTouZhiAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NiLiuTouZhi extends MouldCard {
    public static final String ID = ModHelper.makePath(NiLiuTouZhi.class.getSimpleName());

    public NiLiuTouZhi() {
        super(NiLiuTouZhi.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new NiLiuTouZhiAction(this.magicNumber, m));

        signature(p, m);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeDamage(1);
            upgradeMagicNumber(1);
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new NiLiuTouZhi();
    }
}
