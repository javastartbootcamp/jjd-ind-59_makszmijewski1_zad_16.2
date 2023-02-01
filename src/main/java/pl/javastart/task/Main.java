package pl.javastart.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        String date = loadDate(scanner);
        if (date.length() < 11) {
            date += " 00:00:00";
        }
        DateTimeFormatter datePattern = choosePattern(date);
        if (datePattern != null) {
            LocalDateTime createdDate = LocalDateTime.parse(date, datePattern);
            showTimeInDifferentZones(createdDate, datePattern);
        } else {
            System.out.println("podałeś zły format daty");
        }
    }

    private void showTimeInDifferentZones(LocalDateTime createdDate, DateTimeFormatter datePattern) {
        ZonedDateTime ldtZoned = createdDate.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime londonZoned = ldtZoned.withZoneSameInstant(ZoneId.of("Europe/London"));
        ZonedDateTime losAngelesZoned = ldtZoned.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
        ZonedDateTime sydneyZoned = ldtZoned.withZoneSameInstant(ZoneId.of("Australia/Sydney"));

        String currentTime = ldtZoned.format(datePattern);
        String utcTime = utcZoned.format(datePattern);
        String londonTime = londonZoned.format(datePattern);
        String losAngelesTime = losAngelesZoned.format(datePattern);
        String sydneyTime = sydneyZoned.format(datePattern);

        System.out.println("Czas lokalny: " + currentTime);
        System.out.println("UTC: " + utcTime);
        System.out.println("Londyn: " + londonTime);
        System.out.println("Los Angeles: " + losAngelesTime);
        System.out.println("Sydney: " + sydneyTime);
    }

    private DateTimeFormatter choosePattern(String date) {
        DateTimeFormatter dateTimeFormatter = null;
        if (date.charAt(4) == '-' && date.length() > 10) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        } else if (date.charAt(2) == '.') {
            dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        }
        return dateTimeFormatter;
    }

    private String loadDate(Scanner scanner) {
        System.out.println("Podaj datę:");
        return scanner.nextLine();
    }
}
