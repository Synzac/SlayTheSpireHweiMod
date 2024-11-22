package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Objects;

public class MangMangJiaoTuAction extends AbstractGameAction{

    public MangMangJiaoTuAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (MouldCard.XuanZhiQu.isEmpty()){
            this.isDone = true;
            return;
        }
        AbstractCard card = MouldCard.XuanZhiQu.getTopCard();
        if(Objects.equals(ModHelper.getSig(card), HweiCardTagsEnum.SIGNATURE_SERENITY)){
            addToTop(new GainEnergyAction(1));
            addToTop(new OptionalQuXiaAction(false, false, false));
            this.isDone = true;
            return;
        } else {
            addToTop(new MangMangJiaoTuAction());
            addToTop(new GainEnergyAction(1));
            addToTop(new OptionalQuXiaAction(false, false, false, HweiCardTagsEnum.SIGNATURE_SERENITY));
        }
        this.isDone = true;
    }
}
