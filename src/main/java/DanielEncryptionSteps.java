package com.capitalone.homeloans.correspondence.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DanielEncryptionSteps {
  
  public static String codeIt(String text, String key){
    StringBuffer sb = new StringBuffer();
    String coded = null;
    for (int i = 0; i < text.length(); i+=key.length()){      
      for (int j = 0; j < key.length(); j++){
        String textLetter = Character.toString(text.charAt(i+j));
        String keyLetter = Character.toString(key.charAt(j));
        coded = sb.append(xorASCII(textLetter, keyLetter)).toString();
      }
    }
    return coded;
  }
  
  public static String decodeIt(String text, String key){
    StringBuffer sb = new StringBuffer();
    String decoded = null;
    String asciiText = DanielConversionUtil.hexToASCII(text);
    for (int i = 0; i < asciiText.length(); i+=key.length()){
      for (int j = 0; j < key.length(); j++){
        if (i+j >= asciiText.length())
          break;
        String textLetter = Character.toString(asciiText.charAt(i+j));
        String keyLetter = Character.toString(key.charAt(j));
        decoded = sb.append(xorASCII2(textLetter, keyLetter)).toString();
      }
    }
    return decoded;
  }
  
  public static String xorASCII(String letterValues1, String letterValues2){
    int[] binary1 = DanielConversionUtil.asciiToBinary(letterValues1);
    int[] binary2 = DanielConversionUtil.asciiToBinary(letterValues2);
    int[] binary3 = xorBinary(binary1, binary2);
    String xor = DanielConversionUtil.binaryToHex(binary3);
    //String xor = DanielConversionUtil.binaryToBase64(binary3);
    return xor;
  }
  
  public static String xorASCII2(String letterValues1, String letterValues2){
    int[] binary1 = DanielConversionUtil.asciiToBinary(letterValues1);
    int[] binary2 = DanielConversionUtil.asciiToBinary(letterValues2);
    int[] binary3 = xorBinary(binary1, binary2);
    String xor = DanielConversionUtil.binaryToASCII(binary3);
    return xor;
  }

  public static String xorHex(String letterValues1, String letterValues2) {
    String xor = null;
    if (letterValues1.length() != letterValues2.length()) {
      System.out
          .println("Error: Values are unequal length.");
      return null;
    } else {
      int[] binaryFirst = DanielConversionUtil.hexToBinary(letterValues1);
      int[] binarySecond = DanielConversionUtil.hexToBinary(letterValues2);
      int xorBinary[] = new int[binaryFirst.length];
      for (int i = 0; i < binaryFirst.length; i++) {
        xorBinary[i] = binaryFirst[i] ^ binarySecond[i];
      }
      System.out.println("");
      xor = DanielConversionUtil.binaryToHex(xorBinary);
    }
    return xor;
  }

  public static int[] xorBinary(int[] binary1, int[] binary2) {
    int[] xorBinary = new int[binary1.length];
    for (int i = 0; i < binary1.length; i++) {
      xorBinary[i] = binary1[i] ^ binary2[i];
    }
    return xorBinary;
  }
  
  public static String readFile(String filename) {
    StringBuffer sb = new StringBuffer();
    String fileDir = "C:/Users/mii595/Desktop/Items/";
    File file = Paths.get(fileDir + filename + ".txt").toFile();
    String text = null;
    int line = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      while ((line = reader.read()) != -1)
        text = sb.append((char) line).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return text;
  }
  
  public static List<String> readFileLines(String indicator, String filename) {
    indicator = "n";
    List<String> lines = new ArrayList<String>();
    String fileDir = "C:/Users/mii595/Desktop/Items/";
    File file = Paths.get(fileDir + filename).toFile();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line = "";
      while ((line = reader.readLine()) != null) {
        lines.add(line);
        findKey(line, indicator);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lines;
  }
  
  public static void writeToFile(String text, String file) {
    FileOutputStream fop = null;
    String fileDir = "C:/Users/mii595/Desktop/Items/";
    byte[] contentInBytes = text.getBytes();
    try {
      fop = new FileOutputStream(fileDir + file + ".txt");
      fop.write(contentInBytes);
      fop.flush();
      fop.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int findKey(String letterValues, String indicator) {  
    int key = 0;
    int highScore = 0;
    String truePhrase = null;
    for (int i = 0; i < 256; i++) {
      String plainText = null;
      StringBuffer sb = new StringBuffer();
      int[] keyBinary = DanielConversionUtil.integerToBinary(i);
      for (int j = 0; j < letterValues.length(); j += 2) {
        StringBuffer sb1 = new StringBuffer();
        String hexLetters = null;
        for (int k = 0; k < 2; k++) {
          hexLetters = sb1.append(Character.toString(letterValues.charAt(j + k))).toString();
        }
        int[] letterBinary = DanielConversionUtil.hexToBinary(hexLetters);
        int[] xorBinary = xorBinary(letterBinary, keyBinary);
        plainText = sb.append(DanielConversionUtil.binaryToASCII(xorBinary)).toString();     
      }
      if (indicator.contains("y")) {
        System.out.println("Key: " + (char)i + "(" + DanielConversionUtil.integerToBinaryString(i) + "):");
        System.out.println(plainText);
      }
      
      int score = getScore(plainText);
      if (score > highScore) {
        highScore = score;
        truePhrase = plainText;
        key = i;
      }
    }
    if (highScore > 0) {
      if (!indicator.toLowerCase().contains("y")) {
        System.out.print((char)key);
        //System.out.println(truePhrase);
      }
    }
    return key;
  }

  public static int getScore(String text) {
    int score = 0;
    for (int i = 0; i < text.length(); i++) {
      char x = text.charAt(i);
      String etaoinShrdlu = "ETAOIN SHRDLU etaoin shrdlu";
      if (etaoinShrdlu.indexOf(x) != -1)
        score++;
      if (etaoinShrdlu.indexOf(x) == -1)
        score--;
    }
    return score;
  }
  
  public static int getScoreOLD(String text) {
    int score = 0;
    for (int i = 0; i < text.length(); i++) {
      char x = text.charAt(i);
      String etaoinShrdlu = "ETAOIN SHRDLU etaoin shrdlu";
      if (etaoinShrdlu.indexOf(x) != -1)
        score++;
      if (etaoinShrdlu.indexOf(x) == -1)
        score--;
      if (((x >= 1) && (x <= 9)) || ((x >= 11) && (x <= 12)) || ((x >= 14) && (x <= 31)))
        score-=2;
    }
    return score;
  }

}
