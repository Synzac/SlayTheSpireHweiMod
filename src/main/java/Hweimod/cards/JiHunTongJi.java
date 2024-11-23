package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class JiHunTongJi extends MouldCard {
    public static final String ID = ModHelper.makePath(JiHunTongJi.class.getSimpleName());

    public JiHunTongJi(){
        super(JiHunTongJi.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(HweiCardTagsEnum.SIGNATURE_DISASTER);
        this.exhaust = true;
    }

    @Override
    public void didDiscard() {
        this.baseDamage += this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.damage < 50)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        else {
            if(m != null) {
                //CardCrawlGame.sound.play("JiHunTongJi");
                addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            }
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.NONE));
        }
        signature(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new JiHunTongJi();
    }
}
