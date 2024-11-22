package Hweimod.actions;

import Hweimod.cards.colorless.Ying;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SuLianFengShuangAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("HweiMod:SuLianFengShuangAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private boolean anyNumber;

    private boolean canPickZero;

    public static int numExhausted;

    public SuLianFengShuangAction() {
        this.anyNumber = false;
        this.p = AbstractDungeon.player;
        this.canPickZero = false;
        this.amount = 1;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                int tmp = this.p.hand.size();
                for (int i = 0; i < tmp; i++) {
                    AbstractCard c = this.p.hand.getTopCard();
                    int D = c.baseDamage, B = c.baseBlock;
                    this.p.hand.moveToExhaustPile(c);
                    addToTop(new MakeTempCardInHandAction(new Ying(D, B), 1));
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }
            numExhausted = this.amount;
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                int D = c.baseDamage, B = c.baseBlock;
                this.p.hand.moveToExhaustPile(c);
                addToTop(new MakeTempCardInHandAction(new Ying(D, B), 1));
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
