package Hweimod.patches;

import Hweimod.cards.colorless.Ying;
import Hweimod.cards.mould.MouldCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

import java.util.Objects;

public class AbstractRoomPatch{
    @SpirePatch(clz = AbstractRoom.class, method = "endTurn")
    public static class endTurnInsertPatch{
        @SpireInsertPatch(locator = Locator.class)
        public static void endTurnPatch(AbstractRoom AR){
            for (AbstractCard c : MouldCard.XuanZhiQu.group){
                if(Objects.equals(c.cardID, Ying.ID)) {
                    c.baseDamage += c.magicNumber;
                    c.baseBlock += c.magicNumber;
                }
                c.resetAttributes();
            }
        }
    }

    @SpirePatch(clz = AbstractRoom.class, method = "endBattle")
    public static class endBattlePatch{
        @SpirePrefixPatch
        public static void PrefixPatch(AbstractRoom AR){
            MouldCard.XuanZhiQu.clear();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "hoveredCard");
            int line = LineFinder.findAllInOrder(ctBehavior, matcher)[0];
            return new int[]{line};
        }
    }
}
