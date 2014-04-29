/* MiniJavaParserTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. MiniJavaParserTokenManager.java */
package parser;
import syntaxtree.*;
import visitor.*;

/** Token Manager. */
@SuppressWarnings("unused")public class MiniJavaParserTokenManager implements MiniJavaParserConstants {
    int tokenCount = 0;
    void CommonTokenAction(Token t)
    {
        tokenCount++;
    }

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0x80L) != 0L)
            return 0;
         if ((active0 & 0x7fffc00L) != 0L)
         {
            jjmatchedKind = 49;
            return 7;
         }
         return -1;
      case 1:
         if ((active0 & 0x40000L) != 0L)
            return 7;
         if ((active0 & 0x7fbfc00L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 1;
            return 7;
         }
         return -1;
      case 2:
         if ((active0 & 0x3fafc00L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 2;
            return 7;
         }
         if ((active0 & 0x4010000L) != 0L)
            return 7;
         return -1;
      case 3:
         if ((active0 & 0x172dc00L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 3;
            return 7;
         }
         if ((active0 & 0x2882000L) != 0L)
            return 7;
         return -1;
      case 4:
         if ((active0 & 0x62d800L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 4;
            return 7;
         }
         if ((active0 & 0x1100400L) != 0L)
            return 7;
         return -1;
      case 5:
         if ((active0 & 0x220000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 5;
            return 7;
         }
         if ((active0 & 0x40d800L) != 0L)
            return 7;
         return -1;
      case 6:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         if ((active0 & 0x20000L) != 0L)
            return 7;
         return -1;
      case 7:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 8:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 9:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 10:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 11:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 12:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 13:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 14:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 15:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 16:
         if ((active0 & 0x200000L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 33:
         jjmatchedKind = 32;
         return jjMoveStringLiteralDfa1_0(0x4000000000L);
      case 38:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 40:
         return jjStopAtPos(0, 42);
      case 41:
         return jjStopAtPos(0, 43);
      case 42:
         return jjStopAtPos(0, 31);
      case 43:
         return jjStopAtPos(0, 29);
      case 44:
         return jjStopAtPos(0, 47);
      case 45:
         return jjStopAtPos(0, 30);
      case 46:
         return jjStopAtPos(0, 48);
      case 47:
         return jjMoveStringLiteralDfa1_0(0x80L);
      case 59:
         return jjStopAtPos(0, 46);
      case 60:
         jjmatchedKind = 28;
         return jjMoveStringLiteralDfa1_0(0x800000000L);
      case 61:
         jjmatchedKind = 33;
         return jjMoveStringLiteralDfa1_0(0x2000000000L);
      case 62:
         jjmatchedKind = 34;
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x204000L);
      case 91:
         return jjStopAtPos(0, 44);
      case 93:
         return jjStopAtPos(0, 45);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x20000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x400L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x1000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x50000L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x400000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x4000000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x800L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x2800000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 123:
         return jjStopAtPos(0, 40);
      case 124:
         return jjMoveStringLiteralDfa1_0(0x8000000000L);
      case 125:
         return jjStopAtPos(0, 41);
      default :
         return jjMoveNfa_0(5, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x8000000L) != 0L)
            return jjStopAtPos(1, 27);
         break;
      case 42:
         if ((active0 & 0x80L) != 0L)
            return jjStopAtPos(1, 7);
         break;
      case 61:
         if ((active0 & 0x800000000L) != 0L)
            return jjStopAtPos(1, 35);
         else if ((active0 & 0x1000000000L) != 0L)
            return jjStopAtPos(1, 36);
         else if ((active0 & 0x2000000000L) != 0L)
            return jjStopAtPos(1, 37);
         else if ((active0 & 0x4000000000L) != 0L)
            return jjStopAtPos(1, 38);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x4408000L);
      case 102:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(1, 18, 7);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x2100000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x80400L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x22000L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x5000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x800L);
      case 121:
         return jjMoveStringLiteralDfa2_0(active0, 0x200000L);
      case 124:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStopAtPos(1, 39);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x1400L);
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x800L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x2102000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x280000L);
      case 116:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(2, 16, 7);
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      case 119:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(2, 26, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 100:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(3, 13, 7);
         break;
      case 101:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(3, 19, 7);
         else if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(3, 23, 7);
         break;
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x400000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x120800L);
      case 115:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(3, 25, 7);
         return jjMoveStringLiteralDfa4_0(active0, 0x1000400L);
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x201000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(4, 20, 7);
         else if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(4, 24, 7);
         return jjMoveStringLiteralDfa5_0(active0, 0x220000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x1800L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x8000L);
      case 115:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(4, 10, 7);
         break;
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x400000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x20000L);
      case 99:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(5, 11, 7);
         else if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(5, 12, 7);
         break;
      case 103:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(5, 14, 7);
         break;
      case 104:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(5, 22, 7);
         break;
      case 109:
         return jjMoveStringLiteralDfa6_0(active0, 0x200000L);
      case 110:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(5, 15, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 46:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000L);
      case 110:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(6, 17, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 111:
         return jjMoveStringLiteralDfa8_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 117:
         return jjMoveStringLiteralDfa9_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa10_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
private int jjMoveStringLiteralDfa10_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 46:
         return jjMoveStringLiteralDfa11_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
private int jjMoveStringLiteralDfa11_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 112:
         return jjMoveStringLiteralDfa12_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
private int jjMoveStringLiteralDfa12_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(10, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0);
      return 12;
   }
   switch(curChar)
   {
      case 114:
         return jjMoveStringLiteralDfa13_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(11, active0);
}
private int jjMoveStringLiteralDfa13_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(11, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(12, active0);
      return 13;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa14_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(12, active0);
}
private int jjMoveStringLiteralDfa14_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(12, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(13, active0);
      return 14;
   }
   switch(curChar)
   {
      case 110:
         return jjMoveStringLiteralDfa15_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(13, active0);
}
private int jjMoveStringLiteralDfa15_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(13, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(14, active0);
      return 15;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa16_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(14, active0);
}
private int jjMoveStringLiteralDfa16_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(14, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(15, active0);
      return 16;
   }
   switch(curChar)
   {
      case 108:
         return jjMoveStringLiteralDfa17_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(15, active0);
}
private int jjMoveStringLiteralDfa17_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(15, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(16, active0);
      return 17;
   }
   switch(curChar)
   {
      case 110:
         if ((active0 & 0x200000L) != 0L)
            return jjStopAtPos(17, 21);
         break;
      default :
         break;
   }
   return jjStartNfa_0(16, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 11;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 5:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 50)
                        kind = 50;
                     { jjCheckNAdd(10); }
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 50)
                        kind = 50;
                  }
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 0:
                  if (curChar == 47)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 1:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 2:
                  if ((0x2400L & l) != 0L && kind > 6)
                     kind = 6;
                  break;
               case 3:
                  if (curChar == 10 && kind > 6)
                     kind = 6;
                  break;
               case 4:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 49)
                     kind = 49;
                  jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 8:
                  if (curChar == 48 && kind > 50)
                     kind = 50;
                  break;
               case 9:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 50)
                     kind = 50;
                  { jjCheckNAdd(10); }
                  break;
               case 10:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 50)
                     kind = 50;
                  { jjCheckNAdd(10); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 5:
               case 7:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 49)
                     kind = 49;
                  { jjCheckNAdd(7); }
                  break;
               case 1:
                  { jjAddStates(0, 2); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     { jjAddStates(0, 2); }
                  break;
               default : if (i1 == 0 || l1 == 0 || i2 == 0 || l2 == 0) break; else break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 11 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_1(){
   switch(curChar)
   {
      case 42:
         return jjMoveStringLiteralDfa1_1(0x100L);
      default :
         return 1;
   }
}
private int jjMoveStringLiteralDfa1_1(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 47:
         if ((active0 & 0x100L) != 0L)
            return jjStopAtPos(1, 8);
         break;
      default :
         return 2;
   }
   return 2;
}
static final int[] jjnextStates = {
   1, 2, 4, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, 
"\143\154\141\163\163", "\160\165\142\154\151\143", "\163\164\141\164\151\143", "\166\157\151\144", 
"\123\164\162\151\156\147", "\162\145\164\165\162\156", "\151\156\164", "\142\157\157\154\145\141\156", 
"\151\146", "\145\154\163\145", "\167\150\151\154\145", 
"\123\171\163\164\145\155\56\157\165\164\56\160\162\151\156\164\154\156", "\154\145\156\147\164\150", "\164\162\165\145", "\146\141\154\163\145", 
"\164\150\151\163", "\156\145\167", "\46\46", "\74", "\53", "\55", "\52", "\41", "\75", "\76", 
"\74\75", "\76\75", "\75\75", "\41\75", "\174\174", "\173", "\175", "\50", "\51", 
"\133", "\135", "\73", "\54", "\56", null, null, };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      TokenLexicalActions(matchedToken);
      return matchedToken;
   }
   image = jjimage;
   image.setLength(0);
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         if (jjmatchedPos == 0 && jjmatchedKind > 9)
         {
            jjmatchedKind = 9;
         }
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
           TokenLexicalActions(matchedToken);
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
              SkipLexicalActions(matchedToken);
           }
           else
              SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        jjimageLen += jjmatchedPos + 1;
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public MiniJavaParserTokenManager(JavaCharStream stream){

      if (JavaCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public MiniJavaParserTokenManager (JavaCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  public void ReInit(JavaCharStream stream)
  {
    jjmatchedPos = jjnewStateCnt = 0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 11; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(JavaCharStream stream, int lexState)
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 2 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "WithinComment",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, 1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, 
};
static final long[] jjtoToken = {
   0x7fffffffffc01L, 
};
static final long[] jjtoSkip = {
   0x1feL, 
};
static final long[] jjtoSpecial = {
   0xc0L, 
};
static final long[] jjtoMore = {
   0x200L, 
};
    protected JavaCharStream  input_stream;

    private final int[] jjrounds = new int[11];
    private final int[] jjstateSet = new int[2 * 11];

    private final StringBuilder jjimage = new StringBuilder();
    private StringBuilder image = jjimage;
    private int jjimageLen;
    private int lengthOfMatch;
    
    protected char curChar;
}
