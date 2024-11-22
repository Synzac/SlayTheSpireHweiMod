package Hweimod.powers;

import Hweimod.actions.MagicDamageAction;
import Hweimod.actions.MagicDamageAllEnemiesAction;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.patches.AbstractPlayerPatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static org.apache.commons.lang3.math.NumberUtils.min;

public class InkPower extends AbstractPower{
    public static final String POWER_ID = ModHelper.makePath(InkPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(InkPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(InkPower.class.getSimpleName(), false);

    private static final Texture[] UI = {new Texture("HweiModResources/img/UI/Ink/Ink0.png"),
                                        new Texture("HweiModResources/img/UI/Ink/Ink1.png"),
                                        new Texture("HweiModResources/img/UI/Ink/Ink2.png"),
                                        new Texture("HweiModResources/img/UI/Ink/Ink3.png"),
                                        new Texture("HweiModResources/img/UI/Ink/Ink4.png")};

    public InkPower(AbstractCreature owner, int Amount){
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
    public void renderAmount(SpriteBatch sb, float x, float y, Color c){
        super.renderAmount(sb, x, y, c);
        int clr = 0;
        if (this.owner.hasPower(Subject_DisasterPower.POWER_ID)) clr = 1;
        else if (this.owner.hasPower(Subject_SerenityPower.POWER_ID)) clr = 2;
        else if (this.owner.hasPower(Subject_TormentPower.POWER_ID)) clr = 3;
        else if (this.owner.hasPower(Subject_DespairPower.POWER_ID)) clr = 4;

        if (this.amount > 0){
            x = this.owner.drawX - 205 * Settings.scale;
            y = this.owner.drawY;
            sb.setColor(Color.WHITE);
            for(int i = 0; i < this.amount; ++i){
                sb.draw(UI[clr], x-(i/6)*(float) UI[clr].getWidth(), y+(i%6)*(float) UI[clr].getHeight(), (float) UI[clr].getWidth(), (float) UI[clr].getHeight());
            }
        }
    }

    @Override
    public void onInitialApplication() {
        int max = AbstractPlayerPatch.maxInkField.maxInks.get(this.owner);

        addToTop(new ApplyPowerAction(owner, owner,
                new APPower(owner, 2*min(max, this.amount)), 2*min(max, this.amount), true, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void stackPower(int stackAmount) {
        int max = AbstractPlayerPatch.maxInkField.maxInks.get(this.owner);

        this.fontScale = 8.0F;
        int d = max - this.amount;
        d = min(d, stackAmount);
        this.amount += d;
        if(d>0)
            addToTop(new ApplyPowerAction(owner, owner,
                new APPower(owner, 2*d), 2*d, true, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (this.amount - reduceAmount <= 0) {
            this.fontScale = 8.0F;
            this.amount = 0;
        } else {
            this.fontScale = 8.0F;
            this.amount -= reduceAmount;
        }
        addToBot(new ReducePowerAction(this.owner, this.owner, APPower.POWER_ID, 2*min(this.amount, reduceAmount)));
    }

    @Override
    public void onRemove() {
        addToBot(new ReducePowerAction(this.owner, this.owner, APPower.POWER_ID, 2*this.amount));
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK){
            if(this.amount > 0) {
                if (card.target == AbstractCard.CardTarget.ALL_ENEMY) {
                    if (this.owner.hasPower(Subject_DisasterPower.POWER_ID)) {
                        addToTop(new MagicDamageAllEnemiesAction(this.owner, 4, HweiDamageTypeEnum.DOUBLE_MAGIC, AbstractGameAction.AttackEffect.FIRE));
                    } else {
                        addToTop(new MagicDamageAllEnemiesAction(this.owner, 2, HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
                    }
                } else if (card.target == AbstractCard.CardTarget.ENEMY) {
                    addToTop(new MagicDamageAction(action.target, new DamageInfo(this.owner, 2, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.FIRE));
                }

                if (this.owner.hasPower(Subject_SerenityPower.POWER_ID)) {
                    addToTop(new DrawCardAction(this.owner, 1));
                }

                if (this.owner.hasPower(Subject_TormentPower.POWER_ID)) {
                    addToTop(new GainEnergyAction(1));
                }
            }
            if (this.amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, InkPower.POWER_ID));
            } else {
                if(!this.owner.hasPower(Subject_DespairPower.POWER_ID))
                    addToBot(new ReducePowerAction(this.owner, this.owner, InkPower.POWER_ID, 1));
            }
        }
    }

    public void updateDescription() {
        int max = AbstractPlayerPatch.maxInkField.maxInks.get(this.owner);

        this.description = String.format(DESCRIPTIONS[0] + max + DESCRIPTIONS[1]);
    }
}
