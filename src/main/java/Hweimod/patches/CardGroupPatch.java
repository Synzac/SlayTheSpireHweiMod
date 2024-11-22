package Hweimod.patches;

import Hweimod.cards.JiHunTongJi;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Hweimod.cards.mould.MouldCard.XuanZhiQu;

public class CardGroupPatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile", paramtypez = {AbstractCard.class})
    public static class moveToExhaustPilePatch{
        @SpirePrefixPatch
        public static void PrefixPatch(CardGroup CG, AbstractCard card){
            TePanJiHunTongJi(CG);
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck", paramtypez = {AbstractCard.class, boolean.class})
    public static class moveToDeckPatch{
        @SpirePrefixPatch
        public static void PrefixPatch(CardGroup CG, AbstractCard card, boolean randomSpot){
            TePanJiHunTongJi(CG);
        }
    }

    public static void TePanJiHunTongJi(CardGroup CG) {
        if(!(AbstractDungeon.actionManager.currentAction instanceof UseCardAction)
                && CG.type.equals(CardGroup.CardGroupType.HAND)){
            AbstractPlayer p = AbstractDungeon.player;
            p.drawPile.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
            p.hand.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
            p.discardPile.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
            XuanZhiQu.group.stream().filter(c -> c.cardID.equals(JiHunTongJi.ID)).forEach(AbstractCard::didDiscard);
        }
    }
}
