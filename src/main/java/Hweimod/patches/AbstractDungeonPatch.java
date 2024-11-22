package Hweimod.patches;

import Hweimod.cards.mould.MouldCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class AbstractDungeonPatch{
    @SpirePatch(clz = AbstractDungeon.class, method = "resetPlayer")
    public static class resetPlayerInsertPatch{
        @SpireInsertPatch(locator = Locator.class)
        public static void resetPlayerPatch(){
            MouldCard.XuanZhiQu.clear();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "exhaustPile");
            int line = LineFinder.findAllInOrder(ctBehavior, matcher)[0];
            return new int[]{line};
        }
    }
}
