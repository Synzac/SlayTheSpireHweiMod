package Hweimod.powers;

import Hweimod.cards.mould.MouldCard;
import Hweimod.helpers.ModHelper;
import Hweimod.modcore.HweiCardTagsEnum;
import Hweimod.modcore.HweiDamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class HuaZhongLingPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath(HuaZhongLingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makePowerAd(HuaZhongLingPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makePowerAd(HuaZhongLingPower.class.getSimpleName(), false);

    public AbstractCard card;

    private int turn = 0;
    private int num = 1;
    private static int postfix = 0;

    public HuaZhongLingPower(AbstractCreature owner, AbstractCard card){
        this.name = NAME + card.name;
        this.ID = POWER_ID + postfix++;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.card = card.makeStatEquivalentCopy();
        if(card.rarity == AbstractCard.CardRarity.RARE) num = 3;
        else if (card.rarity == AbstractCard.CardRarity.UNCOMMON) num = 2;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        MouldCard.xuanzhiquReceiveCard(card.makeStatEquivalentCopy());
    }

    @Override
    public void atStartOfTurn() {
        ++turn;
        if ((turn % num) == 0) {
            this.flash();
            MouldCard.xuanzhiquReceiveCard(card.makeStatEquivalentCopy());
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], num-(turn%num)) + this.card.name + String.format(DESCRIPTIONS[1]);
    }
}
