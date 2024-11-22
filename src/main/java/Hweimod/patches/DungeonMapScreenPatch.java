package Hweimod.patches;

import Hweimod.relics.StrangePaintbrush;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import java.util.ArrayList;

public class DungeonMapScreenPatch {
    @SpirePatch(clz = DungeonMapScreen.class, method = "updateControllerInput")
    public static class updateControllerInputSpireReturnPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> updateControllerInputSpireReturn(DungeonMapScreen DMS, @ByRef float[] ___targetOffsetY
                    , @ByRef float[] ___scrollBackTimer, ArrayList<MapRoomNode> ___visibleMapNodes, float ___scrollWaitTimer){
            if ( ___scrollWaitTimer > 0.0F || !Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || DMS.map.legend.isLegendHighlighted || AbstractDungeon.player.viewingRelics) {
                DMS.mapNodeHb = null;
                return SpireReturn.Return();
            }
            if (CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) {
                ___targetOffsetY[0] += Settings.SCROLL_SPEED * 4.0F;
                return SpireReturn.Return();
            }
            if (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) {
                ___targetOffsetY[0] -= Settings.SCROLL_SPEED * 4.0F;
                return SpireReturn.Return();
            }
            if (CInputActionSet.left.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight
                    .isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                ___scrollBackTimer[0] = 0.1F;
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
                ArrayList<MapRoomNode> nodes = new ArrayList<>();
                if (!AbstractDungeon.firstRoomChosen) {
                    for (MapRoomNode n : ___visibleMapNodes) {
                        if (n.y == 0)
                            nodes.add(n);
                    }
                } else {
                    for (MapRoomNode n : ___visibleMapNodes) {
                        boolean paintedConnection = (!n.equals(AbstractDungeon.getCurrMapNode())) && (!n.taken)
                                && AbstractDungeon.player.hasRelic(StrangePaintbrush.ID)
                                && (AbstractDungeon.player.getRelic(StrangePaintbrush.ID).counter > 0)
                                && (n.y == AbstractDungeon.getCurrMapNode().y)
                                && (n.getRoom().getClass() != AbstractDungeon.getCurrRoom().getClass());
                        boolean flightMatters = (AbstractDungeon.player.hasRelic("WingedGreaves") || ModHelper.isModEnabled("Flight"));
                        if (AbstractDungeon.currMapNode.isConnectedTo(n) || (flightMatters && AbstractDungeon.currMapNode
                                .wingedIsConnectedTo(n)) || paintedConnection)
                            nodes.add(n);
                    }
                }
                boolean anyHovered = false;
                int index = 0;
                for (MapRoomNode n : nodes) {
                    if (n.hb.hovered) {
                        anyHovered = true;
                        break;
                    }
                    index++;
                }
                if (!anyHovered && DMS.mapNodeHb == null && !nodes.isEmpty()) {
                    Gdx.input.setCursorPosition(
                            (int) nodes.get(nodes.size() / 2).hb.cX, Settings.HEIGHT -
                                    (int) nodes.get(nodes.size() / 2).hb.cY);
                    DMS.mapNodeHb = nodes.get(nodes.size() / 2).hb;
                } else if (!anyHovered && nodes.isEmpty()) {
                    Gdx.input.setCursorPosition((int)AbstractDungeon.dungeonMapScreen.map.bossHb.cX, Settings.HEIGHT - (int)AbstractDungeon.dungeonMapScreen.map.bossHb.cY);
                    DMS.mapNodeHb = null;
                } else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
                    index--;
                    if (index < 0)
                        index = nodes.size() - 1;
                    Gdx.input.setCursorPosition(
                            (int) nodes.get(index).hb.cX, Settings.HEIGHT -
                                    (int) nodes.get(index).hb.cY);
                    DMS.mapNodeHb = nodes.get(index).hb;
                } else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                    index++;
                    if (index > nodes.size() - 1)
                        index = 0;
                    Gdx.input.setCursorPosition(
                            (int) nodes.get(index).hb.cX, Settings.HEIGHT -
                                    (int) nodes.get(index).hb.cY);
                    DMS.mapNodeHb = nodes.get(index).hb;
                }
            }
            return SpireReturn.Return();
        }
    }
}
