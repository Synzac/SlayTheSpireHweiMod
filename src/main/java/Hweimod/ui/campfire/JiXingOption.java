package Hweimod.ui.campfire;

import Hweimod.relics.JiXingWenZhang;
import Hweimod.vfx.campfire.QuQianEffect;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

public class JiXingOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("HweiMod:JiXing Option");

    public static final String[] TEXT = uiStrings.TEXT;

    public static final Texture texture = new Texture("HweiModResources/img/UI/campfire/QuQian.png");

    public JiXingOption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.description = TEXT[1];
        this.img = texture;
    }

    public void useOption() {
        if (this.usable) {
            if(AbstractDungeon.player.hasRelic(JiXingWenZhang.ID)) {
                AbstractDungeon.effectList.add(new RainingGoldEffect(AbstractDungeon.player.getRelic(JiXingWenZhang.ID).counter));
            }
            AbstractDungeon.effectList.add(new QuQianEffect());
        }
    }
}
