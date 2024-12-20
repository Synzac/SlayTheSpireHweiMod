package Hweimod.patches;

import Hweimod.cards.mould.MouldCard;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

import java.util.Objects;

public class ApotheosisActionPatch {
    @SpirePatch(clz = ApotheosisAction.class, method = "update")
    public static class updatePatch{
        @SpirePrefixPatch
        public static void PrefixPatch(ApotheosisAction AA){
            if(Objects.equals(ReflectionHacks.getPrivateInherited(AA, AbstractGameAction.class, "duration"), Settings.ACTION_DUR_MED)
                    && !MouldCard.XuanZhiQu.isEmpty())
                ReflectionHacks.privateMethod(ApotheosisAction.class, "upgradeAllCardsInGroup", CardGroup.class).invoke(MouldCard.XuanZhiQu);
        }
    }
}
