/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class River extends Room {
/*  6 */   private Room acrossRiver = new AcrossRiver(this);
/*    */   
/*    */   public River(Room prevRoom) {
/*  9 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 14 */     System.out.println("You find yourself alongside a great rushing underground river!\nThe remains of a broken bridge lie torn and rotted. You'll have to find some other way to cross.\nThere's a great root of some tree sticking out from the ceiling, but it's too high for you to reach.\nThe base of the steps lie behind you.");
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/* 19 */       String input = getInput(); String str1;
/* 20 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1906921030: if (!str1.equals("use the rope")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 27 */           if (Player.instance.hasItem("rope")) {
/* 28 */             System.out.println("You throw with all your might, and the rope catches on the root! You swing across safely.");
/* 29 */             this.acrossRiver.enter(); continue;
/*    */           } 
/* 31 */           System.out.println("Hmm, that root might hold your weight... if only you had some rope."); continue;case -1902974149: if (!str1.equals("throw the rope")) break;  if (Player.instance.hasItem("rope")) { System.out.println("You throw with all your might, and the rope catches on the root! You swing across safely."); this.acrossRiver.enter(); continue; }  System.out.println("Hmm, that root might hold your weight... if only you had some rope."); continue;case -1506632735: if (!str1.equals("swing across")) break;  if (Player.instance.hasItem("rope")) { System.out.println("You throw with all your might, and the rope catches on the root! You swing across safely."); this.acrossRiver.enter(); continue; }  System.out.println("Hmm, that root might hold your weight... if only you had some rope."); continue;case -341309461: if (!str1.equals("use rope")) break;  if (Player.instance.hasItem("rope")) { System.out.println("You throw with all your might, and the rope catches on the root! You swing across safely."); this.acrossRiver.enter(); continue; }  System.out.println("Hmm, that root might hold your weight... if only you had some rope."); continue;case -331929108: if (!str1.equals("throw rope")) break;  if (Player.instance.hasItem("rope")) { System.out.println("You throw with all your might, and the rope catches on the root! You swing across safely."); this.acrossRiver.enter(); continue; }  System.out.println("Hmm, that root might hold your weight... if only you had some rope.");
/*    */           continue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         case -124606993:
/*    */           if (!str1.equals("go steps")) {
/*    */             break;
/*    */           }
/* 45 */           System.out.println("You return to the base of the steps.");
/* 46 */           this.previousRoom.enter(); continue;case -98476149: if (!str1.equals("go to the steps")) break;  System.out.println("You return to the base of the steps."); this.previousRoom.enter(); continue;case 3083764: if (!str1.equals("dive")) break;  System.out.println("The water's moving too fast for you to swim across."); continue;case 3273774: if (!str1.equals("jump")) break;  System.out.println("Too far to jump."); continue;case 3543688: if (!str1.equals("swim")) break;  System.out.println("The water's moving too fast for you to swim across."); continue;case 102846135: if (!str1.equals("leave")) break;  System.out.println("You return to the base of the steps."); this.previousRoom.enter(); continue;case 109854462: if (!str1.equals("swing")) break;  if (Player.instance.hasItem("rope")) { System.out.println("You throw with all your might, and the rope catches on the root! You swing across safely."); this.acrossRiver.enter(); continue; }  System.out.println("Hmm, that root might hold your weight... if only you had some rope."); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You return to the base of the steps."); this.previousRoom.enter();
/*    */           continue; }
/*    */       
/* 49 */       System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\River.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */