package Hweimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CtBehavior;

public class GridCardSelectScreenPatch {
    @SpirePatch(clz = GridCardSelectScreen.class, method = "open", paramtypez = {CardGroup.class, int.class, String.class, boolean.class, boolean.class, boolean.class, boolean.class})
    public static class openInsertPatch{
        @SpireInsertPatch(locator = Locator.class)
        public static void openPatch(GridCardSelectScreen GCSS, CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge){
            if(tipMsg.equals(CardCrawlGame.languagePack.getUIString("HweiMod:OptionalQuXiaAction").TEXT[0]) && canCancel)
                AbstractDungeon.overlayMenu.cancelButton.show(CardCrawlGame.languagePack.getUIString("GridCardSelectScreen").TEXT[1]);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "previousScreen");
            int line = LineFinder.findAllInOrder(ctBehavior, matcher)[0];
            return new int[]{line};
        }
    }
}
