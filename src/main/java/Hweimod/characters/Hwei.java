package Hweimod.characters;

import Hweimod.cards.*;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiColorEnum;
import Hweimod.modcore.HweiMod;
import Hweimod.patches.AbstractPlayerPatch;
import Hweimod.powers.InkPower;
import Hweimod.relics.StrangePaintbrush;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.green.Survivor;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static Hweimod.modcore.PlayerEnum.HWEI;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;
import static com.megacrit.cardcrawl.helpers.ModHelper.isModEnabled;

public class Hwei extends CustomPlayer {

    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = "HweiModResources/img/char/shoulder1.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = "HweiModResources/img/char/shoulder2.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "HweiModResources/img/char/corpse.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[]{
            "HweiModResources/img/UI/orb/layer5.png",
            "HweiModResources/img/UI/orb/layer4.png",
            "HweiModResources/img/UI/orb/layer3.png",
            "HweiModResources/img/UI/orb/layer2.png",
            "HweiModResources/img/UI/orb/layer1.png",
            "HweiModResources/img/UI/orb/layer6.png",
            "HweiModResources/img/UI/orb/layer5d.png",
            "HweiModResources/img/UI/orb/layer4d.png",
            "HweiModResources/img/UI/orb/layer3d.png",
            "HweiModResources/img/UI/orb/layer2d.png",
            "HweiModResources/img/UI/orb/layer1d.png"
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    // 人物的本地化文本
    private static final TutorialStrings README = CardCrawlGame.languagePack.getTutorialString("HweiREADME");

    private static final int STARTING_HP = 72;
    private static final int MAX_HP = 72;
    private static final int STARTING_GOLD = 99;
    private static final int DRAW_SIZE = 5;
    private static final int ASCENSION_MAX_HP_LOSS = 6;
    public int maxInks;

    public Hwei(String name) {
        super(name, HWEI,ORB_TEXTURES,"HweiModResources/img/UI/orb/vfx.png", LAYER_SPEED, null, null);

        this.drawY = AbstractDungeon.floorY - 40.0F;

        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                "HweiModResources/img/char/character.png", // 人物图片
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );

        // 如果你的人物没有动画，那么这些不需要写
        // this.loadAnimation("ExampleModResources/img/char/character.atlas", "ExampleModResources/img/char/character.json", 1.8F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // e.setTimeScale(1.2F);
    }

    // 初始卡组
    @Override
    public ArrayList<String> getStartingDeck() {
        ModHelper.logger.info("================设置初始卡组================");
        ArrayList<String> retVal = new ArrayList<>();
        for(int x = 0; x<4; x++) {
            retVal.add(Strike.ID);
        }
        for (int x = 0; x<4; x++) {
            retVal.add(Defend.ID);
        }
        retVal.add(ZaiE.ID);
        retVal.add(JingMing.ID);

        return retVal;
    }

    // 初始遗物
    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(StrangePaintbrush.ID);

        //retVal.add(Heartsteel.ID);

        return retVal;
    }

    //基础属性
    @Override
    public CharSelectInfo getLoadout() {
        String title = README.LABEL[0];
        String introduction = README.TEXT[0];

        return new CharSelectInfo(
                title, // 人物名字
                introduction, // 人物介绍
                STARTING_HP, // 当前血量
                MAX_HP, // 最大血量
                0, // 初始充能球栏位
                STARTING_GOLD, // 初始携带金币
                DRAW_SIZE, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    // 人物名字（出现在游戏左上角）
    @Override
    public String getTitle(PlayerClass playerClass) {
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "异画师";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "奇想繪師";
        } else {
            title = "The Visionary";
        }
        return title;
    }

    // 你的卡牌颜色
    @Override
    public AbstractCard.CardColor getCardColor() {
        return HweiColorEnum.HWEI_COLOR;
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return HweiMod.MY_COLOR;
    }

    // 翻牌事件出现的你的职业牌
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return HweiMod.MY_COLOR;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    // 卡牌的能量字体
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // 人物选择界面点击你的人物按钮时触发的方法
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "异画师";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "奇想繪師";
        } else {
            title = "The Visionary";
        }
        return title;
    }

    // 创建人物实例
    @Override
    public AbstractPlayer newInstance() {
        return new Hwei(this.name);
    }

    // 第三章面对心脏说的话
    @Override
    public String getSpireHeartText() {
        return "这就是。。。这座高塔的心脏？";
    }

    // 打心脏的颜色
    @Override
    public Color getSlashAttackColor() {
        return HweiMod.MY_COLOR;
    }

    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    // 吸血鬼事件文本
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public void initializeStarterDeck() {
        ArrayList<String> cards = getStartingDeck();
        boolean addBaseCards = true;
        if (isModEnabled("Draft") || isModEnabled("Chimera") || isModEnabled("SealedDeck") ||
                isModEnabled("Shiny") || isModEnabled("Insanity"))
            addBaseCards = false;
        if (isModEnabled("Chimera")) {
            this.masterDeck.addToTop(new Bash());
            this.masterDeck.addToTop(new Survivor());
            this.masterDeck.addToTop(new Zap());
            this.masterDeck.addToTop(new Eruption());
            this.masterDeck.addToTop(new Strike_Red());
            this.masterDeck.addToTop(new Strike_Green());
            this.masterDeck.addToTop(new Strike_Blue());
            this.masterDeck.addToTop(new Defend_Red());
            this.masterDeck.addToTop(new Defend_Green());
            this.masterDeck.addToTop(new Defend_Watcher());
        }
        if (isModEnabled("Insanity"))
            for (int i = 0; i < 50; i++)
                this.masterDeck.addToTop(AbstractDungeon.returnRandomCard().makeCopy());
        if (isModEnabled("Shiny")) {
            CardGroup group = AbstractDungeon.getEachRare();
            for (AbstractCard c : group.group)
                this.masterDeck.addToTop(c);
        }
        if (addBaseCards)
            for (String s : cards)
                this.masterDeck.addToTop(CardLibrary.getCard(this.chosenClass, s).makeCopy());
        for (AbstractCard c : this.masterDeck.group)
            UnlockTracker.markCardAsSeen(c.cardID);

        AbstractPlayerPatch.GuiFuShenGongListField.GuiFuShenGongList.get(this).clear();
        AbstractPlayerPatch.GuiFuShenGongListField.GuiFuShenGongList.get(this).add(new IronWave());
        AbstractPlayerPatch.GuiFuShenGongListField.GuiFuShenGongList.get(this).addAll(srcCommonCardPool.group);
        AbstractPlayerPatch.GuiFuShenGongListField.GuiFuShenGongList.get(this).addAll(srcUncommonCardPool.group);
        AbstractPlayerPatch.GuiFuShenGongListField.GuiFuShenGongList.get(this).addAll(srcRareCardPool.group);
    }

    @Override
    protected void initializeClass(String imgUrl, String shoulder2ImgUrl, String shouldImgUrl, String corpseImgUrl, CharSelectInfo info, float hb_x, float hb_y, float hb_w, float hb_h, EnergyManager energy) {
        super.initializeClass(imgUrl, shoulder2ImgUrl, shouldImgUrl, corpseImgUrl, info, hb_x, hb_y, hb_w, hb_h, energy);
        AbstractPlayerPatch.maxInkField.maxInks.set(this, 4);
    }
}
