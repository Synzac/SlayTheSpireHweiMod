package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class APPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(APPower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String PATH128 = ModHelper.makePowerAd(APPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(APPower.class.getSimpleName(), false);

    public APPower(AbstractCreature owner, int Amount){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type.equals(HweiDamageTypeEnum.MAGIC)) {
            return damage + this.amount;
        }
        else if (type.equals(HweiDamageTypeEnum.DOUBLE_MAGIC)) {
            return damage + 2*this.amount;
        }
        else return damage;
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if(card != null && card.hasTag(HweiCardTagsEnum.SHIELD)){
            blockAmount += this.amount;
            if(this.owner.hasPower(DexterityPower.POWER_ID)){
               blockAmount -= this.owner.getPower(DexterityPower.POWER_ID).amount;
            }
        }
        return blockAmount;
    }
}
