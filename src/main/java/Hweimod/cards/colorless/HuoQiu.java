package Hweimod.cards.colorless;

import Hweimod.cards.MieShiZheDeSiWangZhiMao;
import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class HuoQiu extends MouldCard {
    public static final String ID = ModHelper.makePath(HuoQiu.class.getSimpleName());

    public HuoQiu(){
        super(HuoQiu.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        this.damage = this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 2;
        this.isEthereal = true;
        this.tags.add(HweiCardTagsEnum.HANG);
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.FIRE));

        this.baseDamage += this.magicNumber;
        this.applyPowers();
        AbstractDungeon.player.discardPile.group.stream().filter(c -> c instanceof HuoQiu).forEach(c -> {
            c.baseDamage += this.magicNumber;
            c.applyPowers();
        });
        AbstractDungeon.player.drawPile.group.stream().filter(c -> c instanceof HuoQiu).forEach(c -> {
            c.baseDamage += this.magicNumber;
            c.applyPowers();
        });
        AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof HuoQiu).forEach(c -> {
            c.baseDamage += this.magicNumber;
            c.applyPowers();
        });
        XuanZhiQu.group.stream().filter(c -> c instanceof HuoQiu).forEach(c -> {
            c.baseDamage += this.magicNumber;
            c.applyPowers();
        });

        signature(p, m);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new HuoQiu();
    }
}
