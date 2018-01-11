/*  1:   */ package ru.fts.metrics.mqmetrics;
/*  2:   */ 
/*  3:   */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*  4:   */
/*  5:   */

/*  6:   */
/*  7:   */ public class QManagerMonitorStatus
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -8855026797407666947L;
/* 11:   */   private boolean available;
/* 12:   */   private QManagerMonitorConfig config;
/* 13:   */   private List<QueueMonitorStatus> queueStatuses;
/* 14:   */   
/* 15:   */   public QManagerMonitorStatus() {}
/* 16:   */   
/* 17:   */   public QManagerMonitorStatus(QManagerMonitorConfig config)
/* 18:   */   {
/* 19:19 */     this.config = config;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static long getSerialVersionUID()
/* 23:   */   {
/* 24:23 */     return -8855026797407666947L;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setAvailable(boolean available)
/* 28:   */   {
/* 29:27 */     this.available = available;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean isAvailable()
/* 33:   */   {
/* 34:31 */     return this.available;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setQueueStatuses(List<QueueMonitorStatus> queueStatuses)
/* 38:   */   {
/* 39:35 */     this.queueStatuses = queueStatuses;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<QueueMonitorStatus> getQueueStatuses()
/* 43:   */   {
/* 44:39 */     if (this.queueStatuses == null) {
/* 45:40 */       this.queueStatuses = new ArrayList();
/* 46:   */     }
/* 47:42 */     return this.queueStatuses;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setConfig(QManagerMonitorConfig config)
/* 51:   */   {
/* 52:46 */     this.config = config;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public QManagerMonitorConfig getConfig()
/* 56:   */   {
/* 57:50 */     return this.config;
/* 58:   */   }
/* 59:   */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QManagerMonitorStatus

 * JD-Core Version:    0.7.0.1

 */