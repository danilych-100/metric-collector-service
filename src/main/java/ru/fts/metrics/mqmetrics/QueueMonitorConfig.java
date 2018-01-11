/*  1:   */ package ru.fts.metrics.mqmetrics;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;

/*  4:   */
/*  5:   */ public class QueueMonitorConfig
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -2538435700025336974L;
/*  9:   */   private String queueName;
/* 10: 8 */   private int maxDepth = 0;
/* 11: 9 */   private int maxInterval = 0;
/* 12:   */   
/* 13:   */   public void setQueueName(String queueName)
/* 14:   */   {
/* 15:12 */     this.queueName = queueName;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getQueueName()
/* 19:   */   {
/* 20:16 */     return this.queueName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setMaxDepth(int maxDepth)
/* 24:   */   {
/* 25:20 */     this.maxDepth = maxDepth;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getMaxDepth()
/* 29:   */   {
/* 30:24 */     return this.maxDepth;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setMaxInterval(int maxInterval)
/* 34:   */   {
/* 35:28 */     this.maxInterval = maxInterval;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getMaxInterval()
/* 39:   */   {
/* 40:32 */     return this.maxInterval;
/* 41:   */   }
/* 42:   */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QueueMonitorConfig

 * JD-Core Version:    0.7.0.1

 */