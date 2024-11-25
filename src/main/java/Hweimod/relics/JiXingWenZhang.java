package Hweimod.relics;

import Hweimod.helpers.ModHelper;
import Hweimod.ui.campfire.JiXingOption;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class JiXingWenZhang extends CustomRelic {
    public static final String ID = ModHelper.makePath(JiXingWenZhang.class.getSimpleName());
    private static final String IMG_PATH = "HweiModResources/img/relics/" + JiXingWenZhang.class.getSimpleName() + ".png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.FLAT;

    public int gold = 32;

    public JiXingWenZhang() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        this.gold = 32;
    }

    @Override
    public void wasHPLost(int damageAmount) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                damageAmount > 0) {
            flash();
            this.counter += this.gold;
            this.gold /= 2;
            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new JiXingOption(this.counter > 0));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.actNum <= 1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new JiXingWenZhang();
    }

}
