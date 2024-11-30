package Hweimod.modcore;

import Hweimod.cards.FaLiLiuXiDai;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FaLiVariable2 extends DynamicVariable {
    @Override
    public String key() {
        return "F2";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if(card.upgraded){
            return FaLiLiuXiDai.getMisc2(card.misc);
        } else {
            return FaLiLiuXiDai.getMisc2(card.misc);
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if(card.upgraded){
            return FaLiLiuXiDai.getMisc2(card.misc);
        }
        else {
            return FaLiLiuXiDai.getMisc2(card.misc);
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgraded;
    }
}
