/*    */ package rooms;
/*    */ 
/*    */ import java.util.Scanner;
/*    */ 
/*    */ public abstract class Room {
/*  6 */   static Scanner scan = new Scanner(System.in);
/*    */   
/*    */   protected Room previousRoom;
/*    */   
/*    */   public abstract void enter();
/*    */   
/*    */   protected static String getInput() {
/* 13 */     System.out.print("\n> ");
/* 14 */     String in = scan.nextLine();
/* 15 */     System.out.print("\n");
/*    */ 
/*    */     
/* 18 */     if (in.equals("exit")) {
/* 19 */       System.out.println("Okay, goodbye!");
/* 20 */       scan.close();
/* 21 */       System.exit(0);
/*    */     } 
/*    */     
/* 24 */     return in;
/*    */   }
/*    */   
/*    */   protected void darkRoom() {
/* 28 */     System.out.println("It's too dark to see anything!");
/*    */     while (true) {
/* 30 */       String input = getInput(); String str1;
/* 31 */       switch ((str1 = input.toLowerCase()).hashCode()) { case 102846135: if (!str1.equals("leave")) {
/*    */             break;
/*    */           }
/* 34 */           System.out.println("You exit back into the hall you came through.");
/* 35 */           this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You exit back into the hall you came through."); this.previousRoom.enter();
/*    */           continue; }
/*    */       
/* 38 */       System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\Room.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */