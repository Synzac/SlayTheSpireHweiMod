package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MoonlightPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(MoonlightPower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(MoonlightPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(MoonlightPower.class.getSimpleName(), false);

    public MoonlightPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    private boolean invoked = false;

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.target.equals(AbstractCard.CardTarget.ALL_ENEMY) || (action.target!=null && (action.target.equals(this.owner)))) {
            flash();
            action.returnToHand = true;
            invoked = true;
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (invoked){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, MoonlightPower.POWER_ID));
        }
        invoked = false;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }
}
