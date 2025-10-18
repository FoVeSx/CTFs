/*    */ package rooms;
/*    */ 
/*    */ import utility.MagicOrb;
/*    */ 
/*    */ public class DeadEnd extends Room {
/*  6 */   private MagicOrb flag = new MagicOrb();
/*    */   
/*    */   public DeadEnd(Room prevRoom) {
/*  9 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 14 */     System.out.println("It appears to be a dead end.");
/*    */     while (true) {
/* 16 */       String input = getInput(); String str1;
/* 17 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1388976959: if (!str1.equals("reach through the crack in the rocks")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 24 */           System.out.println("What? What crack in the rocks?");
/* 25 */           input = getInput();
/* 26 */           if (input.equals("the crack in the rocks concealing the magical orb with the flag"))
/* 27 */           { System.out.println("There's a crack in the --? Well, it seems you know more about this world than I do. Happy hacking!");
/*    */             
/* 29 */             try { this.flag.printFlag(); }
/* 30 */             catch (Exception e)
/* 31 */             { System.out.println("Hmm.... it seems the magical orb has decided to give you nothing. How strange."); }  }  continue;
/*    */         case 102846135: if (!str1.equals("leave"))
/*    */             break;  System.out.println("You return back to the room you came through."); this.previousRoom.enter(); continue;
/*    */         case 134002975:
/*    */           if (!str1.equals("go back"))
/* 36 */             break;  System.out.println("You return back to the room you came through."); this.previousRoom.enter(); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\DeadEnd.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */