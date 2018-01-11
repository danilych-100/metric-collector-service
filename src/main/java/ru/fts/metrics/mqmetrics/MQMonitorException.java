/*  1:   */ package ru.fts.metrics.mqmetrics;
/*  2:   */
/*  3:   */ public class MQMonitorException
/*  4:   */  extends Exception
/*  5:   */ {
/*  6:   */   public MQMonitorException() {}
/*  7:   */
/*  8:   */   public MQMonitorException(String message)
/*  9:   */   {
/* 10:11 */     super(message);
/* 11:   */   }
/* 12:   */
/* 13:   */   public MQMonitorException(Throwable throwable)
/* 14:   */   {
/* 15:15 */     super(throwable);
/* 16:   */   }
/* 17:   */
  public MQMonitorException(String message, Throwable throwable)
 {
     super(message, throwable);
  }
 }