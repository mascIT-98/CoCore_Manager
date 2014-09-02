package com.hasta.cocoremanager;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Restore extends Activity{
	
	public static void shared(Context context) {
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
		
		// --------------------- BATTERY ---------------------
		if(shared.getInt("dhdpm_val", 0)==1){
			Utils.mSetFilePerm(Strings.DHDPM, 777);
			Utils.writeValue(Strings.DHDPM, shared.getBoolean("wifi", false) ? "0" : "1");
			Utils.mSetFilePerm(Strings.DHDPM, 664);
		}
				
		// ---------------- CPU ---------------------- 
		Utils.mSetFilePerm(Strings.MAX_FREQ, 777);
		Utils.writeValue(Strings.MAX_FREQ, shared.getString("restore_max_freq", "1000000"));
		Utils.mSetFilePerm(Strings.MAX_FREQ, 664);
		Utils.mSetFilePerm(Strings.MIN_FREQ, 777);
		Utils.writeValue(Strings.MIN_FREQ, shared.getString("restore_min_freq", "200000"));
		Utils.mSetFilePerm(Strings.MIN_FREQ, 664);
		Utils.mSetFilePerm(Strings.GOVERNOR, 777);
		Utils.writeValue(Strings.GOVERNOR, shared.getString("restore_gov", "ondemand"));
		Utils.mSetFilePerm(Strings.GOVERNOR, 664);
		Utils.mSetFilePerm(Strings.SCHEDULER, 777);
		Utils.writeValue(Strings.SCHEDULER, shared.getString("restore_sched", "noop"));
		Utils.mSetFilePerm(Strings.SCHEDULER, 664);
		
		// ----------------- DISPLAY -----------------
		if(shared.getInt("sweeptowake_val", 0)==1){
			Utils.mSetFilePerm(Strings.SWEEP, 777);
			Utils.writeValue(Strings.SWEEP, shared.getBoolean("sweep2wake", false) ? "on":"off");
			Utils.mSetFilePerm(Strings.SWEEP, 664);
		}
		if(shared.getInt("touchtowake_val", 0)==1){
		    Utils.mSetFilePerm(Strings.TOUCHTOWAKE, 777);
		    Utils.writeValue(Strings.TOUCHTOWAKE, shared.getBoolean("touch2wake", false)?"on":"off");
		    Utils.mSetFilePerm(Strings.TOUCHTOWAKE, 664);
		}
		if(shared.getInt("led_val", 0)==1){
		    Utils.mSetFilePerm(Strings.LED, 777);
		    Utils.writeValue(Strings.LED, shared.getBoolean("led", false)?"on":"off");
		    Utils.mSetFilePerm(Strings.LED, 664);
		}
		if(shared.getInt("nmode_val", 0)==1){
			Utils.mSetFilePerm(Strings.NIGHT_MODE, 777);
			Utils.writeValue(Strings.NIGHT_MODE, shared.getBoolean("led", false)?"on":"off");
			Utils.mSetFilePerm(Strings.NIGHT_MODE, 664);
		}
		if(shared.getInt("crt_val", 0)==1){
			Utils.mSetFilePerm(Strings.FBDELAY, 777);
			Utils.writeValue(Strings.FBDELAY, shared.getBoolean("crt", false) ? "1" : "0");
			Utils.mSetFilePerm(Strings.FBDELAY, 664);
			Utils.mSetFilePerm(Strings.FBDELAY_MS, 777);
			Utils.writeValue(Strings.FBDELAY_MS, shared.getBoolean("crt", false) ? "350" : "0");
			Utils.mSetFilePerm(Strings.FBDELAY_MS, 664);
		}
			
		// -------------- MALI -----------------
		if(shared.getInt("malideb_val", 0)==1){
			Utils.mSetFilePerm(Strings.MALI_DEB, 777);
			Utils.writeValue(Strings.MALI_DEB, shared.getBoolean("mali_debug", false) ? "0" : "2");
			Utils.mSetFilePerm(Strings.MALI_DEB, 664);
		}
		if(shared.getInt("pp_val", 0)==1){
			Utils.mSetFilePerm(Strings.PP, 777);
			Utils.writeValue(Strings.PP, shared.getBoolean("pp", false) ? "1" : "0");
			Utils.mSetFilePerm(Strings.PP, 664);
		}
				
		// ---------- MISC ------------
		if(shared.getInt("sdcard_val", 0)==1){
			Utils.mSetFilePerm(Strings.SDCARD, 777);
			Utils.writeValue(Strings.SDCARD, shared.getBoolean("sdcard", false) ? "2048" : "512");
			Utils.mSetFilePerm(Strings.SDCARD, 664);
		}
		if(shared.getInt("usbshare_val", 0)==1){
			Utils.mSetFilePerm(Strings.USBSHARE, 777);
			Utils.writeValue(Strings.USBSHARE, shared.getBoolean("usb_share", false) ? "1" : "0");
		    Utils.mSetFilePerm(Strings.USBSHARE, 664);
		}
		if(shared.getInt("usbswitch_val", 0)==1){
			Utils.mSetFilePerm(Strings.USBSWITCH, 777);
			Utils.writeValue(Strings.USBSWITCH, shared.getBoolean("usb_switch", false) ? "1" : "0");
			Utils.mSetFilePerm(Strings.USBSWITCH, 664);
		} 
		if(shared.getInt("zram_val", 0)==1){
			Utils.mSetFilePerm(Strings.ZRAM, 777);
			Utils.writeValue(Strings.ZRAM, shared.getString("zram", "0"));
			Utils.mSetFilePerm(Strings.ZRAM, 664);
		}	
		
		// ----- NET, TCP CONGESTIONS ------
		try {
			Process tcp = Runtime.getRuntime().exec(new String[]{"su", "-c", "sysctl -w net.ipv4.tcp_congestion_control=" +shared.getString("restore_tcp_cong", "cubic")});
			tcp.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
		}
		
        //  ------- PERFORMANCE --------
		if(shared.getInt("fsync_val", 0)==1){
			Utils.mSetFilePerm(Strings.FSYNCODE, 777);
			Utils.writeValue(Strings.FSYNCODE, shared.getBoolean("fsync", true) ? "1" : "0");
			Utils.mSetFilePerm(Strings.FSYNCODE, 664);
		}
		if(shared.getInt("mmc_val", 0)==1){
			Utils.mSetFilePerm(Strings.MMC, 777);
			Utils.writeValue(Strings.MMC, shared.getBoolean("mmc", false) ? "0" : "1");
			Utils.mSetFilePerm(Strings.MMC, 664);
		}
		if(shared.getInt("tboost_val", 0)==1){
		    Utils.mSetFilePerm(Strings.TBOOST, 777);
		    Utils.writeValue(Strings.TBOOST, shared.getBoolean("t_boost", false) ? "on" : "off");
		    Utils.mSetFilePerm(Strings.TBOOST, 664);
		}					
		if(shared.getInt("deep_val", 0)==1){
			Utils.mSetFilePerm(Strings.DEEPSLEEP, 777);
			Utils.writeValue(Strings.DEEPSLEEP, shared.getString("deep", "3"));
			Utils.mSetFilePerm(Strings.DEEPSLEEP, 664);
		}
		if(shared.getInt("chargecurr_val", 0)==1){
			Utils.mSetFilePerm(Strings.CHARGE_CURR, 777);
			Utils.writeValue(Strings.CHARGE_CURR, shared.getString("voltage", "600"));
			Utils.mSetFilePerm(Strings.CHARGE_CURR, 664);
		}
		if(shared.getInt("savemax_val", 0)==1){			
	        Utils.mSetFilePerm(Strings.POWERSAVE_MAX, 777);
	        Utils.writeValue(Strings.POWERSAVE_MAX, shared.getString("save_max", "800000"));
	        Utils.mSetFilePerm(Strings.POWERSAVE_MAX, 664);
		}
		if(shared.getInt("lcdclk_val", 0)==1){
	        Utils.mSetFilePerm(Strings.LCDCLK, 777);
	        Utils.writeValue(Strings.LCDCLK, shared.getString("lcdclk", "0"));
	        Utils.mSetFilePerm(Strings.LCDCLK, 664);
		}
		if(shared.getInt("tparam_val", 0)==1){
	        Utils.mSetFilePerm(Strings.TPARAM, 777);
	        Utils.writeValue(Strings.TPARAM, shared.getString("tparam", "val=17"));
	        Utils.mSetFilePerm(Strings.TPARAM, 664);
		}
		if(shared.getInt("gpuoc_val", 0)==1){
		    Utils.mSetFilePerm(Strings.GPU_OC, 777);
			Utils.writeValue(Strings.GPU_OC, shared.getString("gpu_oc", "499200"));
			Utils.mSetFilePerm(Strings.GPU_OC, 664);
		}
	    if(shared.getInt("lmk_val", 0)==1){
     		Utils.mSetFilePerm(Strings.LMK, 777);
	        Utils.writeValue(Strings.LMK, shared.getString("lmk", ""));
	        Utils.mSetFilePerm(Strings.LMK, 664);
		}
		if(shared.getInt("malimax_val", 0)==1){
		    Utils.mSetFilePerm(Strings.MALI_MAX_READS, 777);
		    Utils.writeValue(Strings.MALI_MAX_READS, shared.getString("mali_max_reads", "28"));
		    Utils.mSetFilePerm(Strings.MALI_MAX_READS, 664);
		}					
	}
}