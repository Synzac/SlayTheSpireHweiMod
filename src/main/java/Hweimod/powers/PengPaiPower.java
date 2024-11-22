package Hweimod.powers;

import Hweimod.cards.PengPai;
import Hweimod.helpers.ModHelper;
import Hweimod.patches.AbstractPlayerPatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PengPaiPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(PengPaiPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(PengPaiPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(PengPaiPower.class.getSimpleName(), false);

    public PengPaiPower(AbstractCreature owner, int Amount){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        int max = AbstractPlayerPatch.maxInkField.maxInks.get(this.owner);
        AbstractPlayerPatch.maxInkField.maxInks.set(this.owner, max+1);

        addToBot(new ApplyPowerAction(this.owner, this.owner, new InkPower(this.owner, this.amount)));
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount, this.amount);
    }
}
