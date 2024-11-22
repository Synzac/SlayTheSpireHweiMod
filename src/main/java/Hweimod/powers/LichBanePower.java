package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LichBanePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(LichBanePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(LichBanePower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(LichBanePower.class.getSimpleName(), false);

    public LichBanePower(AbstractCreature owner, int Amount){
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
    public void onAfterUseCard(AbstractCard card, UseCardAction action){
        if (card.type.equals(AbstractCard.CardType.SKILL)){
            if (!this.owner.hasPower(SpellBladePower.POWER_ID)) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new SpellBladePower(this.owner, this.amount)));
            } else if (this.owner.getPower(SpellBladePower.POWER_ID).amount < this.amount) {
                addToBot(new ApplyPowerAction(this.owner, this.owner,
                        new SpellBladePower(this.owner, this.amount - this.owner.getPower(SpellBladePower.POWER_ID).amount)));
            }
        }
    }
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
