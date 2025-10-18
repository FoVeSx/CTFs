/*    */ package utility;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MagicOrb {
/*    */   public void printFlag() throws IOException {
/*  9 */     BufferedReader br = new BufferedReader(new FileReader("/flag"));
/*    */     String line;
/* 11 */     while ((line = br.readLine()) != null) {
/* 12 */       System.out.println(line);
/*    */     }
/* 14 */     br.close();
/*    */   }
/*    */ }


/* Location:              C:\projects\CTF\BuckeyeCTF-24\Text-Adventure\text-adventure.jar\\utility\MagicOrb.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */