package Hweimod.helpers;

import Hweimod.modcore.HweiCardTagsEnum;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModHelper {
    public static final Logger logger = LogManager.getLogger(ModHelper.class.getName());

    public static String makePath(String id) {
        return "HweiMod:" + id;
    }

    //获得能力图片地址
    public static String makePowerAd(String name, boolean isPortrait) {
        String isP = "32";
        if (isPortrait) isP = "84";

        return "HweiModResources/img/powers/" + name + isP + ".png";
    }

    public static void APAcancel(ApplyPowerAction APA){
        ReflectionHacks.setPrivateInherited(APA, ApplyPowerAction.class,"duration", (float)ReflectionHacks.getPrivateInherited(APA, ApplyPowerAction.class,"duration") - Gdx.graphics.getDeltaTime());
        CardCrawlGame.sound.play("NULLIFY_SFX");
    }

    public static AbstractCard.CardTags getSig(AbstractCard card){
        if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)){
            return HweiCardTagsEnum.SIGNATURE_DISASTER;
        } else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_SERENITY)){
            return HweiCardTagsEnum.SIGNATURE_SERENITY;
        } else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_TORMENT)){
            return HweiCardTagsEnum.SIGNATURE_TORMENT;
        } else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DESPAIR)){
            return HweiCardTagsEnum.SIGNATURE_DESPAIR;
        } else {
            return null;
        }
    }

    public static void rotate(Vector2 vec, float radians) {
        float cos = (float) Math.cos((double) radians * 0.017453292F);
        float sin = (float) Math.sin((double) radians * 0.017453292F);
        float newX = vec.x * cos - vec.y * sin;
        float newY = vec.x * sin + vec.y * cos;
        vec.x = newX;
        vec.y = newY;
    }
}
