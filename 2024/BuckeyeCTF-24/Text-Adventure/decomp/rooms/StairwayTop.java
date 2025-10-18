/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class StairwayTop extends Room {
/*  6 */   private Room stairwayBottom = new StairwayBottom(this);
/*    */   
/*    */   public StairwayTop(Room prevRoom) {
/*  9 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 14 */     if (!Player.instance.hasItem("torch")) {
/* 15 */       darkRoom();
/*    */     } else {
/* 17 */       System.out.println("You find yourself at the top of a long stair descending downward. You cannot make out the bottom.");
/* 18 */       System.out.println("Behind you lies the great hall you first entered through.");
/*    */       while (true) {
/* 20 */         String input = getInput(); String str1;
/* 21 */         switch ((str1 = input.toLowerCase()).hashCode()) { case 102846135: if (!str1.equals("leave")) {
/*    */               break;
/*    */             }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 31 */             System.out.println("You exit back into the hall you came through.");
/* 32 */             this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You exit back into the hall you came through."); this.previousRoom.enter(); continue;case 134076634: if (!str1.equals("go down")) break;  System.out.println("You muster all of your courage and wander down into the depths..."); this.stairwayBottom.enter(); continue;case 134182001: if (!str1.equals("go hall")) break;  System.out.println("You exit back into the hall you came through."); this.previousRoom.enter(); continue;case 1556853930: if (!str1.equals("descend")) break;  System.out.println("You muster all of your courage and wander down into the depths..."); this.stairwayBottom.enter(); continue;case 1797592917: if (!str1.equals("go to the hall")) break;  System.out.println("You exit back into the hall you came through."); this.previousRoom.enter();
/*    */             continue; }
/*    */         
/* 35 */         System.out.println("Can't do that.");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\StairwayTop.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */