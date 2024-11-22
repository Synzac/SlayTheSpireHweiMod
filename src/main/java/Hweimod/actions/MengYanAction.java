package Hweimod.actions;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modifier.SignatureModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class MengYanAction extends AbstractGameAction {

    public MengYanAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        ArrayList<AbstractCard> groupCopy = new ArrayList<>(AbstractDungeon.player.hand.group);
        AbstractCard c = null;
        for (int i = 0; i < this.amount; ++i) {
            c = null;
            if (!groupCopy.isEmpty()) {
                c = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }
            if (c != null) {
                groupCopy.remove(c);
                c.setCostForTurn(0);
                CardModifierManager.addModifier(c, new SignatureModifier(HweiCardTagsEnum.SIGNATURE_TORMENT));
            }
        }
        this.isDone = true;
    }
}
