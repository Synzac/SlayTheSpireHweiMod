package Hweimod.relics;

import Hweimod.helpers.ModHelper;
import Hweimod.powers.HeartsteelPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Heartsteel extends CustomRelic {
    public static final String ID = ModHelper.makePath(Heartsteel.class.getSimpleName());

    private static final String IMG_PATH = "HweiModResources/img/relics/" + Heartsteel.class.getSimpleName() + ".png";

    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.SHOP;

    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.FLAT;

    public Heartsteel() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void atBattleStart() {
        for (AbstractMonster mo:(AbstractDungeon.getCurrRoom()).monsters.monsters){
            addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new HeartsteelPower(mo)));
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Heartsteel();
    }
}
