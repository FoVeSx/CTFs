/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class AcrossRiver
/*    */   extends Room {
/*    */   private boolean hasSword = true;
/*    */   
/*    */   public AcrossRiver(Room prevRoom) {
/* 10 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 15 */     if (this.hasSword) {
/* 16 */       System.out.println("It looks like there was once a battle here, long ago. You see the remains of a knight, still clothed in armor. A slightly-rusted sword lies across his lap.");
/*    */     } else {
/* 18 */       System.out.println("It looks like there was once a battle here, long ago. You still the remains of the knight whose sword you took.");
/*    */     } 
/*    */     while (true) {
/* 21 */       String input = getInput(); String str1;
/* 22 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1906921030: if (!str1.equals("use the rope")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 52 */           System.out.println("You throw the rope again, and swing back to the other side.");
/* 53 */           this.previousRoom.enter(); continue;case -1817454439: if (!str1.equals("pick up armor")) break;  System.out.println("You probably won't need that."); continue;case -1800680105: if (!str1.equals("pick up sword")) break;  if (this.hasSword) { System.out.println("Seems a shame to leave a fine sword to rust like that... It would be better off with you."); Player.instance.equipItem("sword"); this.hasSword = false; continue; }  System.out.println("You already did that."); continue;case -1506632735: if (!str1.equals("swing across")) break;  System.out.println("You throw the rope again, and swing back to the other side."); this.previousRoom.enter(); continue;case -1328913207: if (!str1.equals("swing back")) break;  System.out.println("You throw the rope again, and swing back to the other side."); this.previousRoom.enter(); continue;case -341309461: if (!str1.equals("use rope")) break;  System.out.println("You throw the rope again, and swing back to the other side."); this.previousRoom.enter(); continue;case 3083764: if (!str1.equals("dive")) break;  System.out.println("The water's moving too fast for you to swim across."); continue;case 3543688: if (!str1.equals("swim")) break;  System.out.println("The water's moving too fast for you to swim across."); continue;case 102846135: if (!str1.equals("leave")) break;  System.out.println("You throw the rope again, and swing back to the other side."); this.previousRoom.enter(); continue;case 109854462: if (!str1.equals("swing")) break;  System.out.println("You throw the rope again, and swing back to the other side."); this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You throw the rope again, and swing back to the other side."); this.previousRoom.enter(); continue;case 681711211: if (!str1.equals("grab armor")) break;  System.out.println("You probably won't need that."); continue;case 698485545: if (!str1.equals("grab sword")) break;  if (this.hasSword) { System.out.println("Seems a shame to leave a fine sword to rust like that... It would be better off with you."); Player.instance.equipItem("sword"); this.hasSword = false; continue; }  System.out.println("You already did that."); continue;case 1378416975: if (!str1.equals("equip armor")) break;  System.out.println("You probably won't need that."); continue;case 1395191309: if (!str1.equals("equip sword"))
/*    */             break;  if (this.hasSword) { System.out.println("Seems a shame to leave a fine sword to rust like that... It would be better off with you."); Player.instance.equipItem("sword"); this.hasSword = false; continue; }  System.out.println("You already did that."); continue;case 1699735654: if (!str1.equals("take armor"))
/*    */             break;  System.out.println("You probably won't need that."); continue;case 1716509988: if (!str1.equals("take sword"))
/* 56 */             break;  if (this.hasSword) { System.out.println("Seems a shame to leave a fine sword to rust like that... It would be better off with you."); Player.instance.equipItem("sword"); this.hasSword = false; continue; }  System.out.println("You already did that."); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\AcrossRiver.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */