package gov.naco.soch.singelton;

import java.util.logging.*;

public class LoggerSingleton {
    private static LoggerSingleton instance;
    private Logger logger;

    private LoggerSingleton() {
        // Initialize the logger
        logger = Logger.getLogger(LoggerSingleton.class.getName());

        // Configure the logger (You can customize this part)
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
    }

    public static LoggerSingleton getInstance() {
        if (instance == null) {
            synchronized (LoggerSingleton.class) {
                if (instance == null) {
                    instance = new LoggerSingleton();
                }
            }
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
