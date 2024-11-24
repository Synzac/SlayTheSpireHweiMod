package Hweimod.patches;

import Hweimod.cards.mould.MouldCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

import java.util.ArrayList;

import static Hweimod.cards.mould.MouldCard.XuanZhiQu;

public class AbstractPlayerPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class damageInsertPatch{
        @SpireInsertPatch(locator = Locator.class)
        public static void XuanZhiPatch(AbstractPlayer AP){
            for (AbstractCard c : XuanZhiQu.group)
                c.tookDamage();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
    public static class preBattlePrepPatch{
        @SpirePrefixPatch
        public static void PrefixPatch(AbstractPlayer AP){
            MouldCard.XuanZhiQu.clear();
            AbstractPlayerPatch.maxInkField.maxInks.set(AP, 4);
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "updateCardsOnDiscard")
    public static class updateCardsOnDiscardPatch{
        @SpirePrefixPatch
        public static void PrefixPatch(AbstractPlayer AP){
            for (AbstractCard c : XuanZhiQu.group)
                c.didDiscard();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
    public static class applyStartOfTurnCardsPatch{
        @SpirePostfixPatch
        public static void PostfixPatch(AbstractPlayer AP) {
            for (AbstractCard c : XuanZhiQu.group)
                if (c != null)
                    c.atTurnStart();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class maxInkField{
        public static SpireField<Integer> maxInks = new SpireField<>(() -> 4);
    }

    public static ArrayList<AbstractCard> cards = new ArrayList<>();

    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class GuiFuShenGongListField{
        public static SpireField<ArrayList<AbstractCard>> GuiFuShenGongList = new SpireField<>(() -> cards);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "updateCardsOnDamage");
            int line = LineFinder.findAllInOrder(ctBehavior, matcher)[0];
            return new int[]{line};
        }
    }
}
