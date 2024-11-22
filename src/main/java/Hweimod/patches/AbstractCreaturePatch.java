package Hweimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class AbstractCreaturePatch {
    @SpirePatch(clz = AbstractCreature.class, method = "addBlock", paramtypez = {int.class})
    public static class addBlockInsertPatch{
        @SpireInsertPatch(locator = Locator.class, localvars = "tmp")
        public static void addBlockPatch(AbstractCreature AC, float tmp){
            if(!AC.isPlayer){
                for (AbstractPower p : AC.powers)
                    p.onGainedBlock(tmp);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "isPlayer");
            int line = LineFinder.findAllInOrder(ctBehavior, matcher)[0];
            return new int[]{line};
        }
    }
}
