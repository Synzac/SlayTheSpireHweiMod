package Hweimod.powers;

import Hweimod.cards.PengPai;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.patches.AbstractPlayerPatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class QuJingZheYuePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(QuJingZheYuePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(QuJingZheYuePower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(QuJingZheYuePower.class.getSimpleName(), false);

    public QuJingZheYuePower(AbstractCreature owner, int Amount){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        if(!AbstractDungeon.player.drawPile.isEmpty())
            MouldCard.xuanzhiquReceiveCard(AbstractDungeon.player.drawPile.getBottomCard(), AbstractDungeon.player.drawPile);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
