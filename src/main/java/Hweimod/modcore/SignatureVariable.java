package Hweimod.modcore;

import Hweimod.modifier.SignatureModifier;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SignatureVariable extends DynamicVariable {
    @Override
    public String key() {
        return "S";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return CardModifierManager.hasModifier(card, SignatureModifier.ID);
        // Set to true if the value is modified from the base value.
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        // Do something such that isModified will return the value v.
        // This method is only necessary if you want smith upgrade previews to function correctly.
    }

    @Override
    public int value(AbstractCard card) {
        if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)) return 1;
        else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_SERENITY)) return 2;
        else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_TORMENT)) return 3;
        else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DESPAIR)) return 4;
        else return 0;
        // What the dynamic variable will be set to on your card. Usually uses some kind of int you store on your card.
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)) return 1;
        else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_SERENITY)) return 2;
        else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_TORMENT)) return 3;
        else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DESPAIR)) return 4;
        else return 0;
        // Should generally just be the above.
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgraded;
        // Should return true if the card was upgraded and the value was changed
    }
}