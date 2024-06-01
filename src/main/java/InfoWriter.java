import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class InfoWriter {
    private final JSONLists converter = new JSONLists();
    StringBuilder infoBuilder = new StringBuilder();
    String path;

    public InfoWriter(String path) {
        this.path = path;
    }

    public void write() {
        converter.init();
        writeFlightInfo();
        writeDifference();
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(infoBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException("Файл не найден");
        }
    }

    private void writeFlightInfo() {
        infoBuilder.append("Данные о времени полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика:\n");
        for (String carrier : converter.carrierFlightTime.keySet()) {
            infoBuilder.append(carrier + ": ");
            for (Long mill : converter.carrierFlightTime.get(carrier)) {
                infoBuilder.append(Utils.millisecondsToHourMinute(mill) + ", ");
            }
            infoBuilder.append("Минимальное время полёта - " + Utils.millisecondsToHourMinute(Collections.min(converter.carrierFlightTime.get(carrier))));
            infoBuilder.append("\n");
        }
        infoBuilder.append("\n");
    }

    private void writeDifference() {
        infoBuilder.append("Данные о ценах на полеты между городами Владивосток и Тель-Авив:\n");
        infoBuilder.append("Список всех цен на полеты: " + converter.ticketPrice + "\n");
        infoBuilder.append("Среднее значение цены: " + Utils.getAverageValue(converter.ticketPrice) + "\n");
        infoBuilder.append("Медиана цен: " + Utils.getMedian(converter.ticketPrice) + "\n");
        infoBuilder.append("Разница между средней ценой и медианой: " + (Utils.getAverageValue(converter.ticketPrice) - Utils.getMedian(converter.ticketPrice)));
    }
}
