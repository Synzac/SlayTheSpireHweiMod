package Hweimod.actions;

import Hweimod.cards.JiHunTongJi;
import Hweimod.cards.mould.MouldCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Hweimod.cards.mould.MouldCard.XuanZhiQu;

public class HangAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("HweiMod:HangAction").TEXT;

    private AbstractPlayer p;

    private int amount;
    private boolean anyNumber;
    private boolean canPickZero;

    public HangAction(int amount,  boolean anyNumber, boolean canPickZero){
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST){
            if (this.p.hand.group.isEmpty() || amount == 0){
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, this.anyNumber, this.canPickZero, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {

                this.p.drawPile.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                this.p.hand.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                this.p.discardPile.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                XuanZhiQu.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);

                MouldCard.xuanzhiquReceiveCard(card, this.p.hand);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            AbstractDungeon.actionManager.addToTop(new HangAction(this.amount - 1, this.anyNumber, this.canPickZero));
            this.isDone = true;
        }
        tickDuration();
    }
}
