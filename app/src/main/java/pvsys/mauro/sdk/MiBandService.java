package pvsys.mauro.sdk;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class MiBandService {

    public static final String BTLE_BASE_UUID = "0000%s-0000-1000-8000-00805f9b34fb";

    public static final String MAC_ADDRESS_FILTER_1_1A = "88:0F:10";
    public static final String MAC_ADDRESS_FILTER_1S = "C8:0F:10";

    public static final UUID UUID_SERVICE_MIBAND_SERVICE = UUID.fromString(String.format(BTLE_BASE_UUID, "FEE0"));
    public static final UUID UUID_SERVICE_MIBAND2_SERVICE = UUID.fromString(String.format(BTLE_BASE_UUID, "FEE1"));

    public static final UUID UUID_SERVICE_HEART_RATE = UUID.fromString((String.format(BTLE_BASE_UUID, "180D")));

    public static final String UUID_SERVICE_WEIGHT_SERVICE = "00001530-0000-3512-2118-0009af100700";

    public static final UUID UUID_CHARACTERISTIC_DEVICE_INFO = UUID.fromString(String.format(BTLE_BASE_UUID, "FF01"));

    public static final UUID UUID_CHARACTERISTIC_DEVICE_NAME = UUID.fromString(String.format(BTLE_BASE_UUID, "FF02"));

    public static final UUID UUID_CHARACTERISTIC_NOTIFICATION = UUID.fromString(String.format(BTLE_BASE_UUID, "FF03"));

    public static final UUID UUID_CHARACTERISTIC_USER_INFO = UUID.fromString(String.format(BTLE_BASE_UUID, "FF04"));

    public static final UUID UUID_CHARACTERISTIC_CONTROL_POINT = UUID.fromString(String.format(BTLE_BASE_UUID, "FF05"));

    public static final UUID UUID_CHARACTERISTIC_REALTIME_STEPS = UUID.fromString(String.format(BTLE_BASE_UUID, "FF06"));

    public static final UUID UUID_CHARACTERISTIC_ACTIVITY_DATA = UUID.fromString(String.format(BTLE_BASE_UUID, "FF07"));

    public static final UUID UUID_CHARACTERISTIC_FIRMWARE_DATA = UUID.fromString(String.format(BTLE_BASE_UUID, "FF08"));

    public static final UUID UUID_CHARACTERISTIC_LE_PARAMS = UUID.fromString(String.format(BTLE_BASE_UUID, "FF09"));

    public static final UUID UUID_CHARACTERISTIC_DATE_TIME = UUID.fromString(String.format(BTLE_BASE_UUID, "FF0A"));

    public static final UUID UUID_CHARACTERISTIC_STATISTICS = UUID.fromString(String.format(BTLE_BASE_UUID, "FF0B"));

    public static final UUID UUID_CHARACTERISTIC_BATTERY = UUID.fromString(String.format(BTLE_BASE_UUID, "FF0C"));

    public static final UUID UUID_CHARACTERISTIC_TEST = UUID.fromString(String.format(BTLE_BASE_UUID, "FF0D"));

    public static final UUID UUID_CHARACTERISTIC_SENSOR_DATA = UUID.fromString(String.format(BTLE_BASE_UUID, "FF0E"));

    public static final UUID UUID_CHARACTERISTIC_PAIR = UUID.fromString(String.format(BTLE_BASE_UUID, "FF0F"));


    public static final UUID UUID_CHARACTERISTIC_HEART_RATE_CONTROL_POINT = UUID.fromString((String.format(BTLE_BASE_UUID, "2A39")));
    public static final UUID UUID_CHARACTERISTIC_HEART_RATE_MEASUREMENT = UUID.fromString((String.format(BTLE_BASE_UUID, "2A37")));

    public static final UUID UUID_CHARACTERISTIC_GAP_DEVICE_NAME = UUID.fromString((String.format(BTLE_BASE_UUID, "2A00")));


    /* FURTHER UUIDS that were mixed with the other params below. The base UUID for these is unknown */

    public static final byte ALIAS_LEN = 0xa;

    /*NOTIFICATIONS: usually received on the UUID_CHARACTERISTIC_NOTIFICATION characteristic */

    public static final byte NOTIFY_NORMAL = 0x0;

    public static final byte NOTIFY_FIRMWARE_UPDATE_FAILED = 0x1;

    public static final byte NOTIFY_FIRMWARE_UPDATE_SUCCESS = 0x2;

    public static final byte NOTIFY_CONN_PARAM_UPDATE_FAILED = 0x3;

    public static final byte NOTIFY_CONN_PARAM_UPDATE_SUCCESS = 0x4;

    public static final byte NOTIFY_AUTHENTICATION_SUCCESS = 0x5;

    public static final byte NOTIFY_AUTHENTICATION_FAILED = 0x6;

    public static final byte NOTIFY_FITNESS_GOAL_ACHIEVED = 0x7;

    public static final byte NOTIFY_SET_LATENCY_SUCCESS = 0x8;

    public static final byte NOTIFY_RESET_AUTHENTICATION_FAILED = 0x9;

    public static final byte NOTIFY_RESET_AUTHENTICATION_SUCCESS = 0xa;

    public static final byte NOTIFY_FW_CHECK_FAILED = 0xb;

    public static final byte NOTIFY_FW_CHECK_SUCCESS = 0xc;

    public static final byte NOTIFY_STATUS_MOTOR_NOTIFY = 0xd;

    public static final byte NOTIFY_STATUS_MOTOR_CALL = 0xe;

    public static final byte NOTIFY_STATUS_MOTOR_DISCONNECT = 0xf;

    public static final byte NOTIFY_STATUS_MOTOR_SMART_ALARM = 0x10;

    public static final byte NOTIFY_STATUS_MOTOR_ALARM = 0x11;

    public static final byte NOTIFY_STATUS_MOTOR_GOAL = 0x12;

    public static final byte NOTIFY_STATUS_MOTOR_AUTH = 0x13;

    public static final byte NOTIFY_STATUS_MOTOR_SHUTDOWN = 0x14;

    public static final byte NOTIFY_STATUS_MOTOR_AUTH_SUCCESS = 0x15;

    public static final byte NOTIFY_STATUS_MOTOR_TEST = 0x16;

    // 0x18 is returned when we cancel data sync, perhaps is an ack for this message

    public static final byte NOTIFY_UNKNOWN = -0x1;

    public static final int NOTIFY_PAIR_CANCEL = 0xef;

    public static final int NOTIFY_DEVICE_MALFUNCTION = 0xff;


    /* MESSAGES: unknown */

    public static final byte MSG_CONNECTED = 0x0;

    public static final byte MSG_DISCONNECTED = 0x1;

    public static final byte MSG_CONNECTION_FAILED = 0x2;

    public static final byte MSG_INITIALIZATION_FAILED = 0x3;

    public static final byte MSG_INITIALIZATION_SUCCESS = 0x4;

    public static final byte MSG_STEPS_CHANGED = 0x5;

    public static final byte MSG_DEVICE_STATUS_CHANGED = 0x6;

    public static final byte MSG_BATTERY_STATUS_CHANGED = 0x7;

    /* COMMANDS: usually sent to UUID_CHARACTERISTIC_CONTROL_POINT characteristic */

    public static final byte COMMAND_SET_TIMER = 0x4;

    public static final byte COMMAND_SET_FITNESS_GOAL = 0x5;

    public static final byte COMMAND_FETCH_DATA = 0x6;

    public static final byte COMMAND_SEND_FIRMWARE_INFO = 0x7;

    public static final byte COMMAND_SEND_NOTIFICATION = 0x8;

    public static final byte COMMAND_CONFIRM_ACTIVITY_DATA_TRANSFER_COMPLETE = 0xa;

    public static final byte COMMAND_SYNC = 0xb;

    public static final byte COMMAND_REBOOT = 0xc;

    public static final byte COMMAND_SET_WEAR_LOCATION = 0xf;

    public static final byte COMMAND_STOP_SYNC_DATA = 0x11;

    public static final byte COMMAND_STOP_MOTOR_VIBRATE = 0x13;

    public static final byte COMMAND_SET_REALTIME_STEPS_NOTIFICATION = 0x3;

    public static final byte COMMAND_SET_REALTIME_STEP = 0x10;

    // Test HR
    public static final byte COMMAND_SET_HR_SLEEP = 0x0;
    public static final byte COMMAND_SET__HR_CONTINUOUS = 0x1;
    public static final byte COMMAND_SET_HR_MANUAL = 0x2;

    public static final byte COMMAND_GET_SENSOR_DATA = 0x12;

    /* FURTHER COMMANDS: unchecked therefore left commented


	public static final byte COMMAND_FACTORY_RESET = 0x9t;

	public static final int COMMAND_SET_COLOR_THEME = et;

	*/

    /* CONNECTION: unknown

   	public static final CONNECTION_LATENCY_LEVEL_LOW = 0x0t;

	public static final CONNECTION_LATENCY_LEVEL_MEDIUM = 0x1t;

	public static final CONNECTION_LATENCY_LEVEL_HIGH = 0x2t;

    */

    /* MODES: probably related to the sample data structure
    */

    public static final byte MODE_REGULAR_DATA_LEN_BYTE = 0x0;

    // was MODE_REGULAR_DATA_LEN_MINITE
    public static final byte MODE_REGULAR_DATA_LEN_MINUTE = 0x1;

    /* PROFILE: unknown

	public static final PROFILE_STATE_UNKNOWN:I = 0x0

	public static final PROFILE_STATE_INITIALIZATION_SUCCESS:I = 0x1

	public static final PROFILE_STATE_INITIALIZATION_FAILED:I = 0x2

	public static final PROFILE_STATE_AUTHENTICATION_SUCCESS:I = 0x3

	public static final PROFILE_STATE_AUTHENTICATION_FAILED:I = 0x4

	*/

    // TEST_*: sent to UUID_CHARACTERISTIC_TEST characteristic

    public static final byte TEST_DISCONNECTED_REMINDER = 0x5;

    public static final byte TEST_NOTIFICATION = 0x3;

    public static final byte TEST_REMOTE_DISCONNECT = 0x1;

    public static final byte TEST_SELFTEST = 0x2;

    private static final Map<UUID, String> MIBAND_DEBUG;

    static {
        MIBAND_DEBUG = new HashMap<>();
        MIBAND_DEBUG.put(UUID_SERVICE_MIBAND_SERVICE, "MiBand Service");
        MIBAND_DEBUG.put(UUID_SERVICE_HEART_RATE, "MiBand HR Service");

        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_DEVICE_INFO, "Device Info");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_DEVICE_NAME, "Device Name");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_NOTIFICATION, "Notification");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_USER_INFO, "User Info");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_CONTROL_POINT, "Control Point");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_REALTIME_STEPS, "Realtime Steps");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_ACTIVITY_DATA, "Activity Data");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_FIRMWARE_DATA, "Firmware Data");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_LE_PARAMS, "LE Params");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_DATE_TIME, "Date/Time");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_STATISTICS, "Statistics");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_BATTERY, "Battery");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_TEST, "Test");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_SENSOR_DATA, "Sensor Data");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_PAIR, "Pair");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_HEART_RATE_CONTROL_POINT, "Heart Rate Control Point");
        MIBAND_DEBUG.put(UUID_CHARACTERISTIC_HEART_RATE_MEASUREMENT, "Heart Rate Measure");
    }

    public static String lookup(UUID uuid, String fallback) {
        String name = MIBAND_DEBUG.get(uuid);
        if (name == null) {
            name = fallback;
        }
        return name;
    }






    static final byte[] reboot = new byte[]{MiBandService.COMMAND_REBOOT};

    static final byte[] startHeartMeasurementManual = new byte[]{0x15, MiBandService.COMMAND_SET_HR_MANUAL, 1};
    static final byte[] stopHeartMeasurementManual = new byte[]{0x15, MiBandService.COMMAND_SET_HR_MANUAL, 0};
    static final byte[] startHeartMeasurementContinuous = new byte[]{0x15, MiBandService.COMMAND_SET__HR_CONTINUOUS, 1};
    static final byte[] stopHeartMeasurementContinuous = new byte[]{0x15, MiBandService.COMMAND_SET__HR_CONTINUOUS, 0};
    static final byte[] startHeartMeasurementSleep = new byte[]{0x15, MiBandService.COMMAND_SET_HR_SLEEP, 1};
    static final byte[] stopHeartMeasurementSleep = new byte[]{0x15, MiBandService.COMMAND_SET_HR_SLEEP, 0};

    public static final byte[] startRealTimeStepsNotifications = new byte[]{MiBandService.COMMAND_SET_REALTIME_STEPS_NOTIFICATION, 1};
    public static final byte[] stopRealTimeStepsNotifications = new byte[]{MiBandService.COMMAND_SET_REALTIME_STEPS_NOTIFICATION, 0};

    public static final byte[] startSensorRead = new byte[]{MiBandService.COMMAND_GET_SENSOR_DATA, 1};
    public static final byte[] stopSensorRead = new byte[]{MiBandService.COMMAND_GET_SENSOR_DATA, 0};




    static final byte[] startVibrate = new byte[]{MiBandService.COMMAND_SEND_NOTIFICATION, 1};
    static final byte[] stopVibrate = new byte[]{MiBandService.COMMAND_STOP_MOTOR_VIBRATE};
    public static byte[] getDefaultNotification() {
        final int vibrateTimes = 1;
        final long vibrateDuration = 250l;
        final int flashTimes = 1;
        final int flashColour = 0xFFFFFFFF;
        final int originalColour = 0xFFFFFFFF;
        final long flashDuration = 250l;

        return getNotification(vibrateDuration, vibrateTimes, flashTimes, flashColour, originalColour, flashDuration);
    }

    public static byte[] getNotification(long vibrateDuration, int vibrateTimes, int flashTimes, int flashColour, int originalColour, long flashDuration) {
        byte[] vibrate = startVibrate;
        byte r = 6;
        byte g = 0;
        byte b = 6;
        boolean display = true;
        //      byte[] flashColor = new byte[]{ 14, r, g, b, display ? (byte) 1 : (byte) 0 };
        return vibrate;
    }

    public static byte[] getHighLatency() {
        int minConnectionInterval = 460;
        int maxConnectionInterval = 500;
        int latency = 0;
        int timeout = 500;
        int advertisementInterval = 0;

        return getLatency(minConnectionInterval, maxConnectionInterval, latency, timeout, advertisementInterval);
    }

    public static byte[] getLatency(int minConnectionInterval, int maxConnectionInterval, int latency, int timeout, int advertisementInterval) {
        byte result[] = new byte[12];
        result[0] = (byte) (minConnectionInterval & 0xff);
        result[1] = (byte) (0xff & minConnectionInterval >> 8);
        result[2] = (byte) (maxConnectionInterval & 0xff);
        result[3] = (byte) (0xff & maxConnectionInterval >> 8);
        result[4] = (byte) (latency & 0xff);
        result[5] = (byte) (0xff & latency >> 8);
        result[6] = (byte) (timeout & 0xff);
        result[7] = (byte) (0xff & timeout >> 8);
        result[8] = 0;
        result[9] = 0;
        result[10] = (byte) (advertisementInterval & 0xff);
        result[11] = (byte) (0xff & advertisementInterval >> 8);

        return result;
    }

    public static byte[] getLowLatency() {
        int minConnectionInterval = 39;
        int maxConnectionInterval = 49;
        int latency = 0;
        int timeout = 500;
        int advertisementInterval = 0;

        return getLatency(minConnectionInterval, maxConnectionInterval, latency, timeout, advertisementInterval);
    }

}
