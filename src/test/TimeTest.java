
import java.util.TimeZone;
import java.util.Calendar;

public class TimeTest {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        System.out.println("TimeTest");
        final int minute = Calendar.getInstance().get(Calendar.MINUTE);
        final int hour = Calendar.getInstance().get(Calendar.HOUR);
        System.out.println(hour+":"+minute); // Display the current date
    }
}
