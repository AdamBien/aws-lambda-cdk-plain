package airhacks.logging.control;

/**
 * Centralized logging for AWS Lambda functions.
 * All output is automatically captured and redirected to CloudWatch Logs by the Lambda runtime.
 * Log streams are created per Lambda execution environment, with logs retained based on CloudWatch retention policy.
 */
public interface Log {

    static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    static void info(String message, Object... args) {
        System.out.println("[INFO] " + message.formatted(args));
    }

    static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    static void error(String message, Throwable throwable) {
        System.out.println("[ERROR] " + message);
        throwable.printStackTrace(System.out);
    }

    static void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }

    static void debug(String message, Object... args) {
        System.out.println("[DEBUG] " + message.formatted(args));
    }
}