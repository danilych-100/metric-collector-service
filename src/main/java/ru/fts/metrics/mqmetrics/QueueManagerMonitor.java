/*  1:   */ package ru.fts.metrics.mqmetrics;
/*  2:   */ 
/*  3:   */

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*  4:   */
/*  5:   */
/*  6:   */
/*  7:   */

/*  8:   */
/*  9:   */ public class QueueManagerMonitor
/* 10:   */ {
/* 11:11 */   private static Log log = LogFactory.getLog(QueueManagerMonitor.class);
/* 12:   */   private MQQueueManager manager;
/* 13:   */   private QManagerMonitorConfig qManagerMonitorConfig;
/* 14:   */   
/* 15:   */   public QueueManagerMonitor(QManagerMonitorConfig managerConfig)
/* 16:   */   {
/* 17:17 */     if (managerConfig == null) {
/* 18:18 */       throw new IllegalArgumentException("config is null");
/* 19:   */     }
/* 20:20 */     this.qManagerMonitorConfig = managerConfig;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected boolean connect()
/* 24:   */   {
/* 25:25 */     if (this.qManagerMonitorConfig.isLocalManager())
/* 26:   */     {
/* 27:26 */       com.ibm.mq.MQEnvironment.hostname = null;
/* 28:27 */       com.ibm.mq.MQEnvironment.channel = null;
/* 29:28 */       com.ibm.mq.MQEnvironment.port = 0;
/* 30:   */     }
/* 31:   */     else
/* 32:   */     {
/* 33:31 */       com.ibm.mq.MQEnvironment.hostname = this.qManagerMonitorConfig.getHost();
/* 34:32 */       com.ibm.mq.MQEnvironment.channel = this.qManagerMonitorConfig.getChannel();
/* 35:33 */       com.ibm.mq.MQEnvironment.port = this.qManagerMonitorConfig.getPort();
/* 36:   */     }
/* 37:   */     try
/* 38:   */     {
/* 39:36 */       this.manager = new MQQueueManager(this.qManagerMonitorConfig.getQManagerName());
/* 40:37 */       return true;
/* 41:   */     }
/* 42:   */     catch (MQException e)
/* 43:   */     {
/* 44:39 */       log.error(e.getMessage(), e);
/* 45:   */     }
/* 46:40 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected void disconnect()
/* 50:   */   {
/* 51:   */     try
/* 52:   */     {
/* 53:46 */       if (this.manager != null)
/* 54:   */       {
/* 55:47 */         this.manager.disconnect();
/* 56:48 */         this.manager.close();
/* 57:   */       }
/* 58:   */     }
/* 59:   */     catch (MQException e)
/* 60:   */     {
/* 61:51 */       log.error(e.getMessage(), e);
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public QManagerMonitorStatus monitoring()
/* 66:   */   {
/* 67:56 */     QManagerMonitorStatus result = new QManagerMonitorStatus(this.qManagerMonitorConfig);
/* 68:57 */     if (connect())
/* 69:   */     {
/* 70:58 */       result.setAvailable(true);
/* 71:59 */       for (QueueMonitorConfig qConfig : this.qManagerMonitorConfig.getQueueMonitorConfigs()) {
/* 72:60 */         result.getQueueStatuses().add(new QueueMonitor(this.qManagerMonitorConfig, this.manager).monitoring(qConfig));
/* 73:   */       }
/* 74:   */     }
/* 75:   */     else
/* 76:   */     {
/* 77:64 */       result.setAvailable(false);
/* 78:   */     }
/* 79:66 */     disconnect();
/* 80:67 */     return result;
/* 81:   */   }
/* 82:   */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QueueManagerMonitor

 * JD-Core Version:    0.7.0.1

 */