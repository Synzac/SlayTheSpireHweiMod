package Hweimod.patches;

import Hweimod.cards.mould.MouldCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class XZQUpdateAndRender {

    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class UpdateAT {
        public static void Postfix(AbstractDungeon _inst) {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
                MouldCard.XuanZhiQu.update();
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "render")
    public static class RenderAT {
        @SpireInsertPatch(rloc = 28)
        public static void insert(AbstractDungeon _inst, SpriteBatch sb) {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
                MouldCard.XuanZhiQu.render(sb);
        }
    }
}
