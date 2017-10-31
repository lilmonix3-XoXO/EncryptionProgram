import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

public class DanielEncryptionMain {

  /*
   * SAMPLE TEST DATA Convert hex to base64: sample hex string =
   * 49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d
   * sample output should =
   * SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t Fixed XOR:
   * hex string 1 = 1c0111001f010100061a024b53535009181c hex string 2 =
   * 686974207468652062756c6c277320657965 sample output should =
   * 746865206b696420646f6e277420706c6179 Single Byte XOR sample code:
   * 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
   */

  public static String entry(Scanner br) {
    String write = null;
    write = br.nextLine();
    return write;
  }

  public static int[] getNewBinary(String fahrvergnugen, String option) {
    int fillerLength = fahrvergnugen.length() * 8;
    int[] binary = new int[fillerLength];
    int j = 0;
    int[] data = new int[fahrvergnugen.length()];
    for (int i = 0; i < fahrvergnugen.length(); i++) {
      data[i] = (int) (fahrvergnugen.charAt(i));
      for (int k = 7; k > -1; k--) {
        if (data[i] >= (Math.pow(2, k))) {
          binary[j] = 1;
          data[i] = (int) (data[i] - (Math.pow(2, k)));
        } else {
          binary[j] = 0;
        }
        j++;
      }
    }
    return binary;
  }

  public static int[] getBinary(String fahrvergnugen, String option) {
    int fillerLength = 0;
    int[] binary = new int[fillerLength];
    if ((option.contains("1") || (option.contains("3")))) {
      if (((fahrvergnugen.length() * 4) % 6) == 0) {
        fillerLength = (fahrvergnugen.length() * 4);
      } else {
        fillerLength = ((fahrvergnugen.length() * 4) + ((6 - (fahrvergnugen.length() * 4) % 6)));
      }
    } else {
      fillerLength = (fahrvergnugen.length() * 4);
    }
    if ((option.contains("x")) && (!fahrvergnugen.matches("[a-fA-F0-9]{1,99}"))) {
      System.out.println("Sorry, your entry is invalid. Value must contain only characters 0-9 or A-F.");
      return binary;
    } else {
      int j = 0;
      int[] data = new int[fahrvergnugen.length()];
      binary = new int[fillerLength];
      for (int i = 0; i < fahrvergnugen.length(); i++) {
        data[i] = Character.getNumericValue(fahrvergnugen.toLowerCase().charAt(i));
        for (int k = 3; k > -1; k--) {
          if (data[i] >= (Math.pow(2, k))) {
            binary[j] = 1;
            data[i] = (int) (data[i] - (Math.pow(2, k)));
          } else {
            binary[j] = 0;
          }
          System.out.print(binary[j]);
          j++;
        }
      }
      if ((option.contains("1") || (option.contains("4")))) {
        if (option.contains("1")) {
          for (j = (fahrvergnugen.length() * 4); j < fillerLength; j++) {
            binary[j] = 0;
          }
        }
        if (option.contains("4")) {
          for (j = 0; j < (fahrvergnugen.length() * 4); j++) {
            binary[j + (fillerLength - (fahrvergnugen.length() * 4))] = binary[j];
            if (j < (fillerLength - (fahrvergnugen.length() * 4))) {
              binary[j] = 0;
              // System.out.print(binary[j]);
            }
          }

        }
        for (int i = 0; i < binary.length; i++) {
          // System.out.print(binary[i]);
        }
        System.out.print("");
        DanielConversionUtil.binaryToBase64(binary);
      }
      return binary;
    }
  }

  public static String convertToHex(int[] binary) {
    String hex = null;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < binary.length; i = i + 4) {
      int four = 0;
      for (int j = 3; j > -1; j--) {
        binary[(i + 3) - j] = (int) (binary[(i + 3) - j] * (Math.pow(2, j)));
        four = binary[i] + binary[i + 1] + binary[i + 2] + binary[i + 3];
      }
      switch (four) {
      case 0:
        hex = sb.append("0").toString();
        break;
      case 1:
        hex = sb.append("1").toString();
        break;
      case 2:
        hex = sb.append("2").toString();
        break;
      case 3:
        hex = sb.append("3").toString();
        break;
      case 4:
        hex = sb.append("4").toString();
        break;
      case 5:
        hex = sb.append("5").toString();
        break;
      case 6:
        hex = sb.append("6").toString();
        break;
      case 7:
        hex = sb.append("7").toString();
        break;
      case 8:
        hex = sb.append("8").toString();
        break;
      case 9:
        hex = sb.append("9").toString();
        break;
      case 10:
        hex = sb.append("a").toString();
        break;
      case 11:
        hex = sb.append("b").toString();
        break;
      case 12:
        hex = sb.append("c").toString();
        break;
      case 13:
        hex = sb.append("d").toString();
        break;
      case 14:
        hex = sb.append("e").toString();
        break;
      case 15:
        hex = sb.append("f").toString();
        break;
      }
    }
    return hex;
  }

  public static void findKey(String code, String option, String pepe) {
    String binary = null;
    StringBuffer sbHex = new StringBuffer();
    int highScore = 0;
    String phrase = null;
    String truePhrase = null;
    String key = null;
    char ascii = 0;
    if (!option.contains("6")) {
      for (int i = 0; i < code.length(); i++) {
        int k = Character.getNumericValue(code.charAt(i));
        String out = null;
        StringBuffer sb1 = new StringBuffer();
        for (int j = 0; j < (4 - Integer.toBinaryString(k).length()); j++) {
          out = sb1.append("0").toString();
        }
        out = sb1.append(Integer.toBinaryString(k)).toString();
        binary = sbHex.append(out).toString();
      }
    }
    if (option.contains("6.5"))
      binary = code;

    if (binary.length() % 8 != 0) {
      for (int i = 0; i < (binary.length() + ((8 - binary.length() % 8))); i++) {
        binary = sbHex.append("0").toString();
      }
    }
    List<String> decoded = xorBinary(binary, pepe);
    if (!option.contains("5")) {
      for (int i = 0; i < 256; i++) {
        phrase = decoded.get(i);
        int score = getScore(phrase);
        if (score > highScore) {
          highScore = score;
          truePhrase = phrase;
          ascii = ((char) i);
          key = getKey(i);
        }
      }
      if (highScore > 0) {
        if (!pepe.toLowerCase().contains("y")) {
          System.out.println("Line: " + code);
          System.out.println("Possible valid text found for key '" + ascii + "' (" + key + "):");
          System.out.println(truePhrase);
          System.out.println("");
        }
      }
    }
  }

  public static List<String> xorBinary(String binary, String pepe) {
    String xorBinary = null;
    List<String> decodedText = new ArrayList<String>();
    for (int i = 0; i < 256; i++) {
      String key = getKey(i);
      if (pepe.toLowerCase().contains("y")) {
        System.out.println("key: '" + (char) i + "' (" + key + ")");
      }
      StringBuffer sbXOR = new StringBuffer();
      for (int j = 0; j < binary.length(); j = j + 8) {
        for (int k = 0; k < 8; k++) {
          int binaryDigit = Character.getNumericValue(binary.charAt(j + k));
          int keyDigit = Character.getNumericValue(key.charAt(k));
          int xor = binaryDigit ^ keyDigit;
          xorBinary = sbXOR.append(xor).toString();
        }
      }
      String decoded = getASCII(xorBinary, pepe);
      decodedText.add(decoded);
    }
    return decodedText;
  }

  public static String getKey(int i) {
    String key = null;
    StringBuffer sbKey = new StringBuffer();
    for (int j = 0; j < (8 - Integer.toBinaryString(i).length()); j++) {
      key = sbKey.append("0").toString();
    }
    key = sbKey.append(Integer.toBinaryString(i)).toString();
    return key;
  }

  public static String getASCII(String xorBinary, String pepe) {
    String asciiText = null;
    StringBuffer sb = new StringBuffer();
    for (int j = 0; j < xorBinary.length(); j = j + 8) {
      int[] xorByte = new int[xorBinary.length()];
      for (int k = 0; k < 8; k++) {
        xorByte[k + j] = Character.getNumericValue(xorBinary.charAt(j + k));
        // System.out.print((char) xorByte[k + j]);
      }
      int eight = 0;
      for (int k = 7; k > -1; k--) {
        xorByte[(j + 7) - k] = (int) (xorByte[(j + 7) - k] * (Math.pow(2, k)));
      }
      eight = xorByte[j] + xorByte[j + 1] + xorByte[j + 2] + xorByte[j + 3] + xorByte[j + 4] + xorByte[j + 5]
          + xorByte[j + 6] + xorByte[j + 7];
      asciiText = sb.append((char) eight).toString();
    }
    if (pepe.toLowerCase().contains("y")) {
      System.out.print(asciiText);
      System.out.println("");
    }
    return asciiText;
  }

  public static int getScore(String asciiText) {
    int score = 0;
    for (int i = 0; i < asciiText.length(); i++) {
      char x = asciiText.charAt(i);
      String etaoinShrdlu = "ETAOIN SHRDLU etaoin shrdlu";
      if (etaoinShrdlu.indexOf(x) != -1) {
        score = score + 1;
      }
      if (etaoinShrdlu.indexOf(x) == -1) {
        score = score - 1;
      }
      if ((x >= 1) && (x <= 31)) {
        score = score - 2;
      }
    }
    // System.out.println(score);
    return score;
  }

  public static String doTheNewThing(String file, String key, String option) {
    int n;
    if (option.contains("5.5"))
      n = 1;
    else
      n = 8;
    String displayIt = null;
    String stringFlav = null;
    StringBuffer sb = new StringBuffer();
    int[] keyChar = new int[key.length() * 8];
    int[] flav = new int[file.length() * n];
    if (option.contains("5.5")) {
      for (int i = 0; i < file.length(); i++)
        flav[i] = Integer.valueOf(String.valueOf(file.charAt(i)));
    } else {
      flav = getNewBinary(file, option);
    }
    for (int w = 0; w < flav.length; w += 0) {
      for (int z = 0; z < key.length(); z++) {
        keyChar = getNewBinary(String.valueOf(key.charAt(z)), option);
        for (int q = 0; q < keyChar.length; q++) {
          if (w + q >= flav.length)
            break;
          flav[w + q] = flav[w + q] ^ keyChar[q];
        }
        w = w + keyChar.length;
      }
    }
    if (!option.contains("5.5")) {
      displayIt = convertToHex(flav);
    } else {
      for (int i = 0; i < flav.length; i++) {
        stringFlav = sb.append(flav[i]).toString();
      }
      displayIt = getASCII(stringFlav, option);
    }
    System.out.println(displayIt);
    return displayIt;
  }

  public static void encryptTheFile(String line, String file, String option) {
    FileOutputStream fop = null;
    String fileDir = "C:/Users/mii595/Desktop/Items/";
    // File writeFile = Paths.get(fileDir + file).toFile();
    byte[] contentInBytes = line.getBytes();
    try {
      fop = new FileOutputStream(fileDir + file);
      fop.write(contentInBytes);
      fop.flush();
      fop.close();
      System.out.println("");
      if (option.contains("5.5"))
        System.out.println("File decryption successful");
      else
        System.out.println("File encryption successful");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void doTheThing(List<String> file, List<String> key, String option) {
    int[] keyChar = new int[key.size() * 8];
    for (String x : file) {
      int[] flav = new int[x.length() * 8];
      System.out.println("text binary");
      flav = getNewBinary(x, option);
      for (int w = 0; w < flav.length; w += 0) {
        for (String y : key) {

          for (int z = 0; z < y.length(); z++) {
            // System.out.println(y.length());
            // if (w + z == flav.length)
            // break;
            System.out.println("keychar binary");
            keyChar = getNewBinary(String.valueOf(y.charAt(z)), option);
            for (int q = 0; q < keyChar.length; q++) {
              if (w + q >= flav.length)
                break;
              System.out.print("now xor: ");
              flav[w + q] = flav[w + q] ^ keyChar[q];
              System.out.println(flav[w + q]);

            }
            w = w + keyChar.length;
          }
        }
      }
      String displayIt = convertToHex(flav);
      System.out.println(displayIt);
    }
  }

  public static float hamming(int[] block1, int[] block2, int keySize) {
    float hammingVal = 0;
    for (int i = 0; i < keySize * 8; i++) {
      if ((block1[i] ^ block2[i]) == 1)
        hammingVal++;
    }
    hammingVal = hammingVal / (float) keySize;
    return hammingVal;
  }

  public static float[] findTheKeySizes(int[] binary) {
    float[] keySize = new float[41];
    for (int tryKeySize = 2; tryKeySize < 41; tryKeySize++) {
      int numberOfBlocks = binary.length / (tryKeySize * 8);
      float[] distance = new float[numberOfBlocks];
      int j = 0;
      distance[j] = 0;
      int[] block1 = new int[tryKeySize * 8];
      int[] block2 = new int[tryKeySize * 8];
      int q = 0;
      for (int ctr = 0; ctr < numberOfBlocks - 1; ctr++) {
        for (int i = 0; i < tryKeySize * 8; i++)
          block1[i] = binary[i];
        for (int i = 0; i < tryKeySize * 8; i++) {
          block2[i] = binary[(tryKeySize * 8) + q];
          q++;
        }
        distance[j] = hamming(block1, block2, tryKeySize);
        j++;
      }
      float hammingVal = 0;
      for (int i = 0; i < distance.length; i++)
        hammingVal = hammingVal + distance[i];
      hammingVal = hammingVal / (float) distance.length;
      keySize[tryKeySize] = hammingVal;
      //System.out.println("keySize [" + tryKeySize + "] hamming distance = " + keySize[tryKeySize]);
    }
    return keySize;
  }

  public static int whichOne(float[] keySizes, int keySize) {
    keySizes[keySize] = 100;
    float lowNumber = keySizes[2];
    for (int i = 2; i < 41; i++) {
      if (keySizes[i] <= lowNumber) {
        lowNumber = keySizes[i];
        keySize = i;
      }
    }
    return keySize;
  }

  public static String solveIt(String ascii, int keySize, String pepe) {
    String key = null;
    System.out.println("Attempting key size " + keySize + ":");
    List<String> blocks = new ArrayList<String>();
    for (int i = 0; i < ascii.length(); i += keySize) {
      if (i > (ascii.length() - keySize))
        break;
      StringBuffer sb = new StringBuffer();
      String block = null;
      for (int k = 0; k < keySize; k++) {
        block = sb.append(Character.toString(ascii.charAt(i + k))).toString();
      }
      blocks.add(block);
    }
    System.out.print("'");
    List<String> transposed = transpose(blocks, keySize);
    pepe = "n";
    StringBuffer sb = new StringBuffer();
    for (String block : transposed) {
      key = sb.append((char) DanielEncryptionSteps.findKey(block, pepe)).toString();
    }
    System.out.print("'");
    System.out.println();
    return key;
  }

  public static List<String> transpose(List<String> blocks, int keySize) {
    List<String> newBlocks = new ArrayList<String>();
    for (int i = 0; i < keySize; i++) {
      StringBuffer sb = new StringBuffer();
      String transposedBlock = null;
      for (String block : blocks) {
        transposedBlock = sb.append(Character.toString(block.charAt(i))).toString();
      }
      String transposedHex = DanielConversionUtil.asciiToHex(transposedBlock);
      newBlocks.add(transposedHex);
    }
    return newBlocks;
  }

  public static String options(Scanner br) {
    String pepe = "y";
    String option = DanielEncryptionMain.entry(br);
    String fahrvergnugen = null;
    String keyFile = null;
    switch (option) {
    case "1":
      System.out.println("Enter a hex value:");
      fahrvergnugen = DanielEncryptionMain.entry(br);
      String base64 = DanielConversionUtil.hexToBase64(fahrvergnugen);
      System.out.println("Here is the base 64 conversion:");
      System.out.println(base64);
      System.out.println("");
      break;
    case "1.5":
      System.out.println("Enter a base 64 value:");
      fahrvergnugen = DanielEncryptionMain.entry(br);
      String hexadecimal = DanielConversionUtil.base64ToHex(fahrvergnugen);
      System.out.println("Here is the hex conversion:");
      System.out.println(hexadecimal);
      System.out.println("");
      break;
    case "2":
      System.out.println("Enter hex value 1:");
      String fahrvergnugen1 = DanielEncryptionMain.entry(br);
      System.out.println("Enter hex value 2:");
      String fahrvergnugen2 = DanielEncryptionMain.entry(br);
      String xor = DanielEncryptionSteps.xorHex(fahrvergnugen1, fahrvergnugen2);
      System.out.println("Here is the xor'd value:");
      System.out.println(xor);
      System.out.println("");
      break;
    case "3":
      pepe = "n";
      System.out.println("Enter the hex-encoded string:");
      fahrvergnugen = DanielEncryptionMain.entry(br);
      DanielEncryptionSteps.findKey(fahrvergnugen, pepe);
      System.out.println("");
      System.out.println("");
      System.out.println("Would you like to see the output for all keys?");
      pepe = DanielEncryptionMain.entry(br);
      if (pepe.toLowerCase().contains("y")) {
        DanielEncryptionSteps.findKey(fahrvergnugen, pepe);
        System.out.println("");
      }
      break;
    case "4":
      fahrvergnugen = "challenge4.txt";
      DanielEncryptionSteps.readFileLines(pepe, fahrvergnugen);
      break;
    case "5":
      System.out.println("");
      System.out.println("Enter the name of the text file you want to encrypt:");
      fahrvergnugen = DanielEncryptionMain.entry(br);
      System.out.println("Now enter the name of key file:");
      keyFile = DanielEncryptionMain.entry(br);
      String keylet = DanielEncryptionSteps.readFile(keyFile);
      System.out.println("Key = " + keylet);
      String filet = DanielEncryptionSteps.readFile(fahrvergnugen);
      System.out.println("File text =");
      System.out.println(filet);
      System.out.println("Here is the encryption:");
      String encryption = DanielEncryptionSteps.codeIt(filet, keylet);
      System.out.println(encryption);
      DanielEncryptionSteps.writeToFile(encryption, fahrvergnugen);
      System.out.println("Encryption Successful");
      break;
    case "5.5":
      System.out.println("");
      System.out.println("Enter the name of the file you want to decrypt:");
      fahrvergnugen = DanielEncryptionMain.entry(br);
      System.out.println("Now enter the name of key file:");
      keyFile = DanielEncryptionMain.entry(br);
      String encodedfile = DanielEncryptionSteps.readFile(fahrvergnugen);
      encodedfile = DanielConversionUtil.base64ToHex(encodedfile);
      String key = DanielEncryptionSteps.readFile(keyFile);
      System.out.println("Here is the decrypted message:");
      String decryption = DanielEncryptionSteps.decodeIt(encodedfile, key);
      System.out.println(decryption);
      DanielEncryptionSteps.writeToFile(decryption, fahrvergnugen);
      break;
    case "6":
      String file = "decrypt_this";
      String base = DanielEncryptionSteps.readFile(file);
      String hex = DanielConversionUtil.base64ToHex(base);
      String ascii = DanielConversionUtil.hexToASCII(hex);
      int[] binaryText = DanielConversionUtil.hexToBinary(hex);
      float[] keySizes = findTheKeySizes(binaryText);
      int keySize = 0;
      keySize = whichOne(keySizes, keySize);
      String keyString = solveIt(ascii, keySize, pepe);
      System.out.println("Press any key to read decrypted message.");
      pepe = entry(br);
      pepe = "n";
      decryption = DanielEncryptionSteps.decodeIt(hex, keyString);
      System.out.println(decryption);
      System.out.println("");
      System.out.println("Try a different key size?");
      pepe = entry(br);
      while (pepe.toLowerCase().contains("y")) {
        keySize = whichOne(keySizes, keySize);
        keyString = solveIt(ascii, keySize, pepe);
        System.out.println("Press any key to read decrypted message.");
        pepe = entry(br);
        pepe = "n";
        decryption = DanielEncryptionSteps.decodeIt(hex, keyString);
        System.out.println(decryption);
        System.out.println("");
        System.out.println("Try again with a different key size?");
        pepe = entry(br);
      }
      break;
    default:
      System.out.println(
          "You did not enter a valid option. Please enter the number corresponding to one of the above options.");
      return pepe;
    }
    System.out.println("Anything else? (y/n)");
    pepe = DanielEncryptionMain.entry(br);
    if (pepe.toLowerCase().contains("y")) {
      System.out.println("Please select an option.");
    }
    return pepe;
  }

  public static void main(String[] args) {
    Scanner br = new Scanner(System.in);
    String pepe = "y";
    System.out.println("Encrypt-O-Mat");
    System.out.println("");
    System.out.println("What would you like to do today?");
    System.out.println("");
    System.out.println("1) Convert hexadecimal to base64");
    System.out.println("1.5) Convert base64 to hexadecimal");
    System.out.println("2) XOR 2 hex strings");
    System.out.println("3) Decode single-byte XOR cipher");
    System.out.println("4) Find single-byte XOR from file");
    System.out.println("5) Vigenere encryption");
    System.out.println("5.5) Decrypt Vigenere-encrypted file");
    System.out.println("6) Break Vigenere encryption");
    while (pepe.toLowerCase().contains("y")) {
      pepe = options(br);
    }
    System.out.println("");
    System.out.println("kthxbai.");
    br.close();
  }
}