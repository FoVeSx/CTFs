/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class SpiderHallway extends Room {
/*  6 */   Room keyRoom = new KeyRoom(this);
/*    */   
/*    */   private boolean websCut = false;
/*    */   
/*    */   public SpiderHallway(Room prevRoom) {
/* 11 */     this.previousRoom = prevRoom;
/*    */   }
/*    */   
/*    */   public void enter() {
/* 15 */     if (!Player.instance.hasItem("torch")) {
/* 16 */       darkRoom();
/*    */     } else {
/* 18 */       if (!this.websCut) {
/* 19 */         System.out.println("You come upon a long hallway, the walls covered by thick webs. Thousands of little legs seem to scurry away from your torch's light.\nYou notice a door at the end of the hall, completely covered in webs. You'll need someting sharp to get cut through it.");
/*    */       } else {
/*    */         
/* 22 */         System.out.println("You return to the long hall, still covered in webs. The door is free, now, though. You feel like hundreds of tiny eyes are watching your every move.\nThe main hall lies behind you.");
/*    */       } 
/*    */       
/*    */       while (true) {
/* 26 */         String input = getInput(); String str1;
/* 27 */         switch ((str1 = input.toLowerCase()).hashCode()) { case -2007890096: if (!str1.equals("burn webs")) {
/*    */               break;
/*    */             }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 49 */             System.out.println("You can't seem to get them to light. Must be some kind of magic..."); continue;case -567175905: if (!str1.equals("burn the webs")) break;  System.out.println("You can't seem to get them to light. Must be some kind of magic..."); continue;
/*    */           case 98882:
/*    */             if (!str1.equals("cut"))
/*    */               break;  if (!this.websCut) { if (Player.instance.hasItem("sword")) { System.out.println("The sword slices right through the webs! You're able to cut away the door and get through."); this.websCut = true; this.keyRoom.enter(); continue; }  System.out.println("With what, your hands?"); continue; }  System.out.println("You already did that."); continue;
/*    */           case 102846135:
/*    */             if (!str1.equals("leave"))
/* 55 */               break;  System.out.println("You exit back into the hall you first entered in.");
/* 56 */             this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You exit back into the hall you first entered in."); this.previousRoom.enter(); continue;case 134182001: if (!str1.equals("go hall")) break;  System.out.println("You exit back into the hall you first entered in."); this.previousRoom.enter(); continue;case 239250716: if (!str1.equals("burn it")) break;  System.out.println("You can't seem to get them to light. Must be some kind of magic..."); continue;case 359614183: if (!str1.equals("cut through")) break;  if (!this.websCut) { if (Player.instance.hasItem("sword")) { System.out.println("The sword slices right through the webs! You're able to cut away the door and get through."); this.websCut = true; this.keyRoom.enter(); continue; }  System.out.println("With what, your hands?"); continue; }  System.out.println("You already did that."); continue;case 556903116: if (!str1.equals("cut door")) break;  if (!this.websCut) { if (Player.instance.hasItem("sword")) { System.out.println("The sword slices right through the webs! You're able to cut away the door and get through."); this.websCut = true; this.keyRoom.enter(); continue; }  System.out.println("With what, your hands?"); continue; }  System.out.println("You already did that."); continue;case 557459133: if (!str1.equals("cut webs")) break;  if (!this.websCut) { if (Player.instance.hasItem("sword")) { System.out.println("The sword slices right through the webs! You're able to cut away the door and get through."); this.websCut = true; this.keyRoom.enter(); continue; }  System.out.println("With what, your hands?"); continue; }  System.out.println("You already did that."); continue;case 1797592917: if (!str1.equals("go to the hall")) break;  System.out.println("You exit back into the hall you first entered in."); this.previousRoom.enter(); continue;case 2112469531: if (!str1.equals("cut the door"))
/*    */               break;  if (!this.websCut) { if (Player.instance.hasItem("sword")) { System.out.println("The sword slices right through the webs! You're able to cut away the door and get through."); this.websCut = true; this.keyRoom.enter(); continue; }  System.out.println("With what, your hands?"); continue; }  System.out.println("You already did that."); continue;
/*    */           case 2113025548: if (!str1.equals("cut the webs"))
/* 59 */               break;  if (!this.websCut) { if (Player.instance.hasItem("sword")) { System.out.println("The sword slices right through the webs! You're able to cut away the door and get through."); this.websCut = true; this.keyRoom.enter(); continue; }  System.out.println("With what, your hands?"); continue; }  System.out.println("You already did that."); continue; }  System.out.println("Can't do that.");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\SpiderHallway.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */