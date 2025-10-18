/*    */ package rooms;
/*    */ 
/*    */ public class StairwayBottom extends Room {
/*  4 */   private Room sealedDoor = new SealedDoor(this);
/*  5 */   private Room river = new River(this);
/*    */   
/*    */   public StairwayBottom(Room prevRoom) {
/*  8 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 13 */     System.out.println("You are at the base of the stairway. Two paths lay before you, one left and one right.\nYou hear the sound of rushing water coming from the right passageway.");
/*    */     
/*    */     while (true) {
/* 16 */       String input = getInput(); String str1;
/* 17 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1654690961: if (!str1.equals("up stairs")) {
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
/* 33 */           System.out.println("You go up the steps...");
/* 34 */           this.previousRoom.enter(); continue;case -1408684854: if (!str1.equals("ascend")) break;  System.out.println("You go up the steps..."); this.previousRoom.enter(); continue;case -125856540: if (!str1.equals("go right")) break;  System.out.println("You head into the right passageway..."); this.river.enter(); continue;case 3317767: if (!str1.equals("left")) break;  System.out.println("You head into the left passageway..."); this.sealedDoor.enter(); continue;case 98463955: if (!str1.equals("go up")) break;  System.out.println("You go up the steps..."); this.previousRoom.enter(); continue;case 102846135: if (!str1.equals("leave")) break;  System.out.println("You go up the steps..."); this.previousRoom.enter(); continue;case 108511772: if (!str1.equals("right")) break;  System.out.println("You head into the right passageway..."); this.river.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You go up the steps..."); this.previousRoom.enter(); continue;
/*    */         case 134304831:
/*    */           if (!str1.equals("go left"))
/* 37 */             break;  System.out.println("You head into the left passageway..."); this.sealedDoor.enter(); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\StairwayBottom.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */