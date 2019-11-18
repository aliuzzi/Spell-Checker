
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class CS241A1 {

    //ConfigFile file = null;
    Properties configFile;
    private String urlString = "https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0";
    Addable struct;
    String inputFile = "input.txt";
    String outputFile = "output.txt";


    void readJazzyFile() {


        URL website = null;
        try {
            website = new URL(urlString);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            String inputLine;

//            readConfigFile(); //read config file
//            getProperty();   //gets data structure tree or trie
//            if (getProperty().equals("trie")) {
//                struct = new Trie();
//            }
//            struct = new Tree();

            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty())
                    struct.add(inputLine.toLowerCase());

            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //reads into the config file
    public void readConfigFile() {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream("a1properties.txt"));

        } catch (Exception eta) {
//            System.err.println("File not found");
//            eta.printStackTrace();
        }


    }

    //gets the trie or tree direction
    public String getProperty() {
        if (this.configFile == null) {
            return "trie";
        }
        String value = this.configFile.getProperty("storage");
        if (value == null) {
            return "trie";
        }
        return value;

    }

//    boolean isWordMispelled(String word) {
//        //checks if word is mispelled, if it is produce three suggestions
//        if (struct.lookUp(word) == true) {
//            System.out.println("Word is spelled correctly");
//            return true;
//        }
//        //todo must output at least one and up to three proposed spellings from the english.0 data.
//        //todo perform manipulations on the word (such as changing the characters around) and test each manipulation to see if its a real word or not, if it is, I present it to the user as a suggestion
//        System.out.println("Word is mispelled.\n");
//        generateWordSuggestions(word);
//
//
//    }
//
//    public String[] generateWordSuggestions(String word) {
//        String[] newWords = new String[3];
//        //todo loop through the prefix that needs to be corrected , get node at the last char of that prefix, then loop through that nodes children to get suggestions
//        //todo where the word fails to match/terminates, recurse through the children nodes until there’s a match with a dictionary word at that charAt(i)
//        if (struct.lookUp(word) == false) {
//            struct.add(word); //maybe not because that will add it
//
//        }
//
//
//    }

    public void setUp(String[] args) {


        readConfigFile(); //read config file
        if (getProperty().equals("tree")) {
            struct = new Tree();
        } else {
            struct = new Trie();
        }
        if (args.length > 0) {
            inputFile = args[0];
            outputFile = args[1];//set args[0] to input file so we can read into this file
        } else {
            System.out.println("No command line arguments found");
        }

        readJazzyFile();
        spellCorrector sc = new spellCorrector(outputFile);
        sc.processInputFileByLine(inputFile, struct);
        sc.writeOutputFile();



    }


    public static void main(String[] args) {
        CS241A1 main = new CS241A1();
        main.setUp(args);



    }
}
