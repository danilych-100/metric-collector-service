/*  1:   */ package ru.fts.metrics.mqmetrics;
/*  2:   */ 
/*  3:   */ public class QueueMessage
/*  4:   */ {
/*  5:   */   private String id;
/*  6:   */   private long timeStamp;
/*  7:   */   
/*  8:   */   public QueueMessage(String id)
/*  9:   */   {
/* 10:10 */     this.id = id;
/* 11:11 */     this.timeStamp = System.currentTimeMillis();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getId()
/* 15:   */   {
/* 16:15 */     return this.id;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public long getTimeStamp()
/* 20:   */   {
/* 21:19 */     return this.timeStamp;
/* 22:   */   }
/* 23:   */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QueueMessage

 * JD-Core Version:    0.7.0.1

 */