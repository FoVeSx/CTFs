/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class Bridge extends Room {
/*  6 */   private Room crystalRoom = new CrystalRoom(this);
/*    */   
/*    */   public Bridge(Room prevRoom) {
/*  9 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 14 */     if (!Player.instance.hasItem("torch")) {
/* 15 */       darkRoom();
/*    */     } else {
/* 17 */       System.out.println("You find yourself standing at the edge of an unfathomable chasm! Far off, you can hear fast running water below.\nThere lies a stone bridge spanning the gap, but it's little more than a few feet wide. It arches away from you, and disappears into the darkness.\nThe main hall lies behind you.");
/*    */ 
/*    */       
/*    */       while (true) {
/* 21 */         String input = getInput(); String str1;
/* 22 */         switch ((str1 = input.toLowerCase()).hashCode()) { case -1746062856: if (!str1.equals("cross the bridge")) {
/*    */               break;
/*    */             }
/*    */ 
/*    */             
/* 27 */             System.out.println("You slowly edge out on to the bridge... holding your breath...");
/* 28 */             System.out.println("...and eventually make it to the other side. Uh, good job.");
/* 29 */             this.crystalRoom.enter();
/*    */             continue;
/*    */ 
/*    */           
/*    */           case -341309461:
/*    */             if (!str1.equals("use rope")) {
/*    */               break;
/*    */             }
/* 37 */             System.out.println("The rope isn't long enough to get you to the bottom of the chasm."); continue;case -256874883: if (!str1.equals("jump off")) break;  System.out.println("You wouldn't survive a fall from this height."); continue;case -98487625: if (!str1.equals("go across")) break;  System.out.println("You slowly edge out on to the bridge... holding your breath..."); System.out.println("...and eventually make it to the other side. Uh, good job."); this.crystalRoom.enter(); continue;case 3273774: if (!str1.equals("jump")) break;  System.out.println("You wouldn't survive a fall from this height."); continue;case 3506418: if (!str1.equals("rope")) break;  System.out.println("The rope isn't long enough to get you to the bottom of the chasm."); continue;
/*    */           case 94935104: if (!str1.equals("cross"))
/*    */               break;  System.out.println("You slowly edge out on to the bridge... holding your breath..."); System.out.println("...and eventually make it to the other side. Uh, good job."); this.crystalRoom.enter(); continue;
/*    */           case 102080182: if (!str1.equals("walk across"))
/*    */               break;  System.out.println("You slowly edge out on to the bridge... holding your breath..."); System.out.println("...and eventually make it to the other side. Uh, good job."); this.crystalRoom.enter(); continue;
/*    */           case 102846135: if (!str1.equals("leave"))
/* 43 */               break;  System.out.println("You return to the hall you entered through.");
/* 44 */             this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You return to the hall you entered through."); this.previousRoom.enter(); continue;case 134182001: if (!str1.equals("go hall")) break;  System.out.println("You return to the hall you entered through."); this.previousRoom.enter(); continue;case 1797592917: if (!str1.equals("go to the hall")) break;  System.out.println("You return to the hall you entered through."); this.previousRoom.enter();
/*    */             continue; }
/*    */         
/* 47 */         System.out.println("Can't do that.");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\Bridge.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */