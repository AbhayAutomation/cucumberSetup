package utils;

public class ExtentLogger {

    public static void info(String message) {
        Log.info(message);
        ExtentTestManager.getTest().info(message);
    }

    public static void warn(String message) {
        Log.warn(message);
        ExtentTestManager.getTest().warning(message);
    }

    public static void error(String message) {
        Log.error(message);
        ExtentTestManager.getTest().fail(message);
    }
}
