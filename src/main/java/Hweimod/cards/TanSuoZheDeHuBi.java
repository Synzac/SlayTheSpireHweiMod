package Hweimod.cards;

import Hweimod.cards.colorless.SuiLieDeHuBi;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.powers.StasisPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TanSuoZheDeHuBi extends MouldCard {
    public static final String ID = ModHelper.makePath(TanSuoZheDeHuBi.class.getSimpleName());

    public TanSuoZheDeHuBi(){
        super(TanSuoZheDeHuBi.class.getSimpleName(), 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.cardsToPreview = new SuiLieDeHuBi();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StasisPower(p)));
        addToBot(new MakeTempCardInDrawPileAction(new SuiLieDeHuBi(), 1, false, true));
        addToBot(new PressEndTurnButtonAction());

        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TanSuoZheDeHuBi();
    }
}
