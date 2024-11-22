package Hweimod.actions;

import Hweimod.powers.APPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainShieldAction extends AbstractGameAction {
  private static final float DUR = 0.25F;
  
  public GainShieldAction(AbstractCreature target, int amount) {
    this.target = target;
    this.amount = amount;
    this.actionType = AbstractGameAction.ActionType.BLOCK;
    this.duration = 0.25F;
    this.startDuration = 0.25F;
  }
  
  public GainShieldAction(AbstractCreature target, AbstractCreature source, int amount) {
    setValues(target, source, amount);
    this.actionType = AbstractGameAction.ActionType.BLOCK;
    this.duration = 0.25F;
    this.startDuration = 0.25F;
  }
  
  public GainShieldAction(AbstractCreature target, int amount, boolean superFast) {
    this(target, amount);
    if (superFast)
      this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
  }
  
  public GainShieldAction(AbstractCreature target, AbstractCreature source, int amount, boolean superFast) {
    this(target, source, amount);
    if (superFast)
      this.duration = this.startDuration = Settings.ACTION_DUR_XFAST; 
  }
  
  public void update() {
    if (!this.target.isDying && !this.target.isDead && 
      this.duration == this.startDuration) {

      if(this.target.hasPower(APPower.POWER_ID)){
        this.amount += this.target.getPower(APPower.POWER_ID).amount;
      }

      addToTop(new GainBlockAction(this.target, this.amount));

      for (AbstractCard c : AbstractDungeon.player.hand.group)
        c.applyPowers(); 
    } 
    tickDuration();
    this.isDone = true;
  }
}