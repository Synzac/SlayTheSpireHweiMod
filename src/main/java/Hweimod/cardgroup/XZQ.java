package Hweimod.cardgroup;

import Hweimod.modcore.HweiCardGroupTypeEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.isScreenUp;
import static com.megacrit.cardcrawl.helpers.MathHelper.cardScaleLerpSnap;

public class XZQ extends CardGroup {

    private static final float AniTime = 1.0F;
    private final Color color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    private static final TextureAtlas.AtlasRegion region128 = AbstractPower.atlas.findRegion("128/flight");
    private final float IMG_WIDTH = 300.0F * Settings.scale;
    private final float IMG_HEIGHT = 420.0F * Settings.scale;
    private static float drawStartX;
    private static float drawEndX;
    private static float drawStartY;
    private static float padX;
    private static float scale;
    public int MaxCount = 5;
    public Set<Mapcard> oldList = new HashSet<>();
    public Set<Mapcard> newList;

    public Mapcard hoveredMapcard;
    public ArrayList<AbstractCard> cardsInUse;

    public XZQ(CardGroupType type) {
        super(HweiCardGroupTypeEnum.XUANZHI);
        drawStartX = (float) Settings.WIDTH * 0.4F;
        drawEndX = (float) Settings.WIDTH * 0.4F;
        drawStartY = (float) Settings.HEIGHT * 0.72F;
        padX = 160.0F * Settings.scale;
        scale = 5F / MaxCount;
        cardsInUse = new ArrayList<>();
    }

    @Override
    public void update() {
        if (!isScreenUp) {

            //消失的牌就做一个慢慢变透明的特效算了
            scale = (float) Math.sqrt(5F / MaxCount);
            if (scale > 1.5F) scale = 1.5F;
            drawStartX = (float) Settings.WIDTH * 0.4F - MaxCount * 80.0F * Settings.scale * scale;
            drawEndX = (float) Settings.WIDTH * 0.4F + MaxCount * 80.0F * Settings.scale * scale;
            padX = (drawEndX - drawStartX) / MaxCount;

            //删除部分2，将部分0转化为1
            Iterator<Mapcard> it = oldList.iterator();
            Mapcard mapcard;
            while (it.hasNext()) {
                mapcard = it.next();
                if (mapcard.status == 2) {
                    mapcard.disappearance -= Gdx.graphics.getDeltaTime() * 2F;
                    if (mapcard.disappearance < 0) mapcard.disappearance = 0;
                }
                if (mapcard.status == 0) {
                    mapcard.emergence -= Gdx.graphics.getDeltaTime() * 2F;
                    if (mapcard.emergence < 0) mapcard.emergence = 0;
                }

                if (mapcard.emergence == 0) {
                    mapcard.status = 1; //0变1
                    mapcard.emergence = AniTime;
                }

                if (mapcard.disappearance == 0) {
                    it.remove(); //删除2
                }
            }

            //根据现在的抽牌堆创建新的映射牌表
            newList = new HashSet<>();
            AbstractCard tempCard;
            for (int i = this.size() - 1; i >= 0; i--) {
                tempCard = this.group.get(i);
                newList.add(new Mapcard(tempCard,
                        i - this.size() + MaxCount, -1));
            }

            //和之前的对比，修改卡牌映射的状态,更新target
            Mapcard oldm;
            for (Mapcard m : newList) {
                oldm = contain(m.c, oldList);
                if (oldm != null) {
                    oldm.index = m.index;
                } else {
                    oldList.add(new Mapcard(m.c, m.index, 0));
                }
            }

            for (Mapcard m : oldList) {
                if (contain(m.c, newList) == null) {
                    m.status = 2;
                }
            }

            //更新每张牌的位置与状态
            boolean isHovered = false;
            for (Mapcard m : oldList) {

                try {
                    selfUpdate(m.c);
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                m.currentX = cardScaleLerpSnap(m.currentX, rtTargetX(m));
                m.hb.move(m.currentX, drawStartY);
                m.hb.resize(IMG_WIDTH * m.drawScale, IMG_HEIGHT * m.drawScale);
                m.hb.encapsulatedUpdate(m);

                if (m.hb.hovered && m.status != 0 && m.status != 2) {
                    m.drawScale = 0.6F * scale;
                    hoveredMapcard = m;
                    isHovered = true;
                } else m.drawScale = 0.5F * scale;
            }

            if (!isHovered) hoveredMapcard = null;
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setColor(color);
        float tempCurrentX, tempCurrentY, tempTransparency, tempScale;
        for (Mapcard m : oldList) {
            tempCurrentX = m.c.current_x;
            tempCurrentY = m.c.current_y;
            tempTransparency = m.c.transparency;
            tempScale = m.c.drawScale;

            m.c.current_x = m.currentX;
            m.c.current_y = drawStartY;
            m.c.drawScale = m.drawScale;
            if (m.status == 0)
                m.c.transparency = m.emergence / AniTime;
            else if (m.status == 2)
                m.c.transparency = m.disappearance / AniTime;

            m.c.render(sb);

            if (m.status != 0 && m.status != 2)
                m.hb.render(sb);

            m.c.current_x = tempCurrentX;
            m.c.current_y = tempCurrentY;
            m.c.transparency = tempTransparency;
            m.c.drawScale = tempScale;
        }

    }

    public class Mapcard implements HitboxListener {
        public AbstractCard c;
        public int index;
        public int status;
        public float disappearance = AniTime;
        public float emergence = AniTime;
        public float currentX = drawStartX;
        public float drawScale = 0.5F;
        public Hitbox hb;

        public Mapcard(AbstractCard c, int index, int status) {
            this.c = c;
            this.index = index;
            this.status = status;
            this.hb = new Hitbox(IMG_WIDTH * 0.5F, IMG_HEIGHT * 0.5F);
        }

        @Override
        public void hoverStarted(Hitbox hitbox) {
        }

        @Override
        public void startClicking(Hitbox hitbox) {
        }

        @Override
        public void clicked(Hitbox hitbox) {

        }

    }
    private Mapcard contain(AbstractCard c, Set<Mapcard> arr) {
        for (Mapcard m : arr) {
            if (m.c.uuid == c.uuid) return m;
        }
        return null;
    }

    private void selfUpdate(AbstractCard c) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (c.angle != c.targetAngle) {
            c.angle = MathHelper.angleLerpSnap(c.angle, c.targetAngle);
        }

        Class cardClass = AbstractCard.class;
        Method updateTransparency = cardClass.getDeclaredMethod("updateTransparency");
        Method updateColor = cardClass.getDeclaredMethod("updateColor");
        updateTransparency.setAccessible(true);
        updateColor.setAccessible(true);
        updateTransparency.invoke(c);
        updateColor.invoke(c);
    }

    private float rtTargetX(Mapcard m) {
        return drawStartX + m.index * padX;
    }




}
