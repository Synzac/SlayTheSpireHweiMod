package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static java.lang.Math.round;

public class HeartsteelPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(HeartsteelPower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String PATH128 = ModHelper.makePowerAd(HeartsteelPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(HeartsteelPower.class.getSimpleName(), false);

    public HeartsteelPower(AbstractCreature owner){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = -1;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner.equals(AbstractDungeon.player)){
            flash();
            addToBot(new DamageAction(this.owner, new DamageInfo(info.owner, (int)round(AbstractDungeon.player.maxHealth*0.1), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.player.increaseMaxHp((int)round(AbstractDungeon.player.maxHealth*0.02), true);
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, HeartsteelPower.POWER_ID));
        }
        return damageAmount;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }
}
