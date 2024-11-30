package Hweimod.powers;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DianJingPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(DianJingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(DianJingPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(DianJingPower.class.getSimpleName(), false);

    public DianJingPower(AbstractCreature owner, int Amount){
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!(ModHelper.getSig(card) == null) &&
                ((ModHelper.getSig(card) == HweiCardTagsEnum.SIGNATURE_DISASTER && AbstractDungeon.player.hasPower(Subject_DisasterPower.POWER_ID))
                 || (ModHelper.getSig(card) == HweiCardTagsEnum.SIGNATURE_SERENITY && AbstractDungeon.player.hasPower(Subject_SerenityPower.POWER_ID))
                 || (ModHelper.getSig(card) == HweiCardTagsEnum.SIGNATURE_TORMENT && AbstractDungeon.player.hasPower(Subject_TormentPower.POWER_ID))
                 || (ModHelper.getSig(card) == HweiCardTagsEnum.SIGNATURE_DESPAIR && AbstractDungeon.player.hasPower(Subject_DespairPower.POWER_ID)))) {
            if (!card.purgeOnUse && this.amount > 0) {
                flash();
                AbstractMonster m = null;
                if (action.target != null)
                    m = (AbstractMonster) action.target;
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (m != null)
                    tmp.calculateCardDamage(m);
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
                this.amount--;
                if (this.amount == 0)
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DianJingPower.POWER_ID));
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = String.format(DESCRIPTIONS[1], this.amount);
        }
    }
}
