package Hweimod.powers;

import Hweimod.actions.MagicDamageAllEnemiesAction;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static java.lang.Math.round;

public class FenXinPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(FenXinPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(FenXinPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(FenXinPower.class.getSimpleName(), false);

    private boolean invoked = false;

    public FenXinPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(!this.invoked)
            addToBot(new MagicDamageAllEnemiesAction(AbstractDungeon.player,this.amount*5,
                HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
        this.invoked = true;
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, FenXinPower.POWER_ID));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        int d = 0;
        if(AbstractDungeon.player.hasPower(APPower.POWER_ID))
            d = (int) round(AbstractDungeon.player.getPower(APPower.POWER_ID).amount*this.amount*0.2);
        addToBot(new LoseHPAction(this.owner, null, this.amount + d));
    }

    @Override
    public void onDeath(){
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() &&
                this.owner.currentHealth <= 0 && !this.invoked)
            addToBot(new MagicDamageAllEnemiesAction(AbstractDungeon.player,this.amount*5,
                    HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
        this.invoked = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount, this.amount*20, this.amount*5);
    }
}
