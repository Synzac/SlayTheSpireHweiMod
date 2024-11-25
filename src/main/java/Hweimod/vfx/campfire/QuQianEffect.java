package Hweimod.vfx.campfire;

import Hweimod.relics.JiXingWenZhang;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

public class QuQianEffect extends AbstractGameEffect {

    private boolean hasGained = false;

    private static final float DUR = 3.0F;

    private boolean openedScreen = false;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    private int gold;

    public QuQianEffect() {
        if (Settings.FAST_MODE) {
            this.startingDuration = 1.5F;
        } else {
            this.startingDuration = 3.0F;
        }
        this.duration = this.startingDuration;
        this.screenColor.a = 0.0F;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        AbstractDungeon.overlayMenu.proceedButton.hide();
        if(AbstractDungeon.player.hasRelic(JiXingWenZhang.ID)) {
            gold = AbstractDungeon.player.getRelic(JiXingWenZhang.ID).counter;
            AbstractDungeon.player.getRelic(JiXingWenZhang.ID).counter = 0;
        }
    }

    @Override
    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        updateBlackScreenColor();
        if (this.duration < this.startingDuration - 0.5F && !this.hasGained) {
            this.hasGained = true;
            AbstractDungeon.player.gainGold(this.gold);
        }
        if (this.duration < this.startingDuration / 2.0F) {
            this.isDone = true;
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractRoom.waitTimer = 0.0F;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > this.startingDuration - 0.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.startingDuration - 0.5F) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    @Override
    public void dispose() {

    }
}
