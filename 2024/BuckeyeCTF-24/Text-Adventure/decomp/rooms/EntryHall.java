/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class EntryHall extends Room {
/*  6 */   Room spiderHall = new SpiderHallway(this);
/*  7 */   Room stairway = new StairwayTop(this);
/*  8 */   Room bridge = new Bridge(this);
/*    */   
/*    */   boolean hasTorch = true;
/*    */   
/*    */   public EntryHall(Room previousRoom) {
/* 13 */     this.previousRoom = previousRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 18 */     System.out.print("You find yourself in a central hall.");
/* 19 */     if (this.hasTorch) {
/* 20 */       System.out.print(" It is faintly lit by a torch on the leftmost wall.");
/*    */     }
/* 22 */     System.out.println("\nThrough the gloom you barely make out three arches to ongoing passages: one left, one middle, and one right.");
/*    */     
/*    */     while (true) {
/* 25 */       String input = getInput(); String str1;
/* 26 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1799992490: if (!str1.equals("pick up torch")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */           
/* 31 */           if (this.hasTorch) {
/* 32 */             Player.instance.equipItem("torch");
/* 33 */             this.hasTorch = false; continue;
/*    */           } 
/* 35 */           System.out.println("You already picked that up.");
/*    */           continue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         case -125856540:
/*    */           if (!str1.equals("go right")) {
/*    */             break;
/*    */           }
/* 48 */           System.out.println("You pass through the right corridor...");
/* 49 */           this.bridge.enter(); continue;case -39497075: if (!str1.equals("go center"))
/*    */             break;  System.out.println("You pass through the middle corridor..."); this.stairway.enter(); continue;
/*    */         case 134002975: if (!str1.equals("go back"))
/* 52 */             break;  System.out.println("You exit back into the light of day beyond the cave.");
/* 53 */           this.previousRoom.enter(); continue;case 134304831: if (!str1.equals("go left")) break;  System.out.println("You pass through the left corridor..."); this.spiderHall.enter(); continue;case 250175437: if (!str1.equals("go middle")) break;  System.out.println("You pass through the middle corridor..."); this.stairway.enter(); continue;case 699173160: if (!str1.equals("grab torch"))
/*    */             break;  if (this.hasTorch) { Player.instance.equipItem("torch"); this.hasTorch = false; continue; }  System.out.println("You already picked that up."); continue;case 1395878924: if (!str1.equals("equip torch"))
/*    */             break;  if (this.hasTorch) { Player.instance.equipItem("torch"); this.hasTorch = false; continue; }  System.out.println("You already picked that up."); continue;case 1717197603: if (!str1.equals("take torch"))
/* 56 */             break;  if (this.hasTorch) { Player.instance.equipItem("torch"); this.hasTorch = false; continue; }  System.out.println("You already picked that up."); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\EntryHall.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */