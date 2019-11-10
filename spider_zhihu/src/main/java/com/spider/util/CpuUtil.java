package com.spider.util;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.sun.management.OperatingSystemMXBean;
 
@SuppressWarnings({"restriction","unused"})
public class CpuUtil {
    private static final int CPUTIME = 10;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;
 
    public static double getCpuRatio(){
           // 操作系统
         String osName = System.getProperty("os.name");
           double cpuRatio = 0;
          if (osName.toLowerCase().startsWith("windows")) {
              cpuRatio = getCpuRatioForWindows();
          }else{
        	  cpuRatio = cpuUsage();
          }
          return cpuRatio;
    }
    public static double getMemoryRatio(){
    	int kb = 1024;
		double memoryRatio = 0;
    	OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
    	long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
    	long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getAvailableProcessors()-osmxb.getFreePhysicalMemorySize()) / kb;
    	BigDecimal bg = new BigDecimal((double)usedMemory/totalMemorySize*100).setScale(2, RoundingMode.UP);
    	
    	return bg.doubleValue();
    }
    /**
     * 功能：获取Linux系统cpu使用率
     * */
    public static double cpuUsage() {
        try {
            Map<?, ?> map1 = CpuUtil.cpuinfo();
            Thread.sleep(1 * 1000);
            Map<?, ?> map2 = CpuUtil.cpuinfo();

            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());

            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());

            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;

            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;

            //float cpusage = (total / totalidle) * 100;
            //return (double) cpusage;
            BigDecimal bg = new BigDecimal((double)total/totalidle*100).setScale(2, RoundingMode.UP);
            return bg.doubleValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
     /**
      * 获得CPU使用率.
      * @return 返回cpu使用率
      */
     private static double getCpuRatioForWindows() {
         try {
             String procCmd = System.getenv("windir")
                     + "\\system32\\wbem\\wmic.exe process get Caption,"
                     + "KernelModeTime,UserModeTime,WriteOperationCount";
             // 取进程信息
             long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
             Thread.sleep(CPUTIME);
             long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
             if (c0 != null && c1 != null) {
                 long idletime = c1[0] - c0[0];
                 long busytime = c1[1] - c0[1];
                 return Double.valueOf(
                         PERCENT * (busytime) / (busytime + idletime))
                         .doubleValue();
             } else {
                 return 0.0;
             }
         } catch (Exception ex) {
             ex.printStackTrace();
             return 0.0;
         }
     }
     /**
  * 读取CPU信息.
      * @param proc
      */
     private static long[] readCpu(final Process proc) {
         long[] retn = new long[2];
         try {
             proc.getOutputStream().close();
             InputStreamReader ir = new InputStreamReader(proc.getInputStream());
             LineNumberReader input = new LineNumberReader(ir);
             String line = input.readLine();
             if (line == null || line.length() < FAULTLENGTH) {
                 return null;
             }
             //以下为执行命令行后，各列出现的第一个字母的横坐标。具体样式可以在cmd 执行该指令
             int captionX= line.indexOf("Caption");
             int kernelModeTimeX = line.indexOf("KernelModeTime");
             int userModeTimeX = line.indexOf("UserModeTime");
             //标记最后一列
             int lastMark = line.indexOf("WriteOperationCount");
             long idletime = 0;
             long kneltime = 0;
             long usertime = 0;
             while ((line = input.readLine()) != null) {
                 if (line.length() < lastMark) {
                     continue;
                 }
                 String caption = substring(line, captionX, kernelModeTimeX - 1)
                         .trim();
                /* if (caption.toLowerCase().indexOf("wmic.exe") >= 0) {
                     continue;
                 }*/
                 if (caption.equals("System Idle Process") || caption.equals("System")) {
                     idletime += Long.valueOf(substring(line, kernelModeTimeX, userModeTimeX - 1).trim()).longValue();
                     idletime += Long.valueOf(substring(line, kernelModeTimeX, userModeTimeX - 1).trim()).longValue();
                     continue;
                 }
                 kneltime += Long.valueOf(substring(line, kernelModeTimeX, userModeTimeX - 1).trim()).longValue();
                 usertime += Long.valueOf(substring(line, userModeTimeX, lastMark - 1).trim()).longValue();
             }
             retn[0] = idletime;
             retn[1] = kneltime + usertime;
             return retn;
         } catch (Exception ex) {
             ex.printStackTrace();
         } finally {
             try {
                 proc.getInputStream().close();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return null;
     }
     /**
      * 功能：CPU使用信息
      * */
     public static Map<?, ?> cpuinfo() {
         InputStreamReader inputs = null;
         BufferedReader buffer = null;
         Map<String, Object> map = new HashMap<String, Object>();
         try {
             inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
             buffer = new BufferedReader(inputs);
             String line = "";
             while (true) {
                 line = buffer.readLine();
                 if (line == null) {
                     break;
                 }
                 if (line.startsWith("cpu")) {
                     StringTokenizer tokenizer = new StringTokenizer(line);
                     List<String> temp = new ArrayList<String>();
                     while (tokenizer.hasMoreElements()) {
                         String value = tokenizer.nextToken();
                         temp.add(value);
                     }
                     map.put("user", temp.get(1));
                     map.put("nice", temp.get(2));
                     map.put("system", temp.get(3));
                     map.put("idle", temp.get(4));
                     map.put("iowait", temp.get(5));
                     map.put("irq", temp.get(6));
                     map.put("softirq", temp.get(7));
                     map.put("stealstolen", temp.get(8));
                     break;
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 buffer.close();
                 inputs.close();
             } catch (Exception e2) {
                 e2.printStackTrace();
             }
         }
         return map;
     }
     public static String substring(String src, int start_idx, int end_idx){
         byte[] b = src.getBytes();
         String tgt = "";
         for(int i=start_idx; i<=end_idx; i++){
             tgt +=(char)b[i];
         }
         return tgt;
     }
 
     public static double getDiskInfo(){
         File[] disks = File.listRoots();
         long use = 0L;
         long total = 0L;
         //long free = 0L;
         for(File file : disks){
        	 //free += file.getFreeSpace();
        	 use +=file.getUsableSpace();
        	 total +=file.getTotalSpace();
         }
         BigDecimal bg = new BigDecimal((double)(total-use)/total*100).setScale(2, RoundingMode.UP);
         if(total==0L){
        	 return 0D;
         }else{
        	 return bg.doubleValue();
         }
         
     }
     public static void main(String[] args) throws Exception {
		 System.out.println(getDiskInfo());
		 System.out.println(getCpuRatio());
		 System.out.println(getMemoryRatio());
     }
 
}
