package Hweimod.potion;

import Hweimod.helpers.ModHelper;
import Hweimod.powers.APPower;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Elixir_of_Sorcery extends CustomPotion {
    public static final String ID = ModHelper.makePath(Elixir_of_Sorcery.class.getSimpleName());

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);

    private static final AbstractPotion.PotionRarity rarity = PotionRarity.COMMON;

    private static final AbstractPotion.PotionSize size = PotionSize.M;

    private static final AbstractPotion.PotionColor color = AbstractPotion.PotionColor.WEAK;

    public Elixir_of_Sorcery() {
        super(potionStrings.NAME, ID, rarity, size, color);
        this.isThrown = true;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(potionStrings.DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        AbstractPlayer abstractPlayer = AbstractDungeon.player;
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ApplyPowerAction(abstractPlayer, AbstractDungeon.player, new APPower(abstractPlayer, this.potency), this.potency));
    }

    @Override
    public int getPotency(int i) {
        return 2;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Elixir_of_Sorcery();
    }
}
