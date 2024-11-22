package Hweimod.actions;

import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.cards.purple.ThirdEye;
import com.megacrit.cardcrawl.cards.red.Warcry;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MengLongAction extends AbstractGameAction {

    public MengLongAction() {
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.duration = 0.0F;
    }

    public void update() {
        Set<AbstractCard.CardTags> set = DrawCardAction.drawnCards.stream()
                .filter(c -> ModHelper.getSig(c) != null)
                .map(ModHelper::getSig)
                .collect(Collectors.toSet());
        addToTop(new GainEnergyAction(set.size()));
        this.isDone = true;
    }
}
