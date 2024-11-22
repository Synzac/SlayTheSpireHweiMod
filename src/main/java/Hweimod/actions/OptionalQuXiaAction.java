package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Hweimod.cards.mould.MouldCard.XuanZhiQu;

public class OptionalQuXiaAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("HweiMod:OptionalQuXiaAction").TEXT;
    private boolean forUse;
    private boolean forExhaust;
    private boolean optional;
    private AbstractMonster target;
    private AbstractCard.CardTags tag = null;

    public OptionalQuXiaAction(boolean optional , boolean forUse, boolean forExhaust){
        this(optional, forUse, forExhaust, null, null);
    }

    public OptionalQuXiaAction(boolean optional , boolean forUse, boolean forExhaust, AbstractMonster target){
        this(optional, forUse, forExhaust, target, null);
    }

    public OptionalQuXiaAction(boolean optional , boolean forUse, boolean forExhaust, AbstractCard.CardTags tag){
        this(optional, forUse, forExhaust, null, tag);
    }

    public OptionalQuXiaAction(boolean optional , boolean forUse, boolean forExhaust, AbstractMonster target, AbstractCard.CardTags tag){
        this.optional = optional;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.forUse = forUse;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.forExhaust = forExhaust;
        this.target = target;
        this.tag = tag;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if(XuanZhiQu.isEmpty()) {
                this.isDone = true;
                return;
            }
            if(!this.optional){
                MouldCard.quxia(this.forUse, this.forExhaust, this.target, this.tag);
                this.isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            temp.addToTop(XuanZhiQu.getTopCard());
            AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false, false, true, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            MouldCard.quxia(this.forUse, this.forExhaust, this.target, this.tag);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
