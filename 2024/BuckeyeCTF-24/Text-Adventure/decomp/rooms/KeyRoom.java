/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class KeyRoom
/*    */   extends Room {
/*    */   private boolean hasKey = true;
/*    */   
/*    */   public KeyRoom(Room prevRoom) {
/* 10 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 15 */     if (this.hasKey) {
/* 16 */       System.out.println("You come in to a small room with a glowing pedestal in the center.\nThe light is dazzling, and upon the pedestal lies an ornate key. Neat!");
/*    */     } else {
/*    */       
/* 19 */       System.out.println("You come in to a small room with a glowing pedestal in the center.\nYou already picked up the ornate key, but the light is still beautiful.");
/*    */     } 
/*    */     
/*    */     while (true) {
/* 23 */       String input = getInput(); String str1;
/* 24 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -2106907591: if (!str1.equals("pick up key")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */           
/* 29 */           if (this.hasKey) {
/* 30 */             System.out.println("You slowly reach out your hand, wary of any traps you might spring, or eyes that might be watching...");
/* 31 */             System.out.println("...but there aren't any. Easy, right?");
/* 32 */             Player.instance.equipItem("key");
/* 33 */             this.hasKey = false; continue;
/*    */           } 
/* 35 */           System.out.println("You already did that."); continue;case -646266042: if (!str1.equals("take key")) break;  if (this.hasKey) { System.out.println("You slowly reach out your hand, wary of any traps you might spring, or eyes that might be watching..."); System.out.println("...but there aren't any. Easy, right?"); Player.instance.equipItem("key"); this.hasKey = false; continue; }  System.out.println("You already did that."); continue;case 85634699: if (!str1.equals("grab key")) break;  if (this.hasKey) { System.out.println("You slowly reach out your hand, wary of any traps you might spring, or eyes that might be watching..."); System.out.println("...but there aren't any. Easy, right?"); Player.instance.equipItem("key"); this.hasKey = false; continue; }  System.out.println("You already did that.");
/*    */           continue;
/*    */         case 102846135:
/*    */           if (!str1.equals("leave"))
/*    */             break; 
/* 40 */           System.out.println("You exit back into the hall of spiders.");
/* 41 */           this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You exit back into the hall of spiders."); this.previousRoom.enter(); continue;
/*    */         case 1074068079:
/*    */           if (!str1.equals("equip key"))
/* 44 */             break;  if (this.hasKey) { System.out.println("You slowly reach out your hand, wary of any traps you might spring, or eyes that might be watching..."); System.out.println("...but there aren't any. Easy, right?"); Player.instance.equipItem("key"); this.hasKey = false; continue; }  System.out.println("You already did that."); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\KeyRoom.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */