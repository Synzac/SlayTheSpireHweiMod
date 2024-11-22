package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

import java.util.HashSet;
import java.util.Set;

public class LingGuangZhaXian extends MouldCard {
    public static final String ID = ModHelper.makePath(LingGuangZhaXian.class.getSimpleName());

    public LingGuangZhaXian() {
        super(LingGuangZhaXian.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = 2;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster monster;
        Set<AbstractMonster> set = new HashSet<>();
        for (int i = 0; i < 2; ++i){
            monster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (monster != null) {
                this.calculateCardDamage(monster);
                addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                set.add(monster);
            }
        }
        set.forEach(mo -> signature(p, mo));
    }

    @Override
    public void triggerWhenDrawn() {
        this.tags.remove(ModHelper.getSig(this));
        this.flash();
        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 30) {
            this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
        } else if (roll < 60) {
            this.tags.add(HweiCardTagsEnum.SIGNATURE_SERENITY);
        } else if (roll < 90) {
            this.tags.add(HweiCardTagsEnum.SIGNATURE_TORMENT);
        } else {
            this.tags.add(HweiCardTagsEnum.SIGNATURE_DESPAIR);
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            this.tags.add(HweiCardTagsEnum.HANG);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LingGuangZhaXian();
    }
}
