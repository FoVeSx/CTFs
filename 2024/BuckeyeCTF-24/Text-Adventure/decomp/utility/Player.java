/*    */ package utility;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Player {
/*  6 */   public static Player instance = new Player();
/*    */   private static Set<String> Inventory;
/*    */   
/*    */   private Player() {
/* 10 */     Inventory = new HashSet<>();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void equipItem(String item) {
/* 16 */     if (Inventory.contains(item)) {
/* 17 */       System.out.println("Already have one of those!");
/*    */     } else {
/* 19 */       System.out.println("You picked up the " + item + ".");
/* 20 */       Inventory.add(item);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean hasItem(String item) {
/* 25 */     return Inventory.contains(item);
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar\\utility\Player.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */