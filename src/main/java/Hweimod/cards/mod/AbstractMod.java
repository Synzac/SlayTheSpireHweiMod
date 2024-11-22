package Hweimod.cards.mod;

import Hweimod.cards.mould.MouldCard;
import basemod.AutoAdd;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

@AutoAdd.Ignore
public abstract class AbstractMod extends AbstractCardModifier {

    public MouldCard owner;

    public AbstractMod(MouldCard c){
        this.owner = c;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return this.owner.exhaust;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public String identifier(AbstractCard card) {
        return this.owner.cardID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return this;
    }
}
