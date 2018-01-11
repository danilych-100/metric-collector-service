/*   1:    */ package ru.fts.metrics.mqmetrics;
/*   2:    */ 
/*   3:    */

import com.ibm.mq.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/*   4:    */
/*   5:    */
/*   6:    */
/*   7:    */
/*   8:    */
/*   9:    */
/*  10:    */
/*  11:    */
/*  12:    */

/*  13:    */
/*  14:    */ public class QueueMonitor
/*  15:    */ {
/*  16: 17 */   private static Log log = LogFactory.getLog(QueueMonitor.class);
/*  17: 18 */   private static int options = 8249;
/*  18: 23 */   private static Map<String, QueueMessage> messages = new HashMap();
/*  19:    */   private MQQueueManager manager;
/*  20:    */   private String managerIdString;
/*  21:    */   
/*  22:    */   public QueueMonitor(QManagerMonitorConfig managerMonitorConfig, MQQueueManager manager)
/*  23:    */   {
/*  24: 30 */     if (manager == null) {
/*  25: 31 */       throw new IllegalArgumentException("manager is null");
/*  26:    */     }
/*  27: 33 */     if (managerMonitorConfig == null) {
/*  28: 34 */       throw new IllegalArgumentException("manager config is null");
/*  29:    */     }
/*  30: 36 */     this.manager = manager;
/*  31: 37 */     this.managerIdString = getManagerIdString(managerMonitorConfig);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private String getManagerIdString(QManagerMonitorConfig managerMonitorConfig)
/*  35:    */   {
/*  36: 41 */     StringBuffer managerId = new StringBuffer();
/*  37: 42 */     if (managerMonitorConfig.isLocalManager())
/*  38:    */     {
/*  39: 43 */       managerId.append("local_");
/*  40: 44 */       managerId.append(managerMonitorConfig.getQManagerName());
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44: 47 */       managerId.append("remote_");
/*  45: 48 */       managerId.append(managerMonitorConfig.getQManagerName()).append("_");
/*  46: 49 */       managerId.append(managerMonitorConfig.getHost()).append(":");
/*  47: 50 */       managerId.append(managerMonitorConfig.getPort()).append(":");
/*  48: 51 */       managerId.append(managerMonitorConfig.getChannel());
/*  49:    */     }
/*  50: 53 */     return managerId.toString();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public QueueMonitorStatus monitoring(QueueMonitorConfig qConfig)
/*  54:    */   {
/*  55: 57 */     MQQueue queue = null;
/*  56: 58 */     QueueMonitorStatus status = new QueueMonitorStatus();
/*  57: 59 */     status.setQueueName(qConfig.getQueueName());
/*  58:    */     try
/*  59:    */     {
/*  60: 61 */       queue = this.manager.accessQueue(qConfig.getQueueName(), options);
/*  61: 62 */       status.setAvailable(true);
/*  62:    */       try
/*  63:    */       {
/*  64: 64 */         int depth = queue.getCurrentDepth();
/*  65: 65 */         status.setDepth(depth);
/*  66: 66 */         status.setExceededDepth(isExceededDepth(depth, qConfig));
/*  67:    */       }
/*  68:    */       catch (MQException e)
/*  69:    */       {
/*  70: 68 */         status.getErrors().add(new MQMonitorException("Не удалось получить глубину очереди."));
/*  71:    */       }
/*  72:    */
/*  82: 89 */       return status;
/*  83:    */     }
/*  84:    */     catch (MQException e)
/*  85:    */     {
/*  86: 78 */       status.setAvailable(false);
/*  87: 79 */       log.error(e.getMessage(), e);
					return status;
/*  88:    */     }
/*  89:    */     finally
/*  90:    */     {
/*  91: 81 */       if (queue != null) {
/*  92:    */         try
/*  93:    */         {
/*  94: 83 */           queue.close();
/*  95:    */         }
/*  96:    */         catch (MQException e)
/*  97:    */         {
/*  98: 85 */           log.error(e.getMessage(), e);
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private boolean isExceededDepth(int depth, QueueMonitorConfig qConfig)
/* 105:    */   {
/* 106: 93 */     return depth > qConfig.getMaxDepth();
/* 107:    */   }
/* 108:    */   
/* 109:    */   private boolean isExceededInterval(long interval, QueueMonitorConfig qConfig)
/* 110:    */   {
/* 111: 97 */     return interval > qConfig.getMaxInterval() * 1000;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private String getTopMessageId(MQQueue queue)
/* 115:    */     throws MQException
/* 116:    */   {
/* 117:101 */     MQMessage message = new MQMessage();
/* 118:102 */     MQGetMessageOptions options = new MQGetMessageOptions();
/* 119:103 */     options.options |= 0x10;
/* 120:104 */     queue.get(message, options);
/* 121:105 */     return dumpHexId(message.messageId);
/* 122:    */   }
/* 123:    */   
/* 124:    */   static String dumpHexId(byte[] myId)
/* 125:    */   {
/* 126:109 */     StringBuffer str = new StringBuffer();
/* 127:110 */     for (int i = 0; i < myId.length; i++)
/* 128:    */     {
/* 129:111 */       char b = (char)(myId[i] & 0xFF);
/* 130:112 */       if (b < '\020') {
/* 131:113 */         str.append("0");
/* 132:    */       }
/* 133:115 */       str.append(Integer.toHexString(b).toUpperCase());
/* 134:    */     }
/* 135:117 */     return str.toString();
/* 136:    */   }
/* 137:    */   
/* 138:    */   private long getInterval(MQQueue queue)
/* 139:    */     throws MQException
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:122 */       String messageId = getTopMessageId(queue);
/* 144:123 */       QueueMessage message = (QueueMessage)messages.get(getQueueId(queue));
/* 145:124 */       if ((message == null) || (!message.getId().equals(messageId)))
/* 146:    */       {
/* 147:125 */         messages.put(getQueueId(queue), new QueueMessage(messageId));
/* 148:126 */         return 0L;
/* 149:    */       }
/* 150:128 */       return System.currentTimeMillis() - message.getTimeStamp();
/* 151:    */     }
/* 152:    */     catch (MQException e)
/* 153:    */     {
/* 154:130 */       return noMessageAvailable(e);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private String getQueueId(MQQueue queue)
/* 159:    */   {
/* 160:135 */     return this.managerIdString + "_" + queue.name;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private long noMessageAvailable(MQException e)
/* 164:    */     throws MQException
/* 165:    */   {
/* 166:139 */     if (e.reasonCode == 2033) {
/* 167:140 */       return 0L;
/* 168:    */     }
/* 169:142 */     throw e;
/* 170:    */   }
/* 171:    */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QueueMonitor

 * JD-Core Version:    0.7.0.1

 */