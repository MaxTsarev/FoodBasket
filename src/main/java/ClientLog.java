import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    private int productNum;
    private int amount;
    private static List<ClientLog> data = new ArrayList<>();

    public ClientLog(int productNum, int amount) {
        this.productNum = productNum;
        this.amount = amount;
    }

    public static void log(int productNum, int amount) {
        data.add(new ClientLog(productNum + 1, amount));
    }

    public static void exportAsCSV(File txtFile) {
        ColumnPositionMappingStrategy<ClientLog> strategy =
                new ColumnPositionMappingStrategy<>();
        strategy.setType(ClientLog.class);
        strategy.setColumnMapping("productNum", "amount");
        try (Writer writer = new FileWriter("log.csv")) {
            StatefulBeanToCsv<ClientLog> sbc =
                    new StatefulBeanToCsvBuilder<ClientLog>(writer)
                            .withMappingStrategy(strategy)
                            .build();
            sbc.write(data);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
