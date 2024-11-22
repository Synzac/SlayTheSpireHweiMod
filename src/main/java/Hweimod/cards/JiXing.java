package Hweimod.cards;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.powers.Subject_TormentPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

import static java.lang.Math.round;

public class JiXing extends MouldCard {
    public static final String ID = ModHelper.makePath(JiXing.class.getSimpleName());

    public JiXing(){
        super(JiXing.class.getSimpleName(), -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.misc = 0;
        this.magicNumber = this.baseMagicNumber = 32;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = "这张牌不能被打出";
        return false;
    }

    @Override
    public void tookDamage() {
        if (XuanZhiQu.group.stream().anyMatch(card -> card.uuid == this.uuid)) {
            AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.uuid.equals(this.uuid)).forEach(c -> {
                c.misc += this.magicNumber;
                c.initializeDescription();
            });
            GetAllInBattleInstances.get(this.uuid).forEach(c -> {
                c.misc += this.magicNumber;
                c.initializeDescription();
            });
            this.baseMagicNumber /= 2;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(8);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onRemoveFromMasterDeck() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.misc));
        p.gainGold(this.misc);
        if(!this.upgraded)
            p.increaseMaxHp((int)round(0.3*(p.maxHealth-p.currentHealth)), true);
        else
            p.increaseMaxHp((int)round(0.4*(p.maxHealth-p.currentHealth)), true);
    }

    @Override
    public AbstractCard makeCopy(){
        return new JiXing();
    }
}
