package Hweimod.patches;

import Hweimod.actions.DoubleMagicDamageAllEnemiesAction;
import Hweimod.actions.GainShieldAction;
import Hweimod.actions.MagicDamageAction;
import Hweimod.actions.MagicDamageAllEnemiesAction;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.powers.*;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ApplyPowerActionPatch {
    public static String[] HWEITEXT = CardCrawlGame.languagePack.getUIString("HweiMod:ApplyPowerAction").TEXT;
    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class UpdateSpireReturnPatch{
        @SpireInsertPatch(rloc = 55)
        public static SpireReturn<Void> UpdateReturn(ApplyPowerAction APA){
            int d1 = 3, d2 = 12, sh = 2;
            String powerToApply = ((AbstractPower) ReflectionHacks.getPrivate(APA, ApplyPowerAction.class, "powerToApply")).ID;
            boolean isPlayer = false;
            if(APA.source!=null)
                isPlayer = APA.source.isPlayer;
            if (APA.target.hasPower(Color_DisasterPower.POWER_ID)){
                if (powerToApply.equals(Color_DisasterPower.POWER_ID)){
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_DisasterPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_DisasterPower.POWER_ID).onSpecificTrigger();
                    if(APA.target.isPlayer){
                        if(APA.target.hasPower(Subject_DisasterPower.POWER_ID)) {
                            AbstractDungeon.actionManager.addToTop(new MagicDamageAllEnemiesAction(APA.source,
                                    d1, HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
                            AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[0]));
                        } else {
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(APA.source, APA.source,
                                    new Subject_DisasterPower(APA.source)));
                        }
                    } else {
                        AbstractDungeon.actionManager.addToTop(new MagicDamageAction(APA.target, new DamageInfo(APA.source,
                                d1, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.FIRE));
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[0]));
                    }
                    return SpireReturn.Return();
                } else if (powerToApply.equals(Color_SerenityPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_DisasterPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_DisasterPower.POWER_ID).onSpecificTrigger();
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[1]));
                    AbstractDungeon.actionManager.addToTop(new GainShieldAction(APA.source, sh));
                    return SpireReturn.Return();
                } else if (powerToApply.equals(Color_TormentPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_DisasterPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_DisasterPower.POWER_ID).onSpecificTrigger();
                    return KongBu(APA, isPlayer);
                } else if (powerToApply.equals(Color_DespairPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_DisasterPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_DisasterPower.POWER_ID).onSpecificTrigger();
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[4]));
                    AbstractDungeon.actionManager.addToTop(new DoubleMagicDamageAllEnemiesAction(APA.source,
                            d2, HweiDamageTypeEnum.DOUBLE_MAGIC, AbstractGameAction.AttackEffect.FIRE));
                    return SpireReturn.Return();
                } else {

                }
            } else if (APA.target.hasPower(Color_SerenityPower.POWER_ID)) {
                if (powerToApply.equals(Color_SerenityPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_SerenityPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_SerenityPower.POWER_ID).onSpecificTrigger();
                    if (APA.target.isPlayer) {
                        if (APA.target.hasPower(Subject_SerenityPower.POWER_ID)) {
                            AbstractDungeon.actionManager.addToTop(new MagicDamageAllEnemiesAction(APA.source,
                                    d1, HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
                            AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[0]));
                        } else {
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(APA.source, APA.source,
                                    new Subject_SerenityPower(APA.source)));
                        }
                    } else {
                        AbstractDungeon.actionManager.addToTop(new MagicDamageAction(APA.target, new DamageInfo(APA.source,
                                d1, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.FIRE));
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[0]));
                    }
                    return SpireReturn.Return();
                } else if (powerToApply.equals(Color_DisasterPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_SerenityPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_SerenityPower.POWER_ID).onSpecificTrigger();
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[1]));
                    AbstractDungeon.actionManager.addToTop(new GainShieldAction(APA.source, sh));
                    return SpireReturn.Return();
                } else if (powerToApply.equals(Color_TormentPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_SerenityPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_SerenityPower.POWER_ID).onSpecificTrigger();
                    return ZhenYa(APA, isPlayer);
                } else if (powerToApply.equals(Color_DespairPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_SerenityPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_SerenityPower.POWER_ID).onSpecificTrigger();
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[4]));
                    AbstractDungeon.actionManager.addToTop(new DoubleMagicDamageAllEnemiesAction(APA.source,
                            d2, HweiDamageTypeEnum.DOUBLE_MAGIC, AbstractGameAction.AttackEffect.FIRE));
                    return SpireReturn.Return();
                } else {

                }
            } else if (APA.target.hasPower(Color_TormentPower.POWER_ID)){
                if (powerToApply.equals(Color_TormentPower.POWER_ID)){
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_TormentPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_TormentPower.POWER_ID).onSpecificTrigger();
                    if(APA.target.isPlayer){
                        if(APA.target.hasPower(Subject_TormentPower.POWER_ID)) {
                            AbstractDungeon.actionManager.addToTop(new MagicDamageAllEnemiesAction(APA.source,
                                    d1, HweiDamageTypeEnum.MAGIC, AbstractGameAction.AttackEffect.FIRE));
                            AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[0]));
                        } else {
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(APA.source, APA.source,
                                    new Subject_TormentPower(APA.source)));
                        }
                    } else {
                        AbstractDungeon.actionManager.addToTop(new MagicDamageAction(APA.target, new DamageInfo(APA.source,
                                d1, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.FIRE));
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[0]));
                    }
                    return SpireReturn.Return();
                } else if (powerToApply.equals(Color_DisasterPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_TormentPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_TormentPower.POWER_ID).onSpecificTrigger();
                    return KongBu(APA, isPlayer);
                } else if (powerToApply.equals(Color_SerenityPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_TormentPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_TormentPower.POWER_ID).onSpecificTrigger();
                    return ZhenYa(APA, isPlayer);
                } else if (powerToApply.equals(Color_DespairPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_TormentPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_TormentPower.POWER_ID).onSpecificTrigger();
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[4]));
                    AbstractDungeon.actionManager.addToTop(new DoubleMagicDamageAllEnemiesAction(APA.source,
                            d2, HweiDamageTypeEnum.DOUBLE_MAGIC, AbstractGameAction.AttackEffect.FIRE));
                    return SpireReturn.Return();
                } else {

                }
            } else if (APA.target.hasPower(Color_DespairPower.POWER_ID)) {
                if (powerToApply.equals(Color_DespairPower.POWER_ID)){
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_DespairPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_DespairPower.POWER_ID).onSpecificTrigger();
                    if(APA.target.isPlayer){
                        if(APA.target.hasPower(Subject_DespairPower.POWER_ID)) {
                            for (AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters){
                                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, APA.source,
                                       new VoidPower(mo)));
                            }
                            AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[5]));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(APA.source, APA.source,
                                    new Subject_DespairPower(APA.source)));
                        }
                    } else {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(APA.target, APA.source, new VoidPower(APA.target)));
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[5]));
                    }
                    return SpireReturn.Return();
                } else if (powerToApply.equals(Color_DisasterPower.POWER_ID) || powerToApply
                        .equals(Color_SerenityPower.POWER_ID) || powerToApply
                        .equals(Color_TormentPower.POWER_ID)) {
                    ModHelper.APAcancel(APA);
                    APA.target.getPower(Color_DespairPower.POWER_ID).flashWithoutSound();
                    APA.target.getPower(Color_DespairPower.POWER_ID).onSpecificTrigger();
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[4]));
                    AbstractDungeon.actionManager.addToTop(new DoubleMagicDamageAllEnemiesAction(APA.source,
                            d2, HweiDamageTypeEnum.DOUBLE_MAGIC, AbstractGameAction.AttackEffect.FIRE));
                    return SpireReturn.Return();
                } else {

                }
            }

            if (APA.target.hasPower(StasisPower.POWER_ID) &&
                    ((AbstractPower)ReflectionHacks.getPrivate(APA, ApplyPowerAction.class, "powerToApply")).type
                            .equals(AbstractPower.PowerType.DEBUFF)) {
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, CardCrawlGame.languagePack.getUIString("ApplyPowerAction").TEXT[1]));
                ModHelper.APAcancel(APA);
                return SpireReturn.Return();
            }

            if (APA.target.hasPower(VoidPower.POWER_ID) &&
                    powerToApply.equals(StrengthPower.POWER_ID) && APA.amount > 0) {
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[5]));
                ModHelper.APAcancel(APA);
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }

        public static SpireReturn<Void> KongBu(ApplyPowerAction APA, boolean isPlayer) {
            if(APA.target == AbstractDungeon.player && APA.target.hasPower(AnYanHuoJuPower.POWER_ID)){
                APA.target.getPower(AnYanHuoJuPower.POWER_ID).flash();
                AbstractDungeon.getCurrRoom().monsters.monsters.forEach(mo -> {
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(mo, HWEITEXT[2]));
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, APA.source,
                            new VulnerablePower(APA.target, 1, !isPlayer)));
                });
            } else {
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[2]));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(APA.target, APA.source,
                        new VulnerablePower(APA.target, 1, !isPlayer)));
            }
            return SpireReturn.Return();
        }

        public static SpireReturn<Void> ZhenYa(ApplyPowerAction APA, boolean isPlayer) {
            if(APA.target == AbstractDungeon.player && APA.target.hasPower(AnYanHuoJuPower.POWER_ID)){
                APA.target.getPower(AnYanHuoJuPower.POWER_ID).flash();
                AbstractDungeon.getCurrRoom().monsters.monsters.forEach(mo -> {
                    AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(mo, HWEITEXT[2]));
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, APA.source,
                            new WeakPower(APA.target, 1, !isPlayer)));
                });
            } else {
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(APA.target, HWEITEXT[3]));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(APA.target, APA.source,
                        new WeakPower(APA.target, 1, !isPlayer)));
            }
            return SpireReturn.Return();
        }
    }
}
