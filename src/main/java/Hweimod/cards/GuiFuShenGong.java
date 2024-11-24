package Hweimod.cards;

import Hweimod.actions.GuiFuShenGongAction;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class GuiFuShenGong extends MouldCard {
    public static final String ID = ModHelper.makePath(GuiFuShenGong.class.getSimpleName());

    public GuiFuShenGong(){
        super(GuiFuShenGong.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GuiFuShenGongAction());

        signature(p, p);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new GuiFuShenGong();
    }

}
