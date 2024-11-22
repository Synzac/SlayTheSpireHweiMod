package Hweimod.patches;

import Hweimod.modcore.HweiDamageTypeEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class VulnerablePowerPatch {
    @SpirePatch(clz = VulnerablePower.class, method = "atDamageReceive")
    public static class PostfixPatch{
        @SpirePostfixPatch
        public static float postfix(VulnerablePower VP, float damage, DamageInfo.DamageType type){
            if (type == HweiDamageTypeEnum.MAGIC || type == HweiDamageTypeEnum.DOUBLE_MAGIC) {
                if (VP.owner.isPlayer && AbstractDungeon.player.hasRelic("Odd Mushroom"))
                    return damage * 1.25F;
                if (VP.owner != null && !VP.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Frog"))
                    return damage * 1.75F;
                return damage * 1.5F;
            } else if (type == DamageInfo.DamageType.NORMAL) {
                if (VP.owner.isPlayer && AbstractDungeon.player.hasRelic("Odd Mushroom"))
                    return damage * 1.25F;
                if (VP.owner != null && !VP.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Frog"))
                    return damage * 1.75F;
                return damage * 1.5F;
            }
            return damage;
        }
    }
}
