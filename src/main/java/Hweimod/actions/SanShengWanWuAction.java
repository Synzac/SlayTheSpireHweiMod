package Hweimod.actions;

import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Objects;

public class SanShengWanWuAction extends AbstractGameAction{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath(SanShengWanWuAction.class.getSimpleName()));

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public SanShengWanWuAction(){
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == 0.5F) {
            if (this.p.hand.isEmpty()){
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                tickDuration();
                return;
            }
            AbstractCard.CardTags tag = ModHelper.getSig(this.p.hand.getTopCard());
            if(tag != null) for (AbstractCard c : this.p.discardPile.group)
                if (Objects.equals(ModHelper.getSig(c), tag))
                    addToTop(new DiscardToHandAction(c));
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard.CardTags tag = ModHelper.getSig(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
            if(tag != null) for (AbstractCard c : this.p.discardPile.group)
                if (Objects.equals(ModHelper.getSig(c), tag))
                    addToTop(new DiscardToHandAction(c));
            this.p.hand.addToTop(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
