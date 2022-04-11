package uj.java.w7.insurance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

record Pair(String county, BigDecimal insuranceValueDifference) {
}

public class FloridaInsurance {

    public static void main(String[] args) {
        runTasks(readZip());
    }

    private static void runTasks(List<InsuranceEntry> flInsurance) {
        try {
            firstTask(flInsurance);
            secondTask(flInsurance);
            thirdTask(flInsurance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void thirdTask(List<InsuranceEntry> flInsurance) throws IOException {
        var totalInsuranceValue = flInsurance.stream().collect(new InsuranceValueCollector());

        Path path = Path.of("most_valuable.txt");
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.writeString(path, "country,value\n" + stringWithResults(totalInsuranceValue));
    }

    private static String stringWithResults(List<Pair> totalInsuranceValue) {
        var string = new StringBuilder();
        for (var pair : totalInsuranceValue) {
            string.append(pair.county()).append(",").append(pair.insuranceValueDifference()).append("\n");
        }
        return string.toString();
    }

    private static void secondTask(List<InsuranceEntry> flInsurance) throws IOException {

        var sumOfTheInsuranceValue = flInsurance.stream().map(InsuranceEntry::tiv_2012).reduce(BigDecimal.ZERO, BigDecimal::add);

        Path path = Path.of("tiv2012.txt");
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.writeString(path, sumOfTheInsuranceValue.toString());
    }

    private static void firstTask(List<InsuranceEntry> flInsurance) throws IOException {
        var NumberOfCountries = flInsurance.stream().map(InsuranceEntry::country).distinct().count();

        Path path = Path.of("count.txt");
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.writeString(path, String.valueOf(NumberOfCountries));
    }

    private static List<InsuranceEntry> readZip() {
        var flInsurance = new ArrayList<InsuranceEntry>();

        try {
            ZipFile zipFile = new ZipFile("FL_insurance.csv.zip");
            ZipEntry zipEntry = zipFile.getEntry("FL_insurance.csv");
            InputStream inputStream = zipFile.getInputStream(zipEntry);

            List<String> allRows = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().toList();

            flInsurance = convertStringsToInsuranceEntryFormat(allRows);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flInsurance;
    }

    private static ArrayList<InsuranceEntry> convertStringsToInsuranceEntryFormat(List<String> allRows) {
        var flInsurance = new ArrayList<InsuranceEntry>();

        for (int i = 1; i < allRows.size(); i++) {
            flInsurance.add(parseRow(allRows.get(i)));
        }
        return flInsurance;
    }

    private static InsuranceEntry parseRow(String row) {
        var values = row.split(",");
        return new InsuranceEntry(values);
    }
}
