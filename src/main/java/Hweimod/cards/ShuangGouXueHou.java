package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

public class ShuangGouXueHou extends MouldCard {
    public static final String ID = ModHelper.makePath(ShuangGouXueHou.class.getSimpleName());

    public ShuangGouXueHou() {
        super(ShuangGouXueHou.class.getSimpleName(), 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 15;
        this.damageTypeForTurn = this.damageType = HweiDamageTypeEnum.MAGIC;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_TORMENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.PURPLE, Color.BLACK), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.NONE));
        if (m != null) {
            addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.PURPLE, Color.BLACK), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, HweiDamageTypeEnum.MAGIC), AbstractGameAction.AttackEffect.NONE));
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShuangGouXueHou();
    }
}
