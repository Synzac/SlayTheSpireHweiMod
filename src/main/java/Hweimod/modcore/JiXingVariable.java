package Hweimod.modcore;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class JiXingVariable extends DynamicVariable {
    @Override
    public String key() {
        return "G";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return card.misc;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.misc;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgraded;
    }
}
