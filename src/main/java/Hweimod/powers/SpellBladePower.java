package Hweimod.powers;

import Hweimod.actions.MagicDamageAction;
import Hweimod.actions.MagicDamageAllEnemiesAction;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SpellBladePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(SpellBladePower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final String NAME = powerStrings.NAME;

    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String PATH128 = ModHelper.makePowerAd(SpellBladePower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(SpellBladePower.class.getSimpleName(), false);

    public SpellBladePower(AbstractCreature owner, int Amount){
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
    public void onUseCard(AbstractCard card, UseCardAction action){
        if(card.type.equals(AbstractCard.CardType.ATTACK)){
            if(card.target == AbstractCard.CardTarget.ALL_ENEMY){
                addToTop(new MagicDamageAllEnemiesAction((AbstractPlayer) this.owner, this.amount, HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
            }
            else if(card.target == AbstractCard.CardTarget.ENEMY){
                addToTop(new MagicDamageAction(action.target, new DamageInfo(this.owner, this.amount , HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.FIRE));
            }
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, SpellBladePower.POWER_ID));
        } else if (!card.type.equals(AbstractCard.CardType.SKILL)) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, SpellBladePower.POWER_ID));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer){
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, SpellBladePower.POWER_ID));
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
