package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.stream.IntStream;

public class QiaoDuoTianGongAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("HweiMod:QiaoDuoTianGongAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;

    public QiaoDuoTianGongAction(int numCards) {
        this.amount = numCards;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startingDuration) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            IntStream.range(0, AbstractDungeon.player.drawPile.size()).filter(i -> AbstractDungeon.player.drawPile.group
                    .get(AbstractDungeon.player.drawPile.size() - i - 1).costForTurn <= this.amount).mapToObj(i -> AbstractDungeon.player.drawPile.group
                    .get(AbstractDungeon.player.drawPile.size() - i - 1)).forEach(tmpGroup::addToTop);

            if(!tmpGroup.isEmpty())
                AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, false, TEXT[0]);
            else {
                this.isDone = true;
                return;
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractDungeon.gridSelectScreen.selectedCards.forEach(c -> {
                MouldCard.xuanzhiquReceiveCard(c, AbstractDungeon.player.drawPile);
                c.freeToPlayOnce = true;
            });
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }
}
