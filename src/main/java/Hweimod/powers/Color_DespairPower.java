package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Color_DespairPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(Color_DespairPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String PATH128 = ModHelper.makePowerAd(Color_DespairPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(Color_DespairPower.class.getSimpleName(), false);

    public Color_DespairPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void onSpecificTrigger() {
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Color_DespairPower.POWER_ID));
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }
}
