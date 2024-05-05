package fiscal.service;

import methods.ComputeFiscalCode;
import methods.Town;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static fiscal.utils.FiscalUtil.*;

public class ComputeService {


    private final NameAndSurnameComputationService nameAndSurnameComputationService;

    public ComputeService(NameAndSurnameComputationService nameAndSurnameComputationService) {
        this.nameAndSurnameComputationService = nameAndSurnameComputationService;
    }



    public String computeSurname(String input) {
        String error = "0";
        input = replaceSpecialChars(input);

        if (isAllLetters(input)) {
            StringBuilder result = new StringBuilder();
            input = input.toUpperCase();

            if (input.length() < 3) {
                result = new StringBuilder(input);
                while (result.length() < 3) {
                    result.append("X");
                }
            } else {
                switch (howManyConsonants(input)) {
                    case 0:
                        result.append(nameAndSurnameComputationService.pickFirstThreeVowels(input));
                        break;
                    case 1:
                        result.append(nameAndSurnameComputationService.pickFirstConsonantAndFirstTwoVowels(input));
                        break;
                    case 2:
                        result.append(nameAndSurnameComputationService.pickFirstTwoConsonantsAndFirstVowel(input));
                        break;
                    default:
                        result.append(nameAndSurnameComputationService.pickFirstThreeConsonants(input));
                }
            }
            return result.toString();
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Please insert a valid input in \"Surname\" field.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return error;
        }
    }

    public String computeName(String inputName) {
        String error = "0";
        inputName = replaceSpecialChars(inputName);
        if (isAllLetters(inputName)) {
            StringBuilder result = new StringBuilder();
            inputName = inputName.toUpperCase();

            if (inputName.length() < 3) {
                result = new StringBuilder(inputName);
                while (result.length() < 3)
                    result.append("X");
            } else {
                switch (howManyConsonants(inputName)) {
                    case 0:
                        result.append(nameAndSurnameComputationService.pickFirstThreeVowels(inputName));
                        break;
                    case 1:
                        result.append(nameAndSurnameComputationService.pickFirstConsonantAndFirstTwoVowels(inputName));
                        break;
                    case 2:
                        result.append(nameAndSurnameComputationService.pickFirstTwoConsonantsAndFirstVowel(inputName));
                        break;
                    case 3:
                        result.append(nameAndSurnameComputationService.pickFirstThreeConsonants(inputName));
                        break;
                    default:
                        result.append(nameAndSurnameComputationService.pickFirstThirdAndFourthConsonant(inputName));
                }
            }
            return result.toString();

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Please insert a valid input in \"Name\" field.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return error;
        }
    }

    public String computeDateOfBirth(String dayString, String monthString, String yearString, String gender) {
        String yearError = "0", dateError = "0";

        if (isYearValid(yearString)) {
            if (isDateValid(dayString, monthString, yearString)) {
                String result = "";
                try {
                    int day = Integer.parseInt(dayString);
                    int month = Integer.parseInt(monthString);
                    int year = Integer.parseInt(yearString);

                    // get the last 2 digits of the year
                    if (year % 100 >= 10) {
                        result += (year % 100);
                    } else {
                        result = result + 0 + (year % 100);
                    }

                    // get the letter corresponding to the month
                    switch (month) {
                        case 1: {
                            result += "A";
                            break;
                        }
                        case 2: {
                            result += "B";
                            break;
                        }
                        case 3: {
                            result += "C";
                            break;
                        }
                        case 4: {
                            result += "D";
                            break;
                        }
                        case 5: {
                            result += "E";
                            break;
                        }
                        case 6: {
                            result += "H";
                            break;
                        }
                        case 7: {
                            result += "L";
                            break;
                        }
                        case 8: {
                            result += "M";
                            break;
                        }
                        case 9: {
                            result += "P";
                            break;
                        }
                        case 10: {
                            result += "R";
                            break;
                        }
                        case 11: {
                            result += "S";
                            break;
                        }
                        case 12: {
                            result += "T";
                            break;
                        }
                    }
                    switch (gender) {
                        case "f": {
                            result += (day + 40);
                            break;
                        }
                        case "m": {
                            result += (day <= 10 ? "0" + day : day);
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Check numeric input.");
                }
                return result;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid date", "Error", JOptionPane.WARNING_MESSAGE);
                return dateError;
            }
        } else {
            String message =
                    "Please insert a numeric value between 1900 and "
                            + Calendar.getInstance().get(Calendar.YEAR)
                            + " in \"Year\" field.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.WARNING_MESSAGE);
            return yearError;
        }
    }

    public String computeTownOfBirth(String townString) throws IOException {
        List<Town> townList = new ArrayList<>();
        String townCode = "0";
        townString = townString.toUpperCase();
        try (BufferedReader read =
                     new BufferedReader(
                             new InputStreamReader(ComputeFiscalCode.class.getResourceAsStream("/TownCodeList.txt")))) {
            String line = read.readLine();
            String[] town;
            while (line != null) {
                town = line.split(";");
                townList.add(new Town(town[0], town[1]));
                line = read.readLine();
            }
            int i = 0;
            while (i < townList.size()) {
                if (townString.equals(townList.get(i).getTownName())) {
                    townCode = townList.get(i).getTownCode();
                    break;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null, "File was not found", "Error", JOptionPane.WARNING_MESSAGE);
        }

        if (townCode.equals("0")) {
            JOptionPane.showMessageDialog(
                    null, "Town was not found", "Error", JOptionPane.WARNING_MESSAGE);
        }
        return townCode;
    }

    public String computeControlChar(String incompleteFiscalCode) throws InterruptedException {
        String control = "";
        int evenSum = 0, oddSum = 0;
        incompleteFiscalCode = incompleteFiscalCode.toUpperCase();
        if (incompleteFiscalCode.length() == 15) {
            OddThread ot = new OddThread(incompleteFiscalCode, oddSum);
            Thread t1 = new Thread(ot);
            t1.start();
            t1.join();
            oddSum = ot.getOddSum();

            EvenThread et = new EvenThread(incompleteFiscalCode, evenSum);
            Thread t2 = new Thread(et);
            t2.start();
            t2.join();
            evenSum = et.getEvenSum();

            // The remainder of the division is the control character
            int sum = (oddSum + evenSum) % 26;
            switch (sum) {
                case 0: {
                    control = "A";
                    break;
                }
                case 1: {
                    control = "B";
                    break;
                }
                case 2: {
                    control = "C";
                    break;
                }
                case 3: {
                    control = "D";
                    break;
                }
                case 4: {
                    control = "E";
                    break;
                }
                case 5: {
                    control = "F";
                    break;
                }
                case 6: {
                    control = "G";
                    break;
                }
                case 7: {
                    control = "H";
                    break;
                }
                case 8: {
                    control = "I";
                    break;
                }
                case 9: {
                    control = "J";
                    break;
                }
                case 10: {
                    control = "K";
                    break;
                }
                case 11: {
                    control = "L";
                    break;
                }
                case 12: {
                    control = "M";
                    break;
                }
                case 13: {
                    control = "N";
                    break;
                }
                case 14: {
                    control = "O";
                    break;
                }
                case 15: {
                    control = "P";
                    break;
                }
                case 16: {
                    control = "Q";
                    break;
                }
                case 17: {
                    control = "R";
                    break;
                }
                case 18: {
                    control = "S";
                    break;
                }
                case 19: {
                    control = "T";
                    break;
                }
                case 20: {
                    control = "U";
                    break;
                }
                case 21: {
                    control = "V";
                    break;
                }
                case 22: {
                    control = "W";
                    break;
                }
                case 23: {
                    control = "X";
                    break;
                }
                case 24: {
                    control = "Y";
                    break;
                }
                case 25: {
                    control = "Z";
                    break;
                }
            }
        }
        return control;
    }
}