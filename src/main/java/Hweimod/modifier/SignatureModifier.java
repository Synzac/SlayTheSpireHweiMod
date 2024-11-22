package Hweimod.modifier;

import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.InkPower;
import Hweimod.powers.QunYaFengBaoPower;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SignatureModifier extends AbstractCardModifier {
    private AbstractCard.CardTags Signature;

    public static String ID = ModHelper.makePath(SignatureModifier.class.getSimpleName());

    public SignatureModifier(AbstractCard.CardTags signature){
        this.Signature = signature;
    }

    public boolean shouldApply(AbstractCard card){return !card.hasTag(Signature);}

    public void onInitialApplication(AbstractCard card){
        AbstractCard.CardTags tag = ModHelper.getSig(card);
        if (tag != this.Signature) {
            if (AbstractDungeon.player.hasPower(QunYaFengBaoPower.POWER_ID)) {
                AbstractDungeon.player.getPower(QunYaFengBaoPower.POWER_ID).flash();
                addToBot(new DrawCardAction(AbstractDungeon.player.getPower(QunYaFengBaoPower.POWER_ID).amount));
                if (this.Signature == HweiCardTagsEnum.SIGNATURE_TORMENT)
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new InkPower(AbstractDungeon.player, 1)));
            }
            if(tag != null)
                card.tags.remove(tag);
            card.tags.add(Signature);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SignatureModifier(Signature);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
