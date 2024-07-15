package DesignPatterns.Singleton_;
 class Logger {
    private Logger() {
    }

    private static class LoggerHolder {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() {
        return LoggerHolder.INSTANCE;
    }

    public void log(String message) {
        System.out.println("[INFO] " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        logger.log("This is a log message.");

        Logger anotherLogger = Logger.getInstance();
        anotherLogger.log("This is another log message.");

        System.out.println(logger == anotherLogger); 
    }
}
