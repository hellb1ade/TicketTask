import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Utils {
    public static JSONArray convertFileToJSONArray(String path) { // Конвертация файла json в массив JSONArray
        org.json.simple.JSONObject jsonObject;
        try {
            jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(new FileReader(path));
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Файл не найден");
        }
        return (JSONArray) jsonObject.get("tickets");
    }

    public static Long getFlightTime(Object departureDate, Object departureTime, Object arrivalDate, Object arrivalTime) { // Получение времени полета в миллисекундах
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        String o = departureDate + " " + departureTime;
        String o2 = arrivalDate + " " + arrivalTime;
        Date date;
        Date date2;
        try {
            date = formatter.parse(o);
            date2 = formatter.parse(o2);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return date2.getTime() - date.getTime();
    }

    public static String millisecondsToHourMinute(Long milliseconds) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        double hour = (double) milliseconds / 3600000;
        int minute = ((int) ((hour - (int) hour) * 100) * 60) / 100;
        while (minute % 5 != 0) minute++;
        return (int) hour + ":" + minute;
    }

    public static int getAverageValue(List<Long> list) {
        int result = 0;
        for (Long x : list) result += x;
        return result / list.size();
    }

    public static Long getMedian(List<Long> list) {
        int medium = list.size() / 2;
        Collections.sort(list);
        if (list.size() % 2 == 0) {
            return (list.get(medium) + list.get(medium - 1)) / 2;
        } else {
            return list.get(medium);
        }
    }
}
