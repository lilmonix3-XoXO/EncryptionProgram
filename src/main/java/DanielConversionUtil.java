import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DanielConversionUtil {

  public static int[] integerToBinary(int number) {
    int[] binary = new int[8];
    int j = 0;
    for (int k = 7; k > -1; k--) {
      if (number >= (Math.pow(2, k))) {
        binary[j] = 1;
        number = (int) (number - (Math.pow(2, k)));
      } else {
        binary[j] = 0;
      }
      j++;
    }
    return binary;
  }

  public static String integerToBinaryString(int number) {
    String binaryString = null;
    StringBuffer sb = new StringBuffer();
    int[] binary = integerToBinary(number);
    for (int i = 0; i < binary.length; i++)
      binaryString = sb.append(binary[i]).toString();
    return binaryString;
  }

  public static int[] asciiToBinary(String fahrvergnugen) {
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

  public static int[] hexToBinary(String fahrvergnugen) {
    int fillerLength = fahrvergnugen.length() * 4;
    int[] binary = new int[fillerLength];
    int j = 0;
    int[] data = new int[fahrvergnugen.length()];
    for (int i = 0; i < fahrvergnugen.length(); i++) {
      data[i] = Character.getNumericValue(fahrvergnugen.toLowerCase().charAt(i));
      for (int k = 3; k > -1; k--) {
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
  
  public static String asciiToHex(String fahrvergnugen) {
    int[] binary = asciiToBinary(fahrvergnugen);
    String hexadecimal = binaryToHex(binary);
    return hexadecimal;
  }

  public static String hexToBase64(String fahrvergnugen) {
    int[] binary = hexToBinary(fahrvergnugen);
    String base64 = binaryToBase64(binary);
    return base64;
  }

  public static String hexToASCII(String fahrvergnugen) {
    int[] binary = hexToBinary(fahrvergnugen);
    String asciiText = binaryToASCII(binary);
    return asciiText;
  }

  public static String base64ToHex(String fahrvergnugen) {
    int[] binary = base64ToBinary(fahrvergnugen);
    String hexadecimal = binaryToHex(binary);
    if (Character.toString(fahrvergnugen.charAt(fahrvergnugen.length() - 1)).contains("=")
        && Character.toString(hexadecimal.charAt(hexadecimal.length() - 1)).contains("0")) {
      if (hexadecimal.length() % 2 != 0) {
        hexadecimal = hexadecimal.substring(0, hexadecimal.length()-1);
      }
    }
    return hexadecimal;
  }

  public static String integerToHex(int number) {
    int[] binary = integerToBinary(number);
    String hexadecimal = binaryToHex(binary);
    return hexadecimal;
  }

  public static String binaryToASCII(int[] binary) {
    String asciiText = null;
    StringBuffer sb = new StringBuffer();
    for (int j = 0; j < binary.length; j = j + 8) {
      int eight = 0;
      for (int k = 7; k > -1; k--) {
        binary[(j + 7) - k] = (int) (binary[(j + 7) - k] * (Math.pow(2, k)));
        eight = binary[j] + binary[j + 1] + binary[j + 2] + binary[j + 3] + binary[j + 4] + binary[j + 5]
            + binary[j + 6] + binary[j + 7];
      }
      asciiText = sb.append((char) eight).toString();
    }
    return asciiText;
  }

  public static String binaryToHex(int[] binary) {
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

  public static int[] base64ToBinary(String fahrvergnugen) {
    int[] base64Binary = new int[(fahrvergnugen.length() * 6)];
    int digit = 0;
    int j = 0;
    for (int i = 0; i < fahrvergnugen.length(); i++) {
      char digit64 = fahrvergnugen.charAt(i);
      switch (digit64) {
      case 'A':
        digit = 0;
        break;
      case 'B':
        digit = 1;
        break;
      case 'C':
        digit = 2;
        break;
      case 'D':
        digit = 3;
        break;
      case 'E':
        digit = 4;
        break;
      case 'F':
        digit = 5;
        break;
      case 'G':
        digit = 6;
        break;
      case 'H':
        digit = 7;
        break;
      case 'I':
        digit = 8;
        break;
      case 'J':
        digit = 9;
        break;
      case 'K':
        digit = 10;
        break;
      case 'L':
        digit = 11;
        break;
      case 'M':
        digit = 12;
        break;
      case 'N':
        digit = 13;
        break;
      case 'O':
        digit = 14;
        break;
      case 'P':
        digit = 15;
        break;
      case 'Q':
        digit = 16;
        break;
      case 'R':
        digit = 17;
        break;
      case 'S':
        digit = 18;
        break;
      case 'T':
        digit = 19;
        break;
      case 'U':
        digit = 20;
        break;
      case 'V':
        digit = 21;
        break;
      case 'W':
        digit = 22;
        break;
      case 'X':
        digit = 23;
        break;
      case 'Y':
        digit = 24;
        break;
      case 'Z':
        digit = 25;
        break;
      case 'a':
        digit = 26;
        break;
      case 'b':
        digit = 27;
        break;
      case 'c':
        digit = 28;
        break;
      case 'd':
        digit = 29;
        break;
      case 'e':
        digit = 30;
        break;
      case 'f':
        digit = 31;
        break;
      case 'g':
        digit = 32;
        break;
      case 'h':
        digit = 33;
        break;
      case 'i':
        digit = 34;
        break;
      case 'j':
        digit = 35;
        break;
      case 'k':
        digit = 36;
        break;
      case 'l':
        digit = 37;
        break;
      case 'm':
        digit = 38;
        break;
      case 'n':
        digit = 39;
        break;
      case 'o':
        digit = 40;
        break;
      case 'p':
        digit = 41;
        break;
      case 'q':
        digit = 42;
        break;
      case 'r':
        digit = 43;
        break;
      case 's':
        digit = 44;
        break;
      case 't':
        digit = 45;
        break;
      case 'u':
        digit = 46;
        break;
      case 'v':
        digit = 47;
        break;
      case 'w':
        digit = 48;
        break;
      case 'x':
        digit = 49;
        break;
      case 'y':
        digit = 50;
        break;
      case 'z':
        digit = 51;
        break;
      case '0':
        digit = 52;
        break;
      case '1':
        digit = 53;
        break;
      case '2':
        digit = 54;
        break;
      case '3':
        digit = 55;
        break;
      case '4':
        digit = 56;
        break;
      case '5':
        digit = 57;
        break;
      case '6':
        digit = 58;
        break;
      case '7':
        digit = 59;
        break;
      case '8':
        digit = 60;
        break;
      case '9':
        digit = 61;
        break;
      case '+':
        digit = 62;
        break;
      case '/':
        digit = 63;
        break;
      case '=':
        break;
      default:
        break;
      }
      for (int k = 5; k > -1; k--) {
        if (digit >= (Math.pow(2, k))) {
          base64Binary[j] = 1;
          digit = (int) (digit - (Math.pow(2, k)));
        } else {
          base64Binary[j] = 0;
        }
        j++;
      }
    }
    return base64Binary;
  }

  public static String binaryToBase64(int[] binary) {
    String base64 = null;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < binary.length; i = i + 6) {
      int six = 0;
      for (int j = 5; j > -1; j--) {
        binary[(i + 5) - j] = (int) (binary[(i + 5) - j] * (Math.pow(2, j)));
      }
      six = binary[i] + binary[i + 1] + binary[i + 2] + binary[i + 3] + binary[i + 4] + binary[i + 5];
      switch (six) {
      case 0:
        base64 = sb.append("A").toString();
        break;
      case 1:
        base64 = sb.append("B").toString();
        break;
      case 2:
        base64 = sb.append("C").toString();
        break;
      case 3:
        base64 = sb.append("D").toString();
        break;
      case 4:
        base64 = sb.append("E").toString();
        break;
      case 5:
        base64 = sb.append("F").toString();
        break;
      case 6:
        base64 = sb.append("G").toString();
        break;
      case 7:
        base64 = sb.append("H").toString();
        break;
      case 8:
        base64 = sb.append("I").toString();
        break;
      case 9:
        base64 = sb.append("J").toString();
        break;
      case 10:
        base64 = sb.append("K").toString();
        break;
      case 11:
        base64 = sb.append("L").toString();
        break;
      case 12:
        base64 = sb.append("M").toString();
        break;
      case 13:
        base64 = sb.append("N").toString();
        break;
      case 14:
        base64 = sb.append("O").toString();
        break;
      case 15:
        base64 = sb.append("P").toString();
        break;
      case 16:
        base64 = sb.append("Q").toString();
        break;
      case 17:
        base64 = sb.append("R").toString();
        break;
      case 18:
        base64 = sb.append("S").toString();
        break;
      case 19:
        base64 = sb.append("T").toString();
        break;
      case 20:
        base64 = sb.append("U").toString();
        break;
      case 21:
        base64 = sb.append("V").toString();
        break;
      case 22:
        base64 = sb.append("W").toString();
        break;
      case 23:
        base64 = sb.append("X").toString();
        break;
      case 24:
        base64 = sb.append("Y").toString();
        break;
      case 25:
        base64 = sb.append("Z").toString();
        break;
      case 26:
        base64 = sb.append("a").toString();
        break;
      case 27:
        base64 = sb.append("b").toString();
        break;
      case 28:
        base64 = sb.append("c").toString();
        break;
      case 29:
        base64 = sb.append("d").toString();
        break;
      case 30:
        base64 = sb.append("e").toString();
        break;
      case 31:
        base64 = sb.append("f").toString();
        break;
      case 32:
        base64 = sb.append("g").toString();
        break;
      case 33:
        base64 = sb.append("h").toString();
        break;
      case 34:
        base64 = sb.append("i").toString();
        break;
      case 35:
        base64 = sb.append("j").toString();
        break;
      case 36:
        base64 = sb.append("k").toString();
        break;
      case 37:
        base64 = sb.append("l").toString();
        break;
      case 38:
        base64 = sb.append("m").toString();
        break;
      case 39:
        base64 = sb.append("n").toString();
        break;
      case 40:
        base64 = sb.append("o").toString();
        break;
      case 41:
        base64 = sb.append("p").toString();
        break;
      case 42:
        base64 = sb.append("q").toString();
        break;
      case 43:
        base64 = sb.append("r").toString();
        break;
      case 44:
        base64 = sb.append("s").toString();
        break;
      case 45:
        base64 = sb.append("t").toString();
        break;
      case 46:
        base64 = sb.append("u").toString();
        break;
      case 47:
        base64 = sb.append("v").toString();
        break;
      case 48:
        base64 = sb.append("w").toString();
        break;
      case 49:
        base64 = sb.append("x").toString();
        break;
      case 50:
        base64 = sb.append("y").toString();
        break;
      case 51:
        base64 = sb.append("z").toString();
        break;
      case 52:
        base64 = sb.append("0").toString();
        break;
      case 53:
        base64 = sb.append("1").toString();
        break;
      case 54:
        base64 = sb.append("2").toString();
        break;
      case 55:
        base64 = sb.append("3").toString();
        break;
      case 56:
        base64 = sb.append("4").toString();
        break;
      case 57:
        base64 = sb.append("5").toString();
        break;
      case 58:
        base64 = sb.append("6").toString();
        break;
      case 59:
        base64 = sb.append("7").toString();
        break;
      case 60:
        base64 = sb.append("8").toString();
        break;
      case 61:
        base64 = sb.append("9").toString();
        break;
      case 62:
        base64 = sb.append("+").toString();
        break;
      case 63:
        base64 = sb.append("/").toString();
        break;
      }
    }
    if (((binary.length % 24) <= 12) && ((binary.length % 24) != 0)) {
      base64 = sb.append("==").toString();
    } else if (((binary.length % 24) <= 18) && ((binary.length % 24) != 0)) {
      base64 = sb.append("=").toString();
    }
    return base64;
  }
}
