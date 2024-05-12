package phonebook;

import java.util.concurrent.TimeUnit;

public class TimeConverter {
    public  String convertMilli(long milli) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milli);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milli);
        long mSeconds = milli - TimeUnit.SECONDS.toMillis(seconds);

        return " " + minutes + " min. " + seconds%60 + " sec. " + mSeconds + " ms.";
    }
}
