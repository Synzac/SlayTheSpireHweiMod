package Hweimod.modcore;

import Hweimod.cards.FaLiLiuXiDai;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FaLiVariable1 extends DynamicVariable {
    @Override
    public String key() {
        return "F1";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if(card.upgraded){
            return 3 - FaLiLiuXiDai.getMisc1(card.misc);
        }
        else {
            return 4 - FaLiLiuXiDai.getMisc1(card.misc);
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if(card.upgraded){
            return 3 - FaLiLiuXiDai.getMisc1(card.misc);
        }
        else {
            return 4 - FaLiLiuXiDai.getMisc1(card.misc);
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgraded;
    }
}
