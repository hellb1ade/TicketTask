import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class JSONLists {
    public HashMap<String, ArrayList<Long>> carrierFlightTime; // Список авиаперевозчиков (ключ) и цен (значение-список)
    public List<Long> ticketPrice; // Cписок цен для полета между городами Владивосток и Тель-Авив
    private JSONArray jsonArray;

    public void init() {
        carrierFlightTime = new HashMap<>();
        ticketPrice = new ArrayList<>();
        jsonArray = Utils.convertFileToJSONArray("/Users/maxims/IdeaProjects/TicketsTask/src/main/resources/tickets.json");
        fillTicketPriceList();
        fillCarrierFlightTime();
    }

    private void fillTicketPriceList() { // Заполнение списка ticketPrice ценами из файла json
        org.json.simple.JSONObject jsonObject;
        for (Object object : jsonArray) {
            jsonObject = (org.json.simple.JSONObject) object;
            if (jsonObject.get("origin").equals("VVO") && jsonObject.get("destination").equals("TLV")) {
                ticketPrice.add((Long) jsonObject.get("price"));
            }
        }
        Collections.sort(ticketPrice);
    }

    private void fillCarrierFlightTime() { // Заполнение списка carrierFlightTime данными из файла json
        org.json.simple.JSONObject jsonObject;
        for (Object object : jsonArray) {
            jsonObject = (org.json.simple.JSONObject) object;
            if (jsonObject.get("origin").equals("VVO") && jsonObject.get("destination").equals("TLV")) {
                carrierFlightTime.put((String) jsonObject.get("carrier"), new ArrayList<>());
            }
        }
        for (Object object : jsonArray) {
            jsonObject = (org.json.simple.JSONObject) object;
            if (jsonObject.get("origin").equals("VVO") && jsonObject.get("destination").equals("TLV")) {
                for (String carrier : carrierFlightTime.keySet()) {
                    if (jsonObject.get("carrier").equals(carrier)) {
                        carrierFlightTime.get(carrier).add(Utils.getFlightTime(jsonObject.get("departure_date"), jsonObject.get("departure_time"), jsonObject.get("arrival_date"), jsonObject.get("arrival_time")));
                    }
                }
            }
        }
    }
}
