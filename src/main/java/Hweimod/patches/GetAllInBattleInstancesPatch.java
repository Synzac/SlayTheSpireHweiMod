package Hweimod.patches;

import Hweimod.cards.mould.MouldCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.HashSet;
import java.util.UUID;

public class GetAllInBattleInstancesPatch {
    @SpirePatch(clz = GetAllInBattleInstances.class, method = "get", paramtypez = {UUID.class})
    public static class getPatch{
        @SpirePostfixPatch
        public static HashSet<AbstractCard> Postfix(HashSet<AbstractCard> cards, UUID uuid){
            for (AbstractCard card : MouldCard.XuanZhiQu.group){
                if (!card.uuid.equals(uuid))
                    continue;
                cards.add(card);
            }
            return cards;
        }
    }
}
