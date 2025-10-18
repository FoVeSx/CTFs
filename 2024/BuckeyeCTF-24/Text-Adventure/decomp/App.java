/*   */ import rooms.CaveEnterance;
/*   */ import rooms.Room;
/*   */ 
/*   */ public class App {
/* 5 */   static Room caveEnterance = (Room)new CaveEnterance();
/*   */   public static void main(String[] args) throws Exception {
/* 7 */     System.out.println("You've been transported to a faraway magical land! Can you find the flag?\n---");
/* 8 */     caveEnterance.enter();
/*   */   }
/*   */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar!\App.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */