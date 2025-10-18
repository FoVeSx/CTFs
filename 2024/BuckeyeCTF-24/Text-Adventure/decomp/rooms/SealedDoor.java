/*    */ package rooms;
/*    */ 
/*    */ import utility.Player;
/*    */ 
/*    */ public class SealedDoor extends Room {
/*  6 */   private Room deadEnd = new DeadEnd(this);
/*    */   
/*    */   private boolean locked = true;
/*    */   
/*    */   public SealedDoor(Room prevRoom) {
/* 11 */     this.previousRoom = prevRoom;
/*    */   }
/*    */ 
/*    */   
/*    */   public void enter() {
/* 16 */     if (this.locked) {
/* 17 */       lockedRoom();
/*    */     } else {
/* 19 */       unlockedRoom();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void lockedRoom() {
/* 24 */     if (this.locked) {
/* 25 */       System.out.println("You enter a small room, with stone close all around you. Before you lies a door sealed with a large lock.\nBehind you lie the base of the steps.");
/*    */     } else {
/*    */       
/* 28 */       System.out.println("You return to the small room, with stone close all around you. Before you lies a great door, but it's unlocked now.\nBehind you lie the base of the steps.");
/*    */     } 
/*    */     
/*    */     while (true) {
/* 32 */       String input = getInput(); String str1;
/* 33 */       switch ((str1 = input.toLowerCase()).hashCode()) { case -1989222567: if (!str1.equals("unlock the door")) {
/*    */             break;
/*    */           }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 41 */           if (Player.instance.hasItem("key")) {
/* 42 */             System.out.println("You fit the key into the lock, and slowly start to turn it...");
/* 43 */             System.out.println("It works! The lock falls away and you pass through the door.");
/* 44 */             this.locked = false;
/* 45 */             this.deadEnd.enter(); continue;
/*    */           } 
/* 47 */           System.out.println("Looks like you'll have to find a key."); continue;case -920183030: if (!str1.equals("unlock door")) break;  if (Player.instance.hasItem("key")) { System.out.println("You fit the key into the lock, and slowly start to turn it..."); System.out.println("It works! The lock falls away and you pass through the door."); this.locked = false; this.deadEnd.enter(); continue; }  System.out.println("Looks like you'll have to find a key."); continue;case -840442044: if (!str1.equals("unlock")) break;  if (Player.instance.hasItem("key")) { System.out.println("You fit the key into the lock, and slowly start to turn it..."); System.out.println("It works! The lock falls away and you pass through the door."); this.locked = false; this.deadEnd.enter(); continue; }  System.out.println("Looks like you'll have to find a key.");
/*    */           continue;
/*    */         
/*    */         case -124606993:
/*    */           if (!str1.equals("go steps")) {
/*    */             break;
/*    */           }
/* 54 */           System.out.println("You return to the base of the steps.");
/* 55 */           this.previousRoom.enter(); continue;case -98476149: if (!str1.equals("go to the steps")) break;  System.out.println("You return to the base of the steps."); this.previousRoom.enter(); continue;case 102846135: if (!str1.equals("leave")) break;  System.out.println("You return to the base of the steps."); this.previousRoom.enter(); continue;case 134002975: if (!str1.equals("go back")) break;  System.out.println("You return to the base of the steps."); this.previousRoom.enter(); continue;case 1153113875: if (!str1.equals("open the door"))
/*    */             break;  System.out.println("It's, uh, locked."); continue;
/*    */         case 1487686596: if (!str1.equals("open door"))
/* 58 */             break;  System.out.println("It's, uh, locked."); continue; }  System.out.println("Can't do that.");
/*    */     } 
/*    */   }
/*    */   
/*    */   private void unlockedRoom() {
/*    */     // Byte code:
/*    */     //   0: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*    */     //   3: ldc 'You enter a small room with stone close all around. Before you lies the door you unlocked.'
/*    */     //   5: invokevirtual println : (Ljava/lang/String;)V
/*    */     //   8: invokestatic getInput : ()Ljava/lang/String;
/*    */     //   11: astore_1
/*    */     //   12: aload_1
/*    */     //   13: invokevirtual toLowerCase : ()Ljava/lang/String;
/*    */     //   16: dup
/*    */     //   17: astore_2
/*    */     //   18: invokevirtual hashCode : ()I
/*    */     //   21: lookupswitch default -> 145, 96667352 -> 64, 102846135 -> 76, 134002975 -> 88, 1067699085 -> 100
/*    */     //   64: aload_2
/*    */     //   65: ldc 'enter'
/*    */     //   67: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   70: ifne -> 112
/*    */     //   73: goto -> 145
/*    */     //   76: aload_2
/*    */     //   77: ldc 'leave'
/*    */     //   79: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   82: ifne -> 127
/*    */     //   85: goto -> 145
/*    */     //   88: aload_2
/*    */     //   89: ldc 'go back'
/*    */     //   91: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   94: ifne -> 127
/*    */     //   97: goto -> 145
/*    */     //   100: aload_2
/*    */     //   101: ldc 'go through'
/*    */     //   103: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   106: ifne -> 112
/*    */     //   109: goto -> 145
/*    */     //   112: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*    */     //   115: ldc 'You enter back through the door...'
/*    */     //   117: invokevirtual println : (Ljava/lang/String;)V
/*    */     //   120: aload_0
/*    */     //   121: getfield deadEnd : Lrooms/Room;
/*    */     //   124: invokevirtual enter : ()V
/*    */     //   127: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*    */     //   130: ldc 'You return back to the base of the steps.'
/*    */     //   132: invokevirtual println : (Ljava/lang/String;)V
/*    */     //   135: aload_0
/*    */     //   136: getfield previousRoom : Lrooms/Room;
/*    */     //   139: invokevirtual enter : ()V
/*    */     //   142: goto -> 8
/*    */     //   145: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*    */     //   148: ldc 'Can't do that.'
/*    */     //   150: invokevirtual println : (Ljava/lang/String;)V
/*    */     //   153: goto -> 8
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #65	-> 0
/*    */     //   #67	-> 8
/*    */     //   #68	-> 12
/*    */     //   #71	-> 112
/*    */     //   #72	-> 120
/*    */     //   #75	-> 127
/*    */     //   #76	-> 135
/*    */     //   #77	-> 142
/*    */     //   #79	-> 145
/*    */     //   #66	-> 153
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	156	0	this	Lrooms/SealedDoor;
/*    */     //   12	141	1	input	Ljava/lang/String;
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\rooms\SealedDoor.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */