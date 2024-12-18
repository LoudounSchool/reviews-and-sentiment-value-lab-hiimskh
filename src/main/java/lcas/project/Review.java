package lcas.project;

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  //Update this with your Repository Name
  private static final String ABSOLUTE_PATH = "/workspaces/reviews-and-sentiment-value-lab-hiimskh/src/main/resources/";
  
  static{
    try {
      Scanner input = new Scanner(new File(ABSOLUTE_PATH + "cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File(ABSOLUTE_PATH + "positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File(ABSOLUTE_PATH + "negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(ABSOLUTE_PATH + fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
   /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 


  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }


public static double totalSentiment(String fileName) {
  String fileTxt = Review.textToString(fileName);
  double totalSum = 0;

  while(fileTxt.length() > 0) {
    int spaceIndex = fileTxt.indexOf(" ");
    String word;

  if (spaceIndex > -1) {
    word = fileTxt.substring(0, spaceIndex);
    fileTxt = fileTxt.substring(spaceIndex + 1);
  }
    else {
      word = fileTxt;
      fileTxt = "";
    }

    word = Review.removePunctuation(word);

    totalSum += Review.sentimentVal(word);

    
  }
  return totalSum;
}

public static int starRating(String fileName) {
  double totalSentiment = totalSentiment(fileName);

  if (totalSentiment < -10) {
    return 0;
  }

  else if (totalSentiment < 0) {
    return 1;
  }

  else if (totalSentiment < 10) {
    return 2;
  }

  else if (totalSentiment < 20) {
    return 3;
  }
  else {
    return 4;
  }
  }

  public static String fakeReview(String fileName, boolean makePositive) {
    String fileText = textToString(fileName);
    String fake = "";
    int astIndex = fileText.indexOf("*");

    while (astIndex != -1) {
      int nextSpace = fileText.indexOf(" ", astIndex);
      fake += fileText.substring(0, astIndex);

      String word;
      if (nextSpace == -1) {
        word = fileText.substring(astIndex + 1);
        fileText = "";
      }

      else {
        word = fileText.substring(astIndex+1, nextSpace);
        fileText = fileText.substring(nextSpace+1);
      }

      String newWord;
      if (makePositive) {
        newWord = randomPositiveAdj();
      }
      else {
        newWord = randomNegativeAdj();
      }
      
      String punctuation = getPunctuation(word);
      newWord += punctuation;

      fake += newWord;
      astIndex = fileText.indexOf("*");
    }
    fake += fileText;
    return fake;
  }

public static void main(String[] args) {
  double answer = Review.totalSentiment("SimpleReview.txt");
  System.out.println("The total rating is "  + answer);
  System.out.println("The star rating is " + Review.starRating("SimpleReview.txt"));
  String posFake = Review.fakeReview("SimpleReview.txt", true);
  System.out.println("Here is the positive revised review: \n" + posFake);
  String negFake = Review.fakeReview("SimpleReview.txt", false);
  System.out.println("Here is the negative revised review: \n" + negFake);
}
}


