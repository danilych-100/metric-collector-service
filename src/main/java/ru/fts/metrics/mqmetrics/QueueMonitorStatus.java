/*   1:    */ package ru.fts.metrics.mqmetrics;
/*   2:    */ 
/*   3:    */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*   4:    */
/*   5:    */

/*   6:    */
/*   7:    */ public class QueueMonitorStatus
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -387096920447611729L;
/*  11:    */   private String queueName;
/*  12: 10 */   private boolean available = false;
/*  13: 11 */   private int depth = 0;
/*  14: 12 */   private long interval = 0L;
/*  15: 13 */   private boolean exceededDepth = false;
/*  16: 14 */   private boolean exceededInterval = false;
/*  17:    */   private List<MQMonitorException> errors;
/*  18:    */   
/*  19:    */   public String getQueueName()
/*  20:    */   {
/*  21: 23 */     return this.queueName;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setQueueName(String queueName)
/*  25:    */   {
/*  26: 26 */     this.queueName = queueName;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getDepth()
/*  30:    */   {
/*  31: 34 */     return this.depth;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setDepth(int depth)
/*  35:    */   {
/*  36: 37 */     this.depth = depth;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public long getInterval()
/*  40:    */   {
/*  41: 48 */     return this.interval;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setInterval(long interval)
/*  45:    */   {
/*  46: 51 */     this.interval = interval;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean isAvailable()
/*  50:    */   {
/*  51: 59 */     return this.available;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setAvailable(boolean available)
/*  55:    */   {
/*  56: 62 */     this.available = available;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isExceededDepth()
/*  60:    */   {
/*  61: 73 */     return this.exceededDepth;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setExceededDepth(boolean availableDepth)
/*  65:    */   {
/*  66: 76 */     this.exceededDepth = availableDepth;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isExceededInterval()
/*  70:    */   {
/*  71: 87 */     return this.exceededInterval;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setExceededInterval(boolean availableInterval)
/*  75:    */   {
/*  76: 90 */     this.exceededInterval = availableInterval;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isHasErrors()
/*  80:    */   {
/*  81: 94 */     return getErrors().size() > 0;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setErrors(List<MQMonitorException> errors)
/*  85:    */   {
/*  86: 98 */     this.errors = errors;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<MQMonitorException> getErrors()
/*  90:    */   {
/*  91:102 */     if (this.errors == null) {
/*  92:103 */       this.errors = new ArrayList();
/*  93:    */     }
/*  94:105 */     return this.errors;
/*  95:    */   }
/*  96:    */ }



/* Location:           C:\_PROJECTS\_LEGACY\mq-utilites-1.0.0.jar

 * Qualified Name:     ru.acs.fts.mqmonitoring.QueueMonitorStatus

 * JD-Core Version:    0.7.0.1

 */