package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiMod;
import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static java.lang.Math.round;

public class MieShiZheDeSiWangZhiMao extends MouldCard {
    public static final String ID = ModHelper.makePath(MieShiZheDeSiWangZhiMao.class.getSimpleName());

    public MieShiZheDeSiWangZhiMao(){
        super(MieShiZheDeSiWangZhiMao.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
        int a = 0;
        if(p.hasPower(APPower.POWER_ID)) {
            a = (int) round(p.getPower(APPower.POWER_ID).amount * 0.3);
        }
        addToBot(new ApplyPowerAction(p, p, new APPower(p, this.magicNumber + a)));

        updateCost(1);
        this.applyPowers();
        AbstractDungeon.player.discardPile.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        AbstractDungeon.player.drawPile.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });
        XuanZhiQu.group.stream().filter(c -> c instanceof MieShiZheDeSiWangZhiMao).forEach(c -> {
            c.updateCost(1);
            c.applyPowers();
        });

        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new MieShiZheDeSiWangZhiMao();
    }
}
