package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashSet;
import java.util.Set;

public class LianYi extends MouldCard {
    public static final String ID = ModHelper.makePath(LianYi.class.getSimpleName());

    public LianYi(){
        super(LianYi.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 4;
        this.block = this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void applyPowers() {
        int num = 0;
        int a = 3;
        if (this.upgraded)
            a = 4;
        if(!XuanZhiQu.isEmpty()) {
            Set<CardTags> set = new HashSet<>();
            for (AbstractCard c : XuanZhiQu.group){
                if(ModHelper.getSig(c) != null)
                    set.add(ModHelper.getSig(c));
            }
            num += a*set.size();
        }
        this.baseBlock = a + 1 + num;
        this.baseDamage = a + 1 + num;
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new LianYi();
    }
}
