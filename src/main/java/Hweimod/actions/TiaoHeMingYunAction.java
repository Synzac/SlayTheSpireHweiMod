package Hweimod.actions;

import Hweimod.helpers.ModHelper;
import Hweimod.modifier.SignatureModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TiaoHeMingYunAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("HweiMod:TiaoHeMingYunAction").TEXT;

    private AbstractPlayer p;

    private boolean upgraded;

    public TiaoHeMingYunAction(boolean TiaoHeMingYunPlus){
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = TiaoHeMingYunPlus;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST){
            if (this.p.hand.group.size() > 2) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 2) {
                AbstractCard.CardTags t = ModHelper.getSig(this.p.hand.group.get(0));
                CardModifierManager.addModifier(this.p.hand.group.get(0), new SignatureModifier(ModHelper.getSig(this.p.hand.group.get(1))));
                CardModifierManager.addModifier(this.p.hand.group.get(1), new SignatureModifier(t));
                if(this.upgraded){
                    this.p.hand.group.get(0).retain = true;
                    this.p.hand.group.get(1).retain = true;
                }
                this.p.hand.group.get(0).superFlash();
                this.p.hand.group.get(0).initializeDescription();
                this.p.hand.group.get(0).applyPowers();
                this.p.hand.group.get(1).superFlash();
                this.p.hand.group.get(1).initializeDescription();
                this.p.hand.group.get(1).applyPowers();
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() == 1 && this.upgraded){
                this.p.hand.getTopCard().retain = true;
                this.p.hand.refreshHandLayout();
            }
            this.isDone = true;
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard.CardTags t = ModHelper.getSig(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
            CardModifierManager.addModifier(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0),
                    new SignatureModifier(ModHelper.getSig(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1))));
            CardModifierManager.addModifier(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1), new SignatureModifier(t));
            if(this.upgraded){
                AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0).retain = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1).retain = true;
            }
            AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0).superFlash();
            AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0).initializeDescription();
            AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0).applyPowers();
            AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1).superFlash();
            AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1).initializeDescription();
            AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1).applyPowers();
            this.p.hand.addToTop(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
            this.p.hand.addToTop(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(1));
            this.p.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }
}
