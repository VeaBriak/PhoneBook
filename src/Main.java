import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**Написать программу, которая будет работать как телефонная книга: если пишем новое имя, просит ввести номер телефона и запоминает его,
 *  если новый номер телефона — просит ввести имя и тоже запоминает. Если вводим существующее имя или номер телефона, программа должна выводить всю информацию о контакте.
 *  При вводе команды LIST программа должна печатать в консоль список всех абонентов в алфавитном порядке с номерами.**/

    public class Main {
        private static final Pattern PHONE_PATTERN = Pattern.compile("(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?");
        private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-z]*( [A-Z][a-z]*)?");

        public static boolean isPhone(String string) {
            return PHONE_PATTERN.matcher(string).matches();
        }

        public static boolean isName(String string) {
            return NAME_PATTERN.matcher(string).matches();
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            TreeMap<String, String> phoneBook = new TreeMap<>();

            for (; ; ) {
                System.out.println("Please, type command or contact details: ");
                String command = scanner.nextLine().trim();
                if (command.equals("LIST")) {
                    System.out.println("Contact list:");
                    for (String name : phoneBook.keySet()) {
                        System.out.println(phoneBook.get(name) + " - " + "+7" + name);
                    }
                } else if (isPhone(command)) {
                    String number = command.replaceAll("[^0-9]+", "").substring(1);
                    number = number.replaceFirst("(\\d{3})(\\d{3})(\\d{2})(\\d+)", "$1-$2-$3-$4");
                    if (phoneBook.containsKey(number)) {
                        System.out.println("This number of - " + phoneBook.get(number));
                    } else {
                        System.out.println("Please, type name for this number " + "+7" + number + " :");
                        String name = scanner.nextLine().trim();
                        if (isName(name)) {
                            phoneBook.put(number, name);
                        } else {
                            System.out.println("You entered incorrect contact name " + "+7" + number);
                        }
                    }
                } else if (isName(command)) {
                    boolean found = false;
                    for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                        if (entry.getValue().equals(command)) {
                            System.out.println(command + " has phone: +7" + entry.getKey());
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Nothing found");
                        String name = command;
                        System.out.println("Please, type a number for the name: " + name);
                        command = scanner.nextLine().trim();
                        if (command.equals("")) {

                        } else {
                            String number = command.replaceAll("[^0-9]+", "").substring(1);
                            number = number.replaceFirst("(\\d{3})(\\d{3})(\\d{2})(\\d+)", "$1-$2-$3-$4");
                            if (isPhone(number) == false) {
                                System.out.println("Invalid number");
                            } else if (phoneBook.containsKey(number)) {
                                System.out.println("Occupied number");
                            } else {
                                phoneBook.put(number, name);
                            }
                        }
                    }
                } else {
                    System.out.println("Incorrect contact details");
                }
            }
        }
    }



