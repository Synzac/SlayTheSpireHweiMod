package Hweimod.patches;

import Hweimod.cards.mould.MouldCard;
import Hweimod.modcore.HweiCardTagsEnum;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import static basemod.BaseMod.logger;

public class UseCardActionPatch {
    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class updateInsertPatch{
        @SpireInsertPatch(locator = Locator.class, localvars = {"spoonProc"})
        public static void Insert(UseCardAction UCA, @ByRef AbstractCard[] ___targetCard, boolean spoonProc){
            if((!UCA.exhaustCard || spoonProc) && !UCA.reboundCard && !___targetCard[0].shuffleBackIntoDrawPile){
                if (___targetCard[0].hasTag(HweiCardTagsEnum.HANG)){
                    MouldCard.xuanzhiquReceiveCard(___targetCard[0], AbstractDungeon.player.discardPile);
                } else if(UCA.returnToHand) {
                    if (AbstractDungeon.player.hand.size() == 10) {
                        AbstractDungeon.player.createHandIsFullDialog();
                    } else
                        AbstractDungeon.player.discardPile.moveToHand(___targetCard[0], AbstractDungeon.player.discardPile);
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "exhaustOnUseOnce");
            int line = LineFinder.findAllInOrder(ctBehavior, matcher)[0];
            return new int[]{line};
        }
    }
}



