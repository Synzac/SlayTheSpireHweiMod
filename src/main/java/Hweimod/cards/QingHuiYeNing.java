package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiDamageTypeEnum;
import Hweimod.powers.MoonlightPower;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import java.util.ArrayList;

public class QingHuiYeNing extends MouldCard {
    public static final String ID = ModHelper.makePath(QingHuiYeNing.class.getSimpleName());

    public QingHuiYeNing(){
        super(QingHuiYeNing.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 10;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*if(this.upgraded)
            CardCrawlGame.sound.play("QingHuiYeNing");
        */
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(m, p, new MoonlightPower(m)));

        ArrayList<AbstractCard> derp = new ArrayList<>();
        while (derp.size() < this.magicNumber) {
            AbstractCard.CardRarity cardRarity;
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(99);
            if (roll < 50) {
                cardRarity = AbstractCard.CardRarity.COMMON;
            } else if (roll < 80) {
                cardRarity = AbstractCard.CardRarity.UNCOMMON;
            } else {
                cardRarity = AbstractCard.CardRarity.RARE;
            }
            AbstractCard card = CardLibrary.getAnyColorCard(AbstractCard.CardType.ATTACK, cardRarity);
            for (AbstractCard c : derp) {
                if (c.color.equals(card.color) || card.cardID.equals(QingHuiYeNing.ID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe)
                derp.add(card.makeCopy());
        }
        for (AbstractCard c : derp){
            CardModifierManager.addModifier(c, new EtherealMod());
            addToBot(new MakeTempCardInHandAction(c, 1));
        }

        addToBot(new ApplyPowerAction(p, p, new FreeAttackPower(p, 1), 1));

        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new QingHuiYeNing();
    }
}
