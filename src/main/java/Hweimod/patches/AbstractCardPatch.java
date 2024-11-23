package Hweimod.patches;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import static Hweimod.helpers.ModHelper.*;

public class AbstractCardPatch {
    @SpirePatch(clz = AbstractCard.class,
            method = "render",
            paramtypez = {SpriteBatch.class})
    public static class PatchRenderImaginary {

        private static Matrix4 mx4 = new Matrix4();
        private static final Matrix4 rotatedTextMatrix = new Matrix4();

        public static void Postfix(AbstractCard card, SpriteBatch sb) {
            AbstractCard.CardTags tag = ModHelper.getSig(card);
            if (tag != null) {
                Texture t = getTexture(tag);

                mx4.setToRotation(0.0F, 0.0F, 1.0F, card.angle);

                Vector2 vec = new Vector2(-114.0F,182.0F);
                ModHelper.rotate(vec, card.angle);
                mx4.trn(card.current_x + vec.x * Settings.scale * card.drawScale,
                        card.current_y + vec.y * Settings.scale * card.drawScale, 0.0F);
                sb.end();
                sb.setTransformMatrix(mx4);
                sb.begin();
                if (t != null)
                    sb.draw(t, (float) (card.getCardBgAtlas().getRegionWidth()*card.drawScale*Settings.scale*0.65), (float) (-card.getCardBgAtlas().getRegionHeight()*card.drawScale*Settings.scale*0.8),
                            0, 0, t.getWidth(), t.getHeight(), card.drawScale * 0.8F, card.drawScale * 0.8F, 0,
                            0, 0, t.getWidth(), t.getHeight(), false, false);
                sb.end();
                sb.setTransformMatrix(rotatedTextMatrix);
                sb.begin();
            }
        }
    }

    private static Texture getTexture(AbstractCard.CardTags tag) {
        Texture t = null;
        if(tag == HweiCardTagsEnum.SIGNATURE_DISASTER) {
            t = texture1;
        } else if (tag == HweiCardTagsEnum.SIGNATURE_SERENITY){
            t = texture2;
        } else if (tag == HweiCardTagsEnum.SIGNATURE_TORMENT) {
            t = texture3;
        } else if (tag == HweiCardTagsEnum.SIGNATURE_DESPAIR) {
            t = texture4;
        }
        return t;
    }
}
