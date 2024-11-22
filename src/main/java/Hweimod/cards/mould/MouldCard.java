package Hweimod.cards.mould;

import Hweimod.actions.GainShieldAction;
import Hweimod.actions.MagicDamageAllEnemiesAction;
import Hweimod.actions.SignatureAction;
import Hweimod.cardgroup.XZQ;
import Hweimod.cards.HanHai;
import Hweimod.cards.LingGuangZhaXian;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardGroupTypeEnum;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiColorEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.modifier.SignatureModifier;
import Hweimod.powers.*;
import basemod.AutoAdd;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.Objects;

@AutoAdd.Ignore
public abstract class MouldCard extends CustomCard {
    private static final TextureAtlas cardAtlas = new TextureAtlas(Gdx.files.internal("cards/cards.atlas"));

    public CardStrings cardStrings;
    public String DESCRIPTION;
    public String DESCRIPTION_UPG;
    public String[] EXTENDED_DESCRIPTION;

    public static CardGroup XuanZhiQu = new XZQ(HweiCardGroupTypeEnum.XUANZHI);

    public AbstractCardModifier modifier = null;

    public MouldCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET
    ) {
        this(NAME, COST, TYPE, RARITY, TARGET, HweiColorEnum.HWEI_COLOR);
    }

    public MouldCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET,
            CardColor color
    ) {
        super( ModHelper.makePath(NAME), rtCardStrings(NAME).NAME, rtPicPath(NAME, TYPE),
                COST, rtCardStrings(NAME).DESCRIPTION, TYPE, color, RARITY, TARGET);
        cardStrings = rtCardStrings(NAME);
        DESCRIPTION = cardStrings.DESCRIPTION;
        DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    }

    public static String rtPicPath(String name, CardType type) {
        if (Gdx.files.internal("HweiModResources/img/cards/" + name + ".png").exists())
            return "HweiModResources/img/cards/" + name + ".png";

        if (type == CardType.ATTACK)
            return "HweiModResources/img/cards/attack.png";
        else if (type == CardType.SKILL)
            return "HweiModResources/img/cards/skill.png";
        else if (type == CardType.POWER)
            return "HweiModResources/img/cards/power.png";
        else
            return "HweiModResources/img/cards/temp.png";
    }

    public static CardStrings rtCardStrings(String name) {
        return CardCrawlGame.languagePack.
                getCardStrings(ModHelper.makePath(name));
    }

    @Override
    public abstract void use(AbstractPlayer p, AbstractMonster m);

    @Override
    public abstract void upgrade();

    public void signature(AbstractPlayer p, AbstractCreature creature){
        addToBot(new SignatureAction(this, p, creature));
    }

    public static void xuanzhiquReceiveCard(AbstractCard c, CardGroup cardGroup){
        if (AbstractDungeon.player.hoveredCard == c)
            AbstractDungeon.player.releaseCard();
        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.lighten(true);
        c.stopGlowing();
        cardGroup.group.remove(c);

        receive(c);
    }

    public static void xuanzhiquReceiveCard(AbstractCard c){
        UnlockTracker.markCardAsSeen(c.cardID);
        c.update();
        receive(c);
    }

    public static void receive(AbstractCard c) {
        if(Objects.equals(c.cardID, LingGuangZhaXian.ID)){
            CardTags tag2;
            int roll = AbstractDungeon.cardRandomRng.random(99);
            if (roll < 30) tag2 = HweiCardTagsEnum.SIGNATURE_DISASTER;
            else if (roll < 60) tag2 = HweiCardTagsEnum.SIGNATURE_SERENITY;
            else if (roll < 90) tag2 = HweiCardTagsEnum.SIGNATURE_TORMENT;
            else tag2 = HweiCardTagsEnum.SIGNATURE_DESPAIR;
            CardModifierManager.addModifier(c, new SignatureModifier(tag2));
        }

        while (XuanZhiQu.size() >= 5){
            XuanZhiQu.moveToDeck(XuanZhiQu.getTopCard(), false);
        }

        if(ModHelper.getSig(c) == null && AbstractDungeon.player.hasPower(JinRanPower.POWER_ID)){
            if(AbstractDungeon.player.hasPower(Subject_DisasterPower.POWER_ID))
                CardModifierManager.addModifier(c, new SignatureModifier(HweiCardTagsEnum.SIGNATURE_DISASTER));
            else if (AbstractDungeon.player.hasPower(Subject_SerenityPower.POWER_ID))
                CardModifierManager.addModifier(c, new SignatureModifier(HweiCardTagsEnum.SIGNATURE_SERENITY));
            else if (AbstractDungeon.player.hasPower(Subject_TormentPower.POWER_ID))
                CardModifierManager.addModifier(c, new SignatureModifier(HweiCardTagsEnum.SIGNATURE_TORMENT));
            else if (AbstractDungeon.player.hasPower(Subject_DespairPower.POWER_ID))
                CardModifierManager.addModifier(c, new SignatureModifier(HweiCardTagsEnum.SIGNATURE_DESPAIR));
        }

        if(ModHelper.getSig(c) == HweiCardTagsEnum.SIGNATURE_DISASTER && AbstractDungeon.player.hasPower(HaoJiePower.POWER_ID)){
            AbstractDungeon.actionManager.addToTop(
                    new MagicDamageAllEnemiesAction(AbstractDungeon.player, 3, HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
        }

        XuanZhiQu.addToBottom(c);
        c.flashVfx = null;
    }

    public static void quxia(boolean forUse, boolean forExhaust){
        quxia(forUse, forExhaust, null, null);
    }

    public static void quxia(boolean forUse, boolean forExhaust, CardTags tag){
        quxia(forUse, forExhaust, null, tag);
    }

    public static void quxia(boolean forUse, boolean forExhaust, AbstractMonster target){
        quxia(forUse, forExhaust, target, null);
    }

    public static void quxia(boolean forUse, boolean forExhaust, AbstractMonster target, CardTags tag){
        if(XuanZhiQu.isEmpty())
            return;

        AbstractCard card = XuanZhiQu.getTopCard();
        if (tag != null)
            CardModifierManager.addModifier(card, new SignatureModifier(tag));

        if(ModHelper.getSig(card) == HweiCardTagsEnum.SIGNATURE_SERENITY && AbstractDungeon.player.hasPower(HanHaiPower.POWER_ID)){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player,
                    new InkPower(AbstractDungeon.player, AbstractDungeon.player.getPower(HanHaiPower.POWER_ID).amount)));
        }

        if (forExhaust) {
            if(forUse) {
                XuanZhiQu.group.remove(card);
                card.exhaustOnUseOnce = true;
                uSe(target, card);
            } else {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, XuanZhiQu));
            }
        } else {
            if(forUse) {
                XuanZhiQu.group.remove(card);
                uSe(target, card);
            } else {
                if (AbstractDungeon.player.hand.size() == 10) {
                    XuanZhiQu.moveToDiscardPile(card);
                    AbstractDungeon.player.createHandIsFullDialog();
                } else XuanZhiQu.moveToHand(card, XuanZhiQu);
            }
        }

    }

    public static void uSe(AbstractMonster target, AbstractCard card) {
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        if(target == null || target.isDead || target.escaped || target.halfDead)
            AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true));
        else AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(card, target, false, true));
        AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
        if (!Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
    }
}

