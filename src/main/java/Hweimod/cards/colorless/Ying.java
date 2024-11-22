package Hweimod.cards.colorless;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static java.lang.Math.max;

@AutoAdd.Ignore
public class Ying extends MouldCard {
    public static final String ID = ModHelper.makePath(Ying.class.getSimpleName());

    public Ying(){
        super(Ying.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(HweiCardTagsEnum.HANG);
        this.tags.add(HweiCardTagsEnum.SHIELD);
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
    }

    public Ying(int D, int B){
        this();
        this.damage = this.baseDamage = max(D, 0);
        this.block = this.baseBlock = max(B, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new GainBlockAction(p, this.block));
        signature(p, m);
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new Ying();
    }
}
