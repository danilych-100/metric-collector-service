/*  1:   */ package ru.fts.metrics.mqmetrics;
/*  2:   */ 
/*  3:   */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*  4:   */
/*  5:   */

/*  6:   */
/*  7:   */ public class QManagerMonitorConfig
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -7162824532729832952L;
/* 11: 9 */   private String qManagerName = "";
/* 12:10 */   private boolean localManager = true;
/* 13:11 */   private String host = "localhost";
/* 14:12 */   private String channel = "SYSTEM.DEF.SVRCONN";
/* 15:13 */   private int port = 1414;
/* 16:   */   private List<QueueMonitorConfig> queueMonitorConfigs;
/* 17:   */   
/* 18:   */   public void setQManagerName(String qManagerName)
/* 19:   */   {
/* 20:18 */     this.qManagerName = qManagerName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getQManagerName()
/* 24:   */   {
/* 25:22 */     return this.qManagerName;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setLocalManager(boolean localManager)
/* 29:   */   {
/* 30:26 */     this.localManager = localManager;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean isLocalManager()
/* 34:   */   {
/* 35:30 */     return this.localManager;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setHost(String host)
/* 39:   */   {
/* 40:34 */     this.host = host;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getHost()
/* 44:   */   {
/* 45:38 */     return this.host;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setChannel(String channel)
/* 49:   */   {
/* 50:42 */     this.channel = channel;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getChannel()
/* 54:   */   {
/* 55:46 */     return this.channel;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void setPort(int port)
/* 59:   */   {
/* 60:50 */     this.port = port;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int getPort()
/* 64:   */   {
/* 65:54 */     return this.port;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void setQueueMonitorConfigs(List<QueueMonitorConfig> queueMonitorConfigs)
/* 69:   */   {
/* 70:58 */     this.queueMonitorConfigs = queueMonitorConfigs;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public List<QueueMonitorConfig> getQueueMonitorConfigs()
/* 74:   */   {
/* 75:62 */     if (this.queueMonitorConfigs == null) {
/* 76:63 */       this.queueMonitorConfigs = new ArrayList();
/* 77:   */     }
/* 78:65 */     return this.queueMonitorConfigs;
/* 79:   */   }
/* 80:   */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QManagerMonitorConfig

 * JD-Core Version:    0.7.0.1

 */