package Hweimod.actions;

import Hweimod.cards.JiHunTongJi;
import Hweimod.cards.QingHuiYeNing;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.cards.purple.ThirdEye;
import com.megacrit.cardcrawl.cards.red.Warcry;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Objects;

import static Hweimod.cards.mould.MouldCard.XuanZhiQu;

public class NiuZhuanMingYunAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("HweiMod:HangAction").TEXT;

    private boolean retrieveCard = false;
    private boolean upgraded;
    private AbstractPlayer p;
    public int amount;
    public int num;

    public NiuZhuanMingYunAction(boolean upgraded, int amount, int num) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.num = num;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST){
            if (this.p.hand.group.isEmpty() || amount == 0){
                if(num == 0){
                    this.isDone = true;
                    return;
                }
                ArrayList<AbstractCard> generatedCards = generateCardChoices(num);
                AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], false);
                tickDuration();
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, true, true, false, false);
                tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && this.amount > 0) {
            int n = 0;
            if(!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty())
                n = 1;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(card -> {
                this.p.drawPile.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                this.p.hand.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                this.p.discardPile.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                XuanZhiQu.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
                MouldCard.xuanzhiquReceiveCard(card, this.p.hand);
            });
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            AbstractDungeon.actionManager.addToTop(new NiuZhuanMingYunAction(this.upgraded, this.amount-1, this.num+n));
            this.isDone = true;
        }

        if (this.p.hand.group.isEmpty() || amount == 0) {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (this.upgraded || AbstractDungeon.player.hasPower("MasterRealityPower"))
                        disCard.upgrade();
                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
                this.retrieveCard = true;
            }
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices(int num) {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        while (derp.size() < num) {
            AbstractCard.CardTags cardTag;
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(999);
            if (roll < 242) cardTag = null;
            else if (roll < 484) cardTag = HweiCardTagsEnum.SIGNATURE_DISASTER;
            else if (roll < 726) cardTag = HweiCardTagsEnum.SIGNATURE_SERENITY;
            else if(roll < 968) cardTag = HweiCardTagsEnum.SIGNATURE_TORMENT;
            else cardTag = HweiCardTagsEnum.SIGNATURE_DESPAIR;

            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat();
            if (!Objects.equals(ModHelper.getSig(card), cardTag))
                dupe = true;
            if (derp.stream().anyMatch(c -> Objects.equals(ModHelper.getSig(card), ModHelper.getSig(c)))) {
                dupe = true;
            }
            if (!dupe)
                derp.add(card.makeCopy());
        }
        return derp;
    }
}
