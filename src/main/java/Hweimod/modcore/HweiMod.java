package Hweimod.modcore;

import Hweimod.cards.Strike;
import Hweimod.characters.Hwei;
import Hweimod.potion.Elixir_of_Sorcery;
import Hweimod.relics.StrangePaintbrush;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static Hweimod.modcore.HweiColorEnum.HWEI_COLOR;
import static com.badlogic.gdx.graphics.Color.*;

@SpireInitializer
public class HweiMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber, PostInitializeSubscriber{
    public static final Logger logger = LogManager.getLogger(HweiMod.class.getName());

    // 人物选择界面按钮的图片
    private static final String HWEI_CHARACTER_BUTTON = "HweiModResources/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String HWEI_CHARACTER_PORTRAIT = "HweiModResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "HweiModResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "HweiModResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "HweiModResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "HweiModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "HweiModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "HweiModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "HweiModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "HweiModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENERGY_ORB = "HweiModResources/img/char/cost_orb.png";

    public static final Color MY_COLOR = new Color(24.0F / 255.0F, 97.0F / 255.0F, 111.0F / 255.0F, 1.0F);

    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();

    public HweiMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(HWEI_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENERGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
    }

    public static void initialize(){
        new HweiMod();
    }

    @Override
    public void receiveEditStrings() {
        String relic = "", card = "", power = "", potion = "", event = "", ui = "", tutorial = "";

        logger.info("===============加载文字信息===============");

        String lang;
        if(Settings.language == Settings.GameLanguage.ZHS){
            lang = "ZHS";
        } else {
            lang = "ENG";
        }

        ui = "HweiModResources/localization/" + lang + "/ui.json";
        card = "HweiModResources/localization/" + lang + "/cards.json";
        tutorial = "HweiModResources/localization/" + lang + "/characters.json";
        relic = "HweiModResources/localization/" + lang + "/relics.json";
        power = "HweiModResources/localization/" + lang + "/powers.json";
        potion = "HweiModResources/localization/" + lang + "/potions.json";

        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        String tutorialStrings = Gdx.files.internal(tutorial).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);

        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);

        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        logger.info("===============加载文字信息===============");
    }

    @Override
    public void receiveEditCards(){
        logger.info("================加入卡牌================");
        new AutoAdd("HweiMod") // 这里填写你在ModTheSpire.json中写的modid
                .packageFilter(Strike.class) // 寻找所有和此类同一个包及内部包的类
                .setDefaultSeen(true) // 是否将卡牌标为可见
                .cards(); // 开始批量添加卡牌
        logger.info("================加入卡牌================");
        BaseMod.addDynamicVariable(new SignatureVariable());
        BaseMod.addDynamicVariable(new FaLiVariable());
        BaseMod.addDynamicVariable(new JiXingVariable());
    }

    @Override
    public void receiveAddAudio(){
        BaseMod.addAudio("QingHuiYeNing", "HweiModResources/audio/QingHuiYeNing.mp3");
        BaseMod.addAudio("JiHunTongJi", "HweiModResources/audio/JiHunTongJi.mp3");
    }

    @Override
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new Hwei("Hwei"), HWEI_CHARACTER_BUTTON, HWEI_CHARACTER_PORTRAIT, PlayerEnum.HWEI);
    }

    @Override
    public void receiveEditRelics() {
        logger.info("================加入遗物================");
        new AutoAdd("HweiMod")
                .packageFilter(StrangePaintbrush.class)
                .setDefaultSeen(true)
                .any(CustomRelic.class,(info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, HWEI_COLOR);
                    if (info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
        logger.info("================加入遗物================");
    }

    @Override
    public void receivePostInitialize(){
        BaseMod.addPotion(Elixir_of_Sorcery.class, BLUE, BLUE, null, Elixir_of_Sorcery.ID);
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("===============加载关键字===============");
        Gson gson = new Gson();
        String lang = "ENG";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        }

        String keywordsPath = "HweiModResources/localization/" + lang + "/keywords.json";
        Keywords keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
        if (keywords != null) {
            for (Keyword keyword : keywords.keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
                logger.info("Loading keyword : " + keyword.NAMES[0]);
            }
        }
        logger.info("===============加载关键字===============");
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    class Keywords {
        Keyword[] keywords;
    }
}

















