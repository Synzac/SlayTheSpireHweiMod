package Hweimod.actions;

import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.Color_DisasterPower;
import Hweimod.powers.Color_SerenityPower;
import Hweimod.powers.Color_TormentPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SignatureAction extends AbstractGameAction {

    private AbstractCard card;
    private AbstractPlayer p;
    private AbstractCreature creature;

    public SignatureAction(AbstractCard card, AbstractPlayer p, AbstractCreature creature){
        this.card = card;
        this.p = p;
        this.creature = creature;
    }

    @Override
    public void update() {
        if(creature != null && !creature.isDead && !creature.halfDead && !creature.isEscaping) {
            if (card.hasTag(HweiCardTagsEnum.SIGNATURE_DISASTER)) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(creature, p, new Color_DisasterPower(creature, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            } else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_SERENITY)) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(creature, p, new Color_SerenityPower(creature, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            } else if (card.hasTag(HweiCardTagsEnum.SIGNATURE_TORMENT)) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(creature, p, new Color_TormentPower(creature, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        this.isDone = true;
    }
}
