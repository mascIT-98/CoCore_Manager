package com.hasta.cocoremanager;

public  class Strings {
	
	static final String E="Error", Y ="Yes", N="No", VER_LINK="http://hastalafiesta.altervista.org/version.txt", APP_LINK="http://hastalafiesta.altervista.org/cocoremanager.apk";
	static final String TAG="CoCoreManager";
	//MALI
	static final String MALI_DEB = "/sys/module/mali/parameters/mali_debug_level";
    static final String EGL = "/system/lib/egl/";
    static final String PP = "/sys/module/mali/parameters/mali_pp_scheduler_balance_jobs";
    
    //BATTERY
    static final String REAL_CHARGE ="/sys/kernel/abb-chargalg/eoc_status";
    static final String DHDPM ="/sys/module/dhd/parameters/dhdpm_fast";
    
    //CPU
    static final String AVAILABLE_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
	static final String GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
	static final String AVAILABLE_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
	final static String MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
	final static String MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
	final static String SCHEDULER = "/sys/block/mmcblk0/queue/scheduler";
	
	//DISPLAY
	static final String TOUCHTOWAKE ="/sys/kernel/cypress/touch2wake";
    static final String SWEEP ="/sys/kernel/mxt224e/sweep2wake";
    static final String LED ="/sys/kernel/cypress/led_disable";
    static final String NIGHT_MODE="/sys/kernel/s6e63m0/night_mode";
    static final String FBDELAY = "/sys/module/fbearlysuspend/parameters/fbdelay";
    static final String FBDELAY_MS = "/sys/module/fbearlysuspend/parameters/fbdelay_ms";
    
    static final String ZRAM = "/sys/block/zram0/disksize";   
    static final String USBSHARE ="/sys/kernel/abb-regu/VOTG";
    static final String USBSWITCH ="/sys/devices/virtual/usb_switch/FSA_SWITCH/swreset";    
    static final String SDCARD ="/sys/devices/virtual/bdi/179:0/read_ahead_kb";
    
    static final String TCP_CONG_AVAILABLE = "/proc/sys/net/ipv4/tcp_available_congestion_control";
    
    static final String FSYNCODE = "/sys/kernel/fsync/mode";
    static final String MMC = "/sys/module/mmc_core/parameters/use_spi_crc";  
    static final String TBOOST = "/sys/kernel/mxt224e/touchboost";
    
    static final String DEEPSLEEP = "/d/cpuidle/deepest_state";
    static final String CHARGE_CURR = "/sys/kernel/abb-charger/charger_curr";
    static final String POWERSAVE_MAX = "/sys/power/cpufreq_dvfs_powersave";
    static final String TPARAM ="/sys/kernel/mxt224e/threshold_t48";
    static final String LCDCLK ="/sys/kernel/s6e63m0/lcdclk";
    static final String GPU_OC = "/sys/kernel/mali/mali_gpu_clock";
    static final String LMK ="/sys/module/lowmemorykiller/parameters/minfree";
    static final String MALI_MAX_READS = "/sys/module/mali/parameters/mali_l2_max_reads";
}
