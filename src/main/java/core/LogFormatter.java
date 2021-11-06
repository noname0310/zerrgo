package core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

final class LogFormatter extends Formatter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private final Date date = new Date();
    private final boolean useColor;

    public LogFormatter(boolean useColor) {
        this.useColor = useColor;
    }

    public String getHead(Handler handler) {
        if (useColor) {
            return ANSI_CYAN + "logger started at " + calcDate(date.getTime()) + "\n";
        } else {
            return "logger started at " + calcDate(date.getTime()) + "\n";
        }
    }

    @Override
    public String format(LogRecord record) {
        var stringBuilder = new StringBuilder(1000);

        if (useColor) {
            switch (record.getLevel().toString()) {
                case "WARNING" -> stringBuilder.append(ANSI_YELLOW);
                case "SEVERE" -> stringBuilder.append(ANSI_RED);
                default -> stringBuilder.append(ANSI_CYAN);
            }
        }

        stringBuilder.append(calcDate(record.getMillis()));

        stringBuilder.append(" [");
        stringBuilder.append(record.getLevel());
        stringBuilder.append("] ");

        stringBuilder.append("[");
        stringBuilder.append(record.getSourceClassName());
        stringBuilder.append(".");
        stringBuilder.append(record.getSourceMethodName());
        stringBuilder.append("] ");

        stringBuilder.append(record.getMessage());
        stringBuilder.append("\n");

        if (useColor) {
            stringBuilder.append(ANSI_RESET);
        }

        return stringBuilder.toString();
    }

    @Override
    public String getTail(Handler handler) {
        if (useColor) {
            return ANSI_CYAN + "logger ended at " + calcDate(date.getTime()) + "\n";
        } else {
            return "logger ended at " + calcDate(date.getTime()) + "\n";
        }
    }

    private String calcDate(long milliSecs) {
        var date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date resultDate = new Date(milliSecs);
        return date_format.format(resultDate);
    }
}
