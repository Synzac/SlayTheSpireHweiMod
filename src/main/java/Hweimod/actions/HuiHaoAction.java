package Hweimod.actions;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modifier.SignatureModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.util.Objects;

public class HuiHaoAction extends AbstractGameAction {

    private AbstractCard card;

    public HuiHaoAction(AbstractCard card){
        this.card = card;
    }

    @Override
    public void update() {
        if(MouldCard.XuanZhiQu.isEmpty()){
            this.isDone = true;
            return;
        }
        AbstractCard.CardTags tag = ModHelper.getSig(MouldCard.XuanZhiQu.getTopCard());
        if(!Objects.equals(ModHelper.getSig(card), tag)){
            CardModifierManager.addModifier(card, new SignatureModifier(tag));
        }
        this.isDone = true;
    }
}
