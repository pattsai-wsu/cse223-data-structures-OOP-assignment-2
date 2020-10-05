// Patrick Tsai
// Programming Assignment 2 - Indexer
// CSE 223 M-F 10AM
// Due 5/8/2020 - 8:00 AM
// Description - Indexer class, this class takes in a text file and  creates a HashMap. The HashMap contains a key (word) and an associated LinkedList. The HashMap elment key is a word (uppercase) from the text file, and the LinkedList associated with the key is an integer value of where the word appears in a file. 


import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
//import java.io.FileNotFoundException;

public class Indexer {
  Boolean bool1=new Boolean(true);             //this is an unnecessary step, but it sets a boolean variable to a boolean value to be used within program
  Boolean bool2=new Boolean(false);
  Boolean processSucess=bool2.booleanValue(); // this variable saves a boolean value signifying if the file has been run sucessfully - itial value is false b/c nothing has run

  private HashMap<String,LinkedList<Integer>> index=new HashMap<String, LinkedList<Integer>>();      // declare hash table set to private

  // Constructor - I'm a little unclear on this, I have nothing contained in my constructor. everything seems to work fine. Do I need a constructor if it does nothing?  
  public Indexer() { 
  }

  // processFile method
  public boolean processFile(String filename) {
    File myFile;                               // declare a file var
    myFile=new File(filename);                 // set file var baed on filename
    Scanner sc;                                // declare Scanner

    try {                                      // try/catch - try to scan file
      sc=new Scanner(myFile);                  // scanner to read myFile var file
    }
    catch(Exception except) {                  // try/catch [fail] - if can't scan file
      processSucess=bool2.booleanValue();      // return false bool
      return processSucess;
    }

    int wordIndex=1;                           // try/catch [success] - if can scan file 
    while (sc.hasNext()){                      //process each word of the file
      String word=sc.next();                   //uses scanner to check if there is a "next" word

      word=cleanupWord(word);                  //includes cleaning word

      if (word.length() > 0) {                 //checks to see if the returned word length is greater than 0. this will skip symbols that have been cleaned and returned as words
        addReference(word,wordIndex);          // sends words to an addReference method, which either creates a new HashMap entry, or adds to the linked list already associated with the word
        wordIndex=wordIndex+1;                 //adds 1 to the wordIndex, which keeps track of the location of each word in the file.
      }
    }
  sc.close();                                  //closes scanner
  processSucess=bool1.booleanValue();          // sets processSucess bool to true
  return processSucess;                        //returns true
  }

  // cleanupWord method
  private String cleanupWord(String word) {    // cleanupWord takes a string
      word=word.toUpperCase();                 // then turns it to all uppercase
      String wordClean=new String();           // created a new var to hadle the cleaned word

      int wordLength=word.length();            // store the length of each word as an integer
      int i=0;                                 // create a counter integer
      while (i<wordLength) {                   // start of loop to clean words from file, parses each character, starts at 0 index goes till word length
        if (Character.isLetter(word.charAt(i))==true) {   // if the parsed character is a letter then
          wordClean = wordClean + word.charAt(i);         // it will place the letter at the end of the wordClean string
          i++;                                 // increament counter
        }
        else {
          i++;                                 // else if the parsed character is not a letter, just incement counter
        }
      }
  return(wordClean);                           // return the cleaned word
  }

  // addReference method
  private void addReference(String key,int wordIndex) {       // addREference takes a String (key for HashMap) and integer (location of word)
    if (index.containsKey(key)!=true) {                       // checks for an existing HashMap, if one does not exist
      LinkedList<Integer> wordLoc=new LinkedList<Integer>();  // creates a new Linked List asscoiated with word
      wordLoc.add(wordIndex);                                 // add the word location to the newly created linked list
      index.put(key, wordLoc);
    }
    else {
      index.get(key).addLast(wordIndex);                      // if the hash map already exists, it places the location at the end of the linked list
    }
  return;
  }

  // numberOfInstances method
  public int numberOfInstances(String word) {  // numberOfInstances takes a string
    if (processSucess==false) {                // if the file did not process
      return(-1);                              // it returns the integer -1
    }
    else if (index.containsKey(word)==true) {  // if the hash map exists
      return(index.get(word).size());          // returns the size of the linked list, which is the number of items within the linked list
    }
    else {
      return(0);                               // else it returns 0 if there is no hash map created for the word
    }
  }

  // locationOf method
  public int locationOf(String word, int instanceNum) {  // takes a string (the word) and an integer (which instance of the word you would like to locate)
    if (processSucess==false) {                          // if file has not processed
      return(-1);                                        // return -1
    }
    else if (instanceNum < 0 || instanceNum >= this.numberOfInstances(word)) {  // if the instance number input is less than or greater than the number of instances the word occurs
      return(-1);                                                               // return -1
    }
    else if (index.get(word).get(instanceNum)!=null) {                          // if the instance number entered does not return a null
      return index.get(word).get(instanceNum);                                  // get location of instance from LinkedList based on word
    }
    else {
      return(-1);                                                               // else return -1
    }
  }

  // numberOfWords method
  public int numberOfWords() {                 // this method returns the number of words in the HashMap, or the size of the HashMap
    if (processSucess==false) {                // if the file did not process, then return -1
      return(-1);
    }
    else if (index.isEmpty()==false) {         // if the file did process and the and the HashMap is not empty
      return index.size();                     // return the size of the Hash Map
    }
    else {
      return(-1);                              // else return -1
    }
  }

  // toString method
  public String toString() {                   // toString returns a string output of the HashMap    
    if (processSucess==false) {                // if the file did not process
      return("null");                          // return null
    }
    else if (index.isEmpty()==false) {         // if the file did process and the Hash Map is not empty
      return index.toString();                 // return the default output of the hash map
    }
    else {
      return("-1");                            // else return -1
    }
  }

}
