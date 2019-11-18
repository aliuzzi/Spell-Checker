import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class spellCorrector {

    Addable struct;
    Properties configFile;
    String outputPath;


    HashMap<String, Set<String>> output = new LinkedHashMap<>(); //this will store the input word, and a set of the word suggestions
    //todo write to file

    public spellCorrector(String outputPath){
        this.outputPath = outputPath;

    }

    public void processInputFileByLine(String inputFile, Addable struct) {

       Scanner scanner = null;



        try {
            scanner = new Scanner(new FileInputStream(inputFile));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
//                String tempsoundex = soundex(word.toLowerCase());



                HashSet<String> wordset = (HashSet<String>) output.get(word);

                if(wordset==null){
                    wordset = new HashSet<String>();
                    output.put(word, wordset);
                }

                if(struct.lookUp(word.toLowerCase())){

                } else {
                    String suggestion = struct.spellcheck(word.toLowerCase());
//                    String suggestion = soundex(word.toLowerCase());
                    if(suggestion!=null)
                        wordset.add(suggestion);
                }

            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public void writeOutputFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

            for (String currentKey: output.keySet()) {
                Set<String> wordSet = output.get(currentKey);

               if(wordSet.isEmpty()){
                   writer.write(currentKey + " \n");
               }else{
                   writer.write(currentKey +": ");
                   for(String setWord : wordSet){
                       writer.write(setWord+" ");
                   }
                   writer.write("\n");
               }

            }
            writer.flush();
            writer.close();

        }catch(Exception e){

        }
    }


    /**
     * Soundex algorithm is implemented to index the words with there key paramaters.
     *
     * @param word is string obtained from the dictionary.
     * @return string after considering the soundex algorithm
     */
    public static String soundex(String word){
        String code, previous, soundex;
        code = word.toUpperCase().charAt(0) + "";
        previous = "7";
        for(int i = 1;i < word.length();i++){
            String current = getCode(word.toUpperCase().charAt(i));
            if(current.length() > 0 && !current.equals(previous)){
                code = code + current;
            }
            previous = current;
        }
        soundex = (code + "0000").substring(0, 4);
        return soundex;
    }


    /**
     * This method replace below conversion parameter as per the phonetic algorithm
     * Replace consonants with digits as follows (after the first letter):
     * b, f, p, v => 1
     * c, g, j, k, q, s, x, z => 2
     * d, t => 3
     * l => 4
     * m, n => 5
     * r => 6
     * @param char C of a given string
     * @return an integer as string for the corresponding alphabet
     */
    private static String getCode(char c){
        switch(c){
            case 'B': case 'F': case 'P': case 'V':
                return "1";
            case 'C': case 'G': case 'J': case 'K':
            case 'Q': case 'S': case 'X': case 'Z':
                return "2";
            case 'D': case 'T':
                return "3";
            case 'L':
                return "4";
            case 'M': case 'N':
                return "5";
            case 'R':
                return "6";
            default:
                return "";
        }
    }


    /**
     * Query the spellchecker with a given input string and returns a suggestion.
     * If no word can be suggested, "NO SUGGESTION" will be returned.
     * Complexity: O(n) where n is the length of the input string
     */
    public String spellCheck(String word) {
        int distance = 0;
        int distancetemp = 100;
        String matchingword = null;
        HashSet<String> wordset = (HashSet<String>) output.get(soundex(word.toLowerCase()));
        if (wordset == null){
            return "NO SUGGESTION";
        }
        else if(wordset.contains(word.toLowerCase())){
            return word.toLowerCase();
        }
        else{
            Iterator<String> it = wordset.iterator();
            while(it.hasNext()){
                String words = it.next();
                distance = computeLevenshteinDistance(words, word.toLowerCase());
                if (distancetemp > distance){
                    distancetemp = distance;
                    matchingword = words;
                }
            }
            return matchingword;
        }
    }



    /**
     * Minumum of given numbers
     * @param a is an integer
     * @param b is an integer
     * @param c is an integer
     * @return an interger which is minimum of a b c
     */
    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Has computed the distance between the strings using Levenshtein Distance algorithm which helps
     * in finding out the nearest word to the misspelled one
     * More information of the algorithm can be obtained from the below url
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     * @param str1 is a string
     * @param str2 is a string
     * @return the distance between the strings str1 and str2
     */
    public static int computeLevenshteinDistance(CharSequence str1,
                                                 CharSequence str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++)
            distance[i][0] = i;
        for (int j = 0; j <= str2.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= str1.length(); i++)
            for (int j = 1; j <= str2.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]
                                + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                : 1));

        return distance[str1.length()][str2.length()];
    }





}
