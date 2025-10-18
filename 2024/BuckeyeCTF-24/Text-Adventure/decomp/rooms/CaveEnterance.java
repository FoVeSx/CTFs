/*    */ package rooms;
/*    */ 
/*    */ public class CaveEnterance extends Room {
/*  4 */   Room entryHall = new EntryHall(this);
/*    */ 
/*    */   
/*    */   public void enter() {
/*  8 */     System.out.println("You find yourself standing at the opening of a vast, mysterious cave. The entrance looms before you, awaiting.");
/*    */     
/*    */     while (true) {
/* 11 */       String input = getInput(); String str1;
/* 12 */       switch ((str1 = input.toLowerCase()).hashCode()) { case 96667352: if (!str1.equals("enter")) {
/*    */             break;
/*    */           }
/* 15 */           System.out.println("You feel the air grow cool as you pass through the threshold into the depths...");
/* 16 */           this.entryHall.enter(); continue;case 98463581: if (!str1.equals("go in")) break;  System.out.println("You feel the air grow cool as you pass through the threshold into the depths..."); this.entryHall.enter(); continue;
/*    */         case 102846135:
/*    */           if (!str1.equals("leave"))
/*    */             break; 
/* 20 */           System.out.println("Go back? GO BACK? You cannot go back. Your fate is fixed; you have no choice."); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("Go back? GO BACK? You cannot go back. Your fate is fixed; you have no choice.");
/*    */           continue; }
/*    */       
/* 23 */       System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\CaveEnterance.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */