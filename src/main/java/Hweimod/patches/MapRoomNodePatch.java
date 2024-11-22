package Hweimod.patches;

import Hweimod.relics.StrangePaintbrush;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;
import com.megacrit.cardcrawl.vfx.MapCircleEffect;

import static com.megacrit.cardcrawl.map.MapRoomNode.AVAILABLE_COLOR;
import static com.megacrit.cardcrawl.map.MapRoomNode.OFFSET_X;

public class MapRoomNodePatch {
    @SpirePatch(clz = MapRoomNode.class, method = "update")
    public static class updateSpireReturnPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> updateReturn(MapRoomNode MRNode, @ByRef float[] ___animWaitTimer, float ___SPACING_X, float ___OFFSET_Y
                        ,@ByRef float[] ___scale, Color ___NOT_TAKEN_COLOR, float ___angle, @ByRef float[] ___oscillateTimer) {
            if (___animWaitTimer[0] != 0.0F) {
                ___animWaitTimer[0] -= Gdx.graphics.getDeltaTime();
                if (___animWaitTimer[0] < 0.0F) {
                    if (!AbstractDungeon.firstRoomChosen) {
                        AbstractDungeon.setCurrMapNode(MRNode);
                    } else {
                        (AbstractDungeon.getCurrMapNode()).taken = true;
                    }
                    MapEdge connectedEdge = AbstractDungeon.getCurrMapNode().getEdgeConnectedTo(MRNode);
                    if (connectedEdge != null)
                        connectedEdge.markAsTaken();
                    ___animWaitTimer[0] = 0.0F;
                    AbstractDungeon.nextRoom = MRNode;
                    AbstractDungeon.pathX.add(MRNode.x);
                    AbstractDungeon.pathY.add(MRNode.y);
                    CardCrawlGame.metricData.path_taken.add(AbstractDungeon.nextRoom.getRoom().getMapSymbol());
                    if (!AbstractDungeon.isDungeonBeaten) {
                        AbstractDungeon.nextRoomTransitionStart();
                        CardCrawlGame.music.fadeOutTempBGM();
                    }
                }
            }
            ReflectionHacks.privateMethod(MapRoomNode.class, "updateEmerald").invoke(MRNode);
            MRNode.highlighted = false;
            ___scale[0] = MathHelper.scaleLerpSnap(___scale[0], 0.5F);
            MRNode.hb.move(MRNode.x * ___SPACING_X + OFFSET_X + MRNode.offsetX, MRNode.y * Settings.MAP_DST_Y + ___OFFSET_Y + DungeonMapScreen.offsetY + MRNode.offsetY);
            MRNode.hb.update();
            for (MapEdge edge : MRNode.getEdges()) {
                if (!edge.taken)
                    edge.color = ___NOT_TAKEN_COLOR;
            }
            if ((AbstractDungeon.getCurrRoom()).phase.equals(AbstractRoom.RoomPhase.COMPLETE)) {
                if (MRNode.equals(AbstractDungeon.getCurrMapNode()))
                    for (MapEdge edge : MRNode.getEdges())
                        edge.color = AVAILABLE_COLOR;
                boolean normalConnection = AbstractDungeon.getCurrMapNode().isConnectedTo(MRNode);
                boolean wingedConnection = AbstractDungeon.getCurrMapNode().wingedIsConnectedTo(MRNode);
                boolean paintedConnection = (!MRNode.equals(AbstractDungeon.getCurrMapNode())) && (!MRNode.taken) 
                        && AbstractDungeon.player.hasRelic(StrangePaintbrush.ID)
                        && (AbstractDungeon.player.getRelic(StrangePaintbrush.ID).counter > 0)
                        && (MRNode.y == AbstractDungeon.getCurrMapNode().y)
                        && (MRNode.getRoom().getClass() != AbstractDungeon.getCurrRoom().getClass());
                if (normalConnection || Settings.isDebug || wingedConnection || paintedConnection) {
                    if (MRNode.hb.hovered) {
                        if (MRNode.hb.justHovered)
                            ReflectionHacks.privateMethod(MapRoomNode.class, "playNodeHoveredSound").invoke(MRNode);
                        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && AbstractDungeon.dungeonMapScreen.clicked && ___animWaitTimer[0] <= 0.0F) {
                            ReflectionHacks.privateMethod(MapRoomNode.class, "playNodeSelectedSound").invoke(MRNode);
                            AbstractDungeon.dungeonMapScreen.clicked = false;
                            AbstractDungeon.dungeonMapScreen.clickTimer = 0.0F;
                            if (!normalConnection && wingedConnection && !paintedConnection &&
                                    AbstractDungeon.player.hasRelic("WingedGreaves")) {
                                (AbstractDungeon.player.getRelic("WingedGreaves")).counter--;
                                if ((AbstractDungeon.player.getRelic("WingedGreaves")).counter <= 0)
                                    AbstractDungeon.player.getRelic("WingedGreaves").setCounter(-2);
                            } else if (!normalConnection && !wingedConnection && paintedConnection &&
                                    AbstractDungeon.player.hasRelic(StrangePaintbrush.ID)) {
                                (AbstractDungeon.player.getRelic(StrangePaintbrush.ID)).counter--;
                                if ((AbstractDungeon.player.getRelic(StrangePaintbrush.ID)).counter <= 0)
                                    AbstractDungeon.player.getRelic(StrangePaintbrush.ID).setCounter(-2);
                            }
                            AbstractDungeon.topLevelEffects.add(new MapCircleEffect(MRNode.x * ___SPACING_X + OFFSET_X + MRNode.offsetX, MRNode.y * Settings.MAP_DST_Y + ___OFFSET_Y + DungeonMapScreen.offsetY + MRNode.offsetY, ___angle));
                            if (!Settings.FAST_MODE)
                                AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
                            ___animWaitTimer[0] = 0.25F;
                            if (MRNode.room instanceof com.megacrit.cardcrawl.rooms.EventRoom)
                                CardCrawlGame.mysteryMachine++;
                        }
                        MRNode.highlighted = true;
                    } else {
                        MRNode.color = AVAILABLE_COLOR.cpy();
                    }

                    if (!MRNode.taken) {
                        ___oscillateTimer[0] += Gdx.graphics.getDeltaTime() * 5.0F;
                        MRNode.color.a = 0.66F + (MathUtils.cos(___oscillateTimer[0]) + 1.0F) / 6.0F;
                        ___scale[0] = 0.25F + MRNode.color.a;
                    } else {
                        ___scale[0] = MathHelper.scaleLerpSnap(___scale[0], Settings.scale);
                    }

                } else if (MRNode.hb.hovered && !MRNode.taken) {
                    ___scale[0] = 1.0F;
                    MRNode.color = AVAILABLE_COLOR.cpy();
                } else {
                    MRNode.color = ___NOT_TAKEN_COLOR.cpy();
                }
            } else if (MRNode.hb.hovered) {
                ___scale[0] = 1.0F;
                MRNode.color = AVAILABLE_COLOR.cpy();
            } else {
                MRNode.color = ___NOT_TAKEN_COLOR.cpy();
            }
            if (!AbstractDungeon.firstRoomChosen && MRNode.y == 0 && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMPLETE) {
                if (MRNode.hb.hovered) {
                    if (MRNode.hb.justHovered)
                        ReflectionHacks.privateMethod(MapRoomNode.class, "playNodeHoveredSound").invoke(MRNode);
                    if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && (CInputActionSet.select.isJustPressed() || AbstractDungeon.dungeonMapScreen.clicked)) {
                        ReflectionHacks.privateMethod(MapRoomNode.class, "playNodeSelectedSound").invoke(MRNode);
                        AbstractDungeon.dungeonMapScreen.clicked = false;
                        AbstractDungeon.dungeonMapScreen.clickTimer = 0.0F;
                        AbstractDungeon.dungeonMapScreen.dismissable = true;
                        if (!AbstractDungeon.firstRoomChosen)
                            AbstractDungeon.firstRoomChosen = true;
                        AbstractDungeon.topLevelEffects.add(new MapCircleEffect(MRNode.x * ___SPACING_X + OFFSET_X + MRNode.offsetX, MRNode.y * Settings.MAP_DST_Y + ___OFFSET_Y + DungeonMapScreen.offsetY + MRNode.offsetY, ___angle));
                        AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
                        ___animWaitTimer[0] = 0.25F;
                    }
                    MRNode.highlighted = true;
                } else if (MRNode.y != 0) {
                    MRNode.highlighted = true;
                    ___scale[0] = 1.0F;
                } else {
                    MRNode.color = AVAILABLE_COLOR.cpy();
                }

                if (!MRNode.taken) {
                    ___oscillateTimer[0] += Gdx.graphics.getDeltaTime() * 5.0F;
                    MRNode.color.a = 0.66F + (MathUtils.cos(___oscillateTimer[0]) + 1.0F) / 6.0F;
                    ___scale[0] = 0.25F + MRNode.color.a;
                } else {
                    ___scale[0] = MathHelper.scaleLerpSnap(___scale[0], Settings.scale);
                }

            }
            if (MRNode.equals(AbstractDungeon.getCurrMapNode())) {
                MRNode.color = AVAILABLE_COLOR.cpy();
                ___scale[0] = 0.5F;
            }
            return SpireReturn.Return();
        }
    }
}
