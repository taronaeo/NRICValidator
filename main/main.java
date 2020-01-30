package main;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

class NRICValidator {
  public static int sumDigits(String digits) {
    int sum = 0;
    int[] weights = { 2, 7, 6, 5, 4, 3, 2 };

    for (int i = 0; i < digits.length(); i++) {
      sum += Integer.parseInt(String.valueOf(digits.charAt(i))) * weights[i];
    }

    return sum;
  }
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("No arguments specified!");
      return;
    }

    String nric = args[0].trim().toUpperCase();
    String digits = nric.substring(1, 8);
    String prefixLetter = String.valueOf(nric.charAt(0));
    String checkLetter = String.valueOf(nric.charAt(8));

    String[] validPrefixes = { "S", "T", "G", "F" };
    Stream<String> prefixStream = Arrays.stream(validPrefixes);

    if (nric.length() != 9 || prefixStream.noneMatch(prefixLetter::equals) || Double.isNaN(Integer.valueOf(digits))) {
      System.err.println("Invalid NRIC Provided!");
      return;
    }

    System.out.println("NRIC: " + args[0]);
    System.out.println("  First Letter:\t" + prefixLetter);
    System.out.println("  Check Letter:\t" + checkLetter);
    System.out.println("  Digits:\t" + digits);
    System.out.println();

    int checksum = sumDigits(digits);

    if (prefixLetter.equals("T") || prefixLetter.equals("G")) checksum += 4;

    if (prefixLetter.equals("S") || prefixLetter.equals("T")) {
      String[] checksumLetter = {"J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"};

      if (checksumLetter[checksum % 11].equals(checkLetter)) {
        System.out.println("Result: NRIC is valid.");
        return;
      }
    }

    if (prefixLetter.equals("F") || prefixLetter.equals("G")) {
      String[] checksumLetter = {"X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"};

      if (checksumLetter[checksum % 11].equals(checkLetter)) {
        System.out.println("Result: NRIC is valid.");
        return;
      }
    }

    System.out.println("Result: The NRIC is invalid.");
  }
}
