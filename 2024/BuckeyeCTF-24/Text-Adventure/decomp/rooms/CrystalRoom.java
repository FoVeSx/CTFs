/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class CrystalRoom
/*    */   extends Room {
/*    */   private boolean hasRope = true;
/*    */   
/*    */   public CrystalRoom(Room prevRoom) {
/* 10 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 15 */     System.out.println("On the other side of the bridge, you come upon a cavern covered in glistening pink crystals!\nSome are so large you can see your reflection in them as they glisten from your torchlight.\nSome of the crystals look mined away, but you don't see any sort of pickaxe. All that remains of the mining operation is a bundle of rope.");
/*    */ 
/*    */     
/*    */     while (true) {
/* 19 */       String input = getInput(); String str1;
/* 20 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1640073658: if (!str1.equals("grab rope")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */           
/* 25 */           if (this.hasRope) {
/* 26 */             Player.instance.equipItem("rope");
/* 27 */             this.hasRope = false; continue;
/*    */           } 
/* 29 */           System.out.println("You already did that."); continue;case -1063409950: if (!str1.equals("equip rope")) break;  if (this.hasRope) { Player.instance.equipItem("rope"); this.hasRope = false; continue; }  System.out.println("You already did that."); continue;case -889407912: if (!str1.equals("pick up rope")) break;  if (this.hasRope) { Player.instance.equipItem("rope"); this.hasRope = false; continue; }  System.out.println("You already did that.");
/*    */           continue;
/*    */         case 102846135:
/*    */           if (!str1.equals("leave"))
/*    */             break; 
/* 34 */           System.out.println("You brave the bridge once again...");
/* 35 */           System.out.println("...and again, safely make it across. Surefooted as they come.");
/* 36 */           this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You brave the bridge once again..."); System.out.println("...and again, safely make it across. Surefooted as they come."); this.previousRoom.enter(); continue;
/*    */         case 1440807147:
/*    */           if (!str1.equals("take rope"))
/* 39 */             break;  if (this.hasRope) { Player.instance.equipItem("rope"); this.hasRope = false; continue; }  System.out.println("You already did that."); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\CrystalRoom.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */