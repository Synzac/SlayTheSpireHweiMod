package Hweimod.actions;

import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class YingYanAction extends ExhaustAction {
    private boolean anyNumber;
    private boolean canPickZero;
    private boolean upgraded;

    public YingYanAction(boolean upgraded) {
        super(false, true, true);
        this.anyNumber = true;
        this.canPickZero = true;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == this.startDuration) {
            if (p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if(!this.upgraded){
                int count = p.hand.size();
                addToTop(new DrawCardAction(count));
                if(count == 3){
                    addToTop(new VFXAction(p, new InflameEffect(p), 1.0F));
                    addToTop(new ApplyPowerAction(p, p, new APPower(p, 3)));
                }
                for (int i = 0; i < count; i++) {
                    if (Settings.FAST_MODE) {
                        addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
                    } else {
                        addToTop(new ExhaustAction(1, true, true));
                    }
                }
                this.isDone = true;
                return;
            }
            numExhausted = this.amount;
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int count = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            addToTop(new DrawCardAction(count));
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                p.hand.moveToExhaustPile(c);
            if(count == 3){
                addToTop(new VFXAction(p, new InflameEffect(p), 1.0F));
                addToTop(new ApplyPowerAction(p, p,
                        new APPower(p, 3)));
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
