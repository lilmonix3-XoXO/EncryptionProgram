import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
  
  public static String xorASCII(String fahrvergnugen1, String fahrvergnugen2){
    int[] binary1 = DanielConversionUtil.asciiToBinary(fahrvergnugen1);
    int[] binary2 = DanielConversionUtil.asciiToBinary(fahrvergnugen2);
    int[] binary3 = xorBinary(binary1, binary2);
    String xor = DanielConversionUtil.binaryToHex(binary3);
    return xor;
  }
  
  public static String xorASCII2(String fahrvergnugen1, String fahrvergnugen2){
    int[] binary1 = DanielConversionUtil.asciiToBinary(fahrvergnugen1);
    int[] binary2 = DanielConversionUtil.asciiToBinary(fahrvergnugen2);
    int[] binary3 = xorBinary(binary1, binary2);
    String xor = DanielConversionUtil.binaryToASCII(binary3);
    return xor;
  }

  public static String xorHex(String fahrvergnugen1, String fahrvergnugen2) {
    String xor = null;
    if (fahrvergnugen1.length() != fahrvergnugen2.length()) {
      System.out
          .println("Please do not enter values of unequal length. Encrypt-O-Mat does not like it when you do that.");
      return null;
    } else {
      int[] binaryFirst = DanielConversionUtil.hexToBinary(fahrvergnugen1);
      int[] binarySecond = DanielConversionUtil.hexToBinary(fahrvergnugen2);
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
    String fileDir = "C:/Users/Cindy Boortz/Desktop/Daniel/";
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
  
  public static List<String> readFileLines(String pepe, String filename) {
    pepe = "n";
    List<String> lines = new ArrayList<String>();
    String fileDir = "C:/Users/mii595/Desktop/Items/";
    File file = Paths.get(fileDir + filename).toFile();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line = "";
      while ((line = reader.readLine()) != null) {
        lines.add(line);
        findKey(line, pepe);
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

  public static int findKey(String fahrvergnugen, String pepe) {  
    int key = 0;
    int highScore = 0;
    String truePhrase = null;
    for (int i = 0; i < 256; i++) {
      String plainText = null;
      StringBuffer sb = new StringBuffer();
      int[] keyBinary = DanielConversionUtil.integerToBinary(i);
      for (int j = 0; j < fahrvergnugen.length(); j += 2) {
        StringBuffer sb1 = new StringBuffer();
        String hexLetters = null;
        for (int k = 0; k < 2; k++) {
          hexLetters = sb1.append(Character.toString(fahrvergnugen.charAt(j + k))).toString();
        }
        int[] letterBinary = DanielConversionUtil.hexToBinary(hexLetters);
        int[] xorBinary = xorBinary(letterBinary, keyBinary);
        plainText = sb.append(DanielConversionUtil.binaryToASCII(xorBinary)).toString();     
      }
      if (pepe.contains("y")) {
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
      if (!pepe.toLowerCase().contains("y")) {
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
      //String etaoinShrdlu = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
      String etaoinShrdlu = "ETAOIN SHRDLU etaoin shrdlu";
      if (etaoinShrdlu.indexOf(x) != -1)
        score++;
      if (etaoinShrdlu.indexOf(x) == -1)
        score--;
      //if (((x >= 1) && (x <= 9)) || ((x >= 11) && (x <= 12)) || ((x >= 14) && (x <= 31)))
        //score-=2;
        //score -= 500;
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

  public static void main(String[] args) {
    // String write = null;
    // TestMethods ergh = new TestMethods();
    // TestMethods.entry(write);
    // System.out.println(TestMethods.entry(write));

    /*
     * static String inputFileDir =
     * "C:\\Development\\TE\\Code\\test-correspondence-outbound-processor\\src\\test\\resources\\inputFiles";
     * //static String inputFileDir2 = inputFileDir.replaceAll("\\", "/");
     * static String inputFile = "tg114";
     * 
     * public static void main(String[] args) { File[] inputFiles =
     * Paths.get(inputFileDir).toFile() .listFiles((dir, filename) ->
     * //filename.matches("tg[0-9]{3}.[0-9]{1}.txt"));
     * filename.matches(inputFile + ".[0-9]{1}.txt"));
     * 
     * for (int i = 0; i < inputFiles.length; i++) { System.out.println(
     * "inputFile: " + inputFiles[i] ); } } }
     */
    /*
     * public static void main(String[] args) { String line =
     * "i.m.sewper.kewl!"; System.out.println(line); List<String> wordList =
     * Arrays.asList(line.split("\\.")); Collections.replaceAll(wordList, "i",
     * "u"); Collections.replaceAll(wordList, "m", "r"); String newLine =
     * StringUtils.collectionToDelimitedString(wordList, ".");
     * System.out.println(newLine); } }
     */

    // System.out.println("Now add filler:");
    /*
     * for (int i = 0; i < binary.length; i++) { System.out.print(binary[i]); }
     * System.out.println("");
     */
    /*
     * for (int i = 0; i < write.length(); i++) { data[i] =
     * Character.getNumericValue(write.charAt(i)); int j = ((i + 1) % 2); double
     * calc = (data[i] * Math.pow(16, j)); sum = sum + calc; if (j == 0) {
     * System.out.println("Sum of Digit[" + i + "] + Digit[" + (i + 1) + "] = "
     * + sum); sum = 0; } }
     */
    // System.out.println("The decimal value of your number is:");
    // System.out.println(sum);
    //char x = 'B';
    //System.out.println((int) x);
    String x = "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";
    String y = DanielConversionUtil.base64ToHex(x);
    String z = DanielConversionUtil.hexToASCII(y);
    System.out.println(z);
  }
}
