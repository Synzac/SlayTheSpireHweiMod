package Hweimod.actions;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modifier.SignatureModifier;
import Hweimod.powers.Subject_DisasterPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class JianWoSuoJianAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private final AbstractPlayer p;

    private int energy;

    public JianWoSuoJianAction(AbstractCreature target, AbstractCreature source, int energy) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, source, 1);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.energy = energy;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (this.p.hand.isEmpty()){
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                tickDuration();
                return;
            }
            AbstractCard card = this.p.hand.getTopCard();
            if(AbstractDungeon.player.hasPower(Subject_DisasterPower.POWER_ID) ||
                    card.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)){
                addToTop(new ApplyPowerAction(p, p, new EnergizedPower(p, this.energy)));
            }
            this.p.hand.moveToDeck(card, false);
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if(AbstractDungeon.player.hasPower(Subject_DisasterPower.POWER_ID) ||
                        c.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)){
                    addToTop(new ApplyPowerAction(p, p, new EnergizedPower(p, this.energy)));
                }
                this.p.hand.moveToDeck(c, false);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
