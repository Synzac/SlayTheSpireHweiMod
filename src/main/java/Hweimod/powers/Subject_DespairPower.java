package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Subject_DespairPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(Subject_DespairPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(Subject_DespairPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(Subject_DespairPower.class.getSimpleName(), false);

    public Subject_DespairPower(AbstractCreature owner) {
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
    public void onInitialApplication() {
        if(this.owner.hasPower(Subject_DisasterPower.POWER_ID)){
            this.owner.getPower(Subject_DisasterPower.POWER_ID).onSpecificTrigger();
        }
        if(this.owner.hasPower(Subject_SerenityPower.POWER_ID)){
            this.owner.getPower(Subject_SerenityPower.POWER_ID).onSpecificTrigger();
        }
        if(this.owner.hasPower(Subject_TormentPower.POWER_ID)){
            this.owner.getPower(Subject_TormentPower.POWER_ID).onSpecificTrigger();
        }
        if(this.owner.hasPower(QianBianZhePower1.POWER_ID)){
            this.owner.getPower(QianBianZhePower1.POWER_ID).onSpecificTrigger();
        }
        if(this.owner.hasPower(QianBianZhePower2.POWER_ID)){
            this.owner.getPower(QianBianZhePower2.POWER_ID).onSpecificTrigger();
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(!card.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER) && !card.hasTag(HweiCardTagsEnum.SIGNATURE_SERENITY)
                && !card.hasTag(HweiCardTagsEnum.SIGNATURE_TORMENT) && !card.hasTag(HweiCardTagsEnum.SIGNATURE_DESPAIR)) {
            if (card.target == AbstractCard.CardTarget.ALL_ENEMY) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!mo.hasPower(Color_SerenityPower.POWER_ID) && !mo.hasPower(Color_DisasterPower.POWER_ID)
                            && !mo.hasPower(Color_TormentPower.POWER_ID) && !mo.hasPower(Color_DespairPower.POWER_ID)) {

                        addToBot(new ApplyPowerAction(mo, this.owner, new Color_DespairPower(mo, 1), 1, true, AbstractGameAction.AttackEffect.NONE));

                    }
                }
            } else if (card.target == AbstractCard.CardTarget.ENEMY) {
                if (!(action.target == null) &&
                        !action.target.hasPower(Color_SerenityPower.POWER_ID) && !action.target.hasPower(Color_DisasterPower.POWER_ID)
                        && !action.target.hasPower(Color_TormentPower.POWER_ID) && !action.target.hasPower(Color_DespairPower.POWER_ID)) {

                    addToBot(new ApplyPowerAction(action.target, this.owner, new Color_DespairPower(action.target, 1), 1, true, AbstractGameAction.AttackEffect.NONE));

                }
            } else if (card.target == AbstractCard.CardTarget.SELF || card.target == AbstractCard.CardTarget.NONE) {
                if (!this.owner.hasPower(Color_SerenityPower.POWER_ID) && !this.owner.hasPower(Color_DisasterPower.POWER_ID)
                        && !this.owner.hasPower(Color_TormentPower.POWER_ID) && !this.owner.hasPower(Color_DespairPower.POWER_ID)) {

                    addToBot(new ApplyPowerAction(this.owner, this.owner, new Color_DespairPower(this.owner, 1), 1, true, AbstractGameAction.AttackEffect.NONE));

                }
            }
        }
    }

    public void onSpecificTrigger() {
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Subject_DespairPower.POWER_ID));
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }
}
