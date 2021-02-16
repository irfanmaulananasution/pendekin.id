import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class LinkShortener {
  static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  static HashMap<String, LinkData> linkDataMap = new HashMap<String,LinkData>();
  final static String HOST = "https://pendekin.id/";
  
  public static void main(String[] args) throws IOException {
    Boolean runFlag = true;
    while (runFlag) {
      try {
        String input = in.readLine();
        String output = processInput(input);
        System.out.println(output);
      }
      catch (Exception e) {
        System.out.println("Error: unknown error occured");
      }
      
    }
  }
  
  public static String processInput(String input) {
    String[] inputs = input.split("\\?");
    String command = inputs[0];
    HashMap<String, String> parameters = convertToHashMap(inputs[1]);
    String result="";
    switch (command) {
      case "/shorten":
        result = shorten(parameters.get("url"), parameters.get("short_path"));
        break;
      case "/redirect":
        result= redirect(parameters.get("url"));
        break;
      case "/delete":
        result= delete(parameters.get("url"));
        break;
      case "/count":
        result= count(parameters.get("url"));
        break;
    }
    return result;
  }
  
  public static String shorten(String fullUrl, String shortPath) {
    if(linkDataMap.get(shortPath)!=null) {
      return String.format("Error: short_path:%s taken", shortPath);
    }
    if (shortPath==null) {
      shortPath = getRandomShortPath();
    }
    LinkData linkData = new LinkData(shortPath, fullUrl);
    linkDataMap.put(shortPath, linkData);
    
    return HOST+shortPath;
  }
  
  public static String redirect(String shortPath) {
    shortPath = shortPath.replace(HOST, "");
    if(linkDataMap.get(shortPath)==null) {
      return String.format("Error: %s not found", shortPath);
    }
    LinkData linkData = linkDataMap.get(shortPath);
    String fullUrl = linkData.redirect();
    return fullUrl; 
  }
  
  public static String delete(String shortPath) {
    shortPath = shortPath.replace(HOST, "");
    if(linkDataMap.get(shortPath)==null) {
      return String.format("Error: %s not found", shortPath);
    }
    linkDataMap.remove(shortPath);
    return "OK";
  }
  
  public static String count(String shortPath) {
    shortPath = shortPath.replace(HOST, "");
    if(linkDataMap.get(shortPath)==null) {
      return String.format("Error: %s not found", shortPath);
    }
    LinkData linkData = linkDataMap.get(shortPath);
    String count = String.valueOf(linkData.getRedirectCount());
    return count; 
  }
  
  public static String getRandomShortPath() {
    String shortPath="";
    do {
      shortPath = getSaltString();
    }
    while (linkDataMap.get(shortPath)!=null);
    return shortPath;
  }
  
  //https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
  protected static String getSaltString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 6) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }
    
  public static HashMap<String, String> convertToHashMap(String toConvert) {
    HashMap <String, String> result = new HashMap<String, String>();
    String[] parameters = toConvert.split("&");
    for(int parameterIdx=0; parameterIdx<parameters.length; parameterIdx++) {
      String[] tmp = parameters[parameterIdx].split("=");
      result.put(tmp[0], tmp[1]);
    }
    return result;
  }
  
  public static String arrayToString(String[] array) {
    String string = "[";
    for(int element=0; element<array.length; element++) {
      string = string + array[element] + ", ";
    }
    return string+"]";
  }  
}

class LinkData {
  private String shortPath;
  private String fullUrl;
  private int redirectCount = 0;
  
  LinkData(String shortPath, String fullUrl) {
    this.shortPath = shortPath;
    this.fullUrl = fullUrl;
  }
  
  public String getShortPath() {
    return this.shortPath;
  }
  
  public String getFullUrl() {
    return this.fullUrl;
  }
  
  public String redirect() {
    this.redirectCount++;
    return this.fullUrl;
  }
  
  public int getRedirectCount() {
    return this.redirectCount;
  }
}