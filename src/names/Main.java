package names;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        Main m = new Main();
        //Data: Test
        ArrayList<String> topranked = m.giveTopRanked(readFileIn(2008, "ssa_2000s"));
        ArrayList<Integer> firstletter = m.firstLetterAndBabies('F', 'Z', readFileIn(2007, "ssa_complete"));

        //Data: Basic
        ArrayList<Integer> allranks = m.findAllRanks("ssa_2000s", "Emily", 'F');
        ArrayList<String> recentrankednames = m.findRecentRankedName("Jonathan", 'M', 1999, "ssa_complete");
        ArrayList<String> mostpopularoverrange = m.findMostPopularNameOverRangeOfYears(1880, 2018, 'M', "ssa_complete");
        Set<String> mostpopulargirlnames = m.findMostPopularGirlNamesFirstLetter(2100, 2101, "ssa_testdata");
        System.out.println(mostpopulargirlnames);
    }

    /**
     * The below function will return the top ranked male and female name in an ArrayList
     * in the order of [F, M] for the top ranked female and male baby name
     */

    public ArrayList<String> giveTopRanked(ArrayList<String[]> babynames) {
        ArrayList<String> topranked = new ArrayList<String>();
        topranked.add(babynames.get(0)[0]);
        for (int i = 0; i < babynames.size(); i++) {
            String currentgender = babynames.get(i)[1];
            if (currentgender.equals("M")) {
                topranked.add(babynames.get(i)[0]);
                break;
            }
        }
        return topranked;
    }

    /**
     * The below function, given a year, gender, and letter, return how many names start with this letter
     * and how many total babies were born under that name. We return this as an ArrayList in that respective
     * order.
     */

    public ArrayList<Integer> firstLetterAndBabies(char gender, char letter, ArrayList<String[]> babynames) {
        ArrayList<Integer> freqandtotal = new ArrayList<Integer>();
        Integer totalbabies = 0;
        Integer totalfirstletternames = 0;
        for (int j = 0; j < babynames.size(); j++){
            char currentgender = babynames.get(j)[1].charAt(0);
            char startletter = babynames.get(j)[0].charAt(0);
            if (currentgender == gender) {
                if (startletter == letter) {
                    totalfirstletternames++;
                    totalbabies += Integer.parseInt(babynames.get(j)[2]);
                }
            }
        }
        freqandtotal.add(totalfirstletternames);
        freqandtotal.add(totalbabies);
        return freqandtotal;
    }

    /**
     * Helper method to return an ArrayList containing the baby data for a given year. If the year is out of the valid
     * range of 1880-2018, it simply reverts the year to the range it exceeded and let's the user know with a print statement.
     * Ex: if year == 2019, year is set to 2018 and likewise if year == 1879, year is set to 1880 and we continue.
     *
     * THIS IS ALSO WHERE WE CAN EASILY CHANGE THE FILE PATH in the String csvFile variable, however as you will see in the
     * later functions, this can be passed directly to method
     */

    public static ArrayList<String[]> readFileIn(int year, String foldername) {
        if (foldername.equals("ssa_complete")) {
            if (year > 2018 || year < 1880) {
                System.out.println("The year is invalid. Setting to the nearest valid year.");
                if (year > 2018) {
                    year = 2018;
                } else {
                    year = 1880;
                }
            }
        }
        else if (foldername.equals("ssa_2000s")) {
            if (year < 2000 || year > 2009) {
                System.out.println("The year is invalid. Setting to the nearest valid year.");
                if (year < 2000) {
                    year = 2000;
                }
                else {
                    year = 2009;
                }
            }
        }
        ArrayList<String[]> babydatacomplete = new ArrayList<>();
        String csvFile = "data/" + foldername + "/yob" + year + ".txt";
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator to allow us to create an array of the data and add this to the larger ArrayList
                String [] babydata = line.split(cvsSplitBy);
                babydatacomplete.add(babydata);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return babydatacomplete;
    }

    /**
     * The below helper method calculates the ranking of a certain name given the gender, name, and datafrom that year
     */

    public Integer findRanking(ArrayList<String[]> babydata, Character gender, String name) {
        Integer maleranking = 1;
        Integer femaleranking = 1;
        for (int i =0; i < babydata.size(); i++) {
            Character g = babydata.get(i)[1].charAt(0);
            if (g.equals('F')){
                if (babydata.get(i)[0].equals(name) && gender.equals('F')) {
                    return femaleranking;
                }
                else {
                    femaleranking++;
                }
            }
            else if (g.equals('M')) {
                if (babydata.get(i)[0].equals(name) && gender.equals('M')) {
                    return maleranking;
                } else {
                    maleranking++;
                }
            }
        }
        return -1;
    }

    /**
     * This method below, given a name and gender, as well as the foldername we are currently operating on for the dataset,
     * will return an ArrayList of all rankings of that name/gender pair in the data set.
     */
    public ArrayList<Integer> findAllRanks(String foldername, String name, Character gender) {
        ArrayList<Integer> all_ranks = new ArrayList<>();
        ArrayList<ArrayList<String[]>> allbabydata = generateDataFromDirectory(foldername);
        for (ArrayList<String[]> babydata: allbabydata) {
            all_ranks.add(findRanking(babydata, gender, name));
        }
        return all_ranks;
    }

    /**
     * Helper method to take the directory we are in and convert the entire dataset to an ArrayList of
     * the baby name data for each year in the directory
     */

    public ArrayList<ArrayList<String[]>> generateDataFromDirectory(String foldername) {
        ArrayList<ArrayList<String[]>> allbabydata = new ArrayList<>();
        String[] listoffiles = getFileNameList(foldername);
        for (int i = 0; i < listoffiles.length; i++) {
            Integer year = Integer.parseInt(listoffiles[i].substring(3, 7));
            allbabydata.add(readFileIn(year, foldername));
        }
        return allbabydata;
    }

    /**
     * The below helper method will return a list of all the file names in a given folder contained within the data folder
     */

    public String[] getFileNameList(String foldername) {
        File folder = new File("data/" + foldername);
        String[] listoffiles = folder.list();
        Arrays.sort(listoffiles);
        return listoffiles;
    }

    /**
     * The below method is a helper function that will return the name/gender pair given the gender, data, and ranking number
     */

    public ArrayList<String> findNameAndGenderBasedOnRanking(ArrayList<String[]> babydata, Character gender, Integer ranking) {
        ArrayList<String> namegenderpair = new ArrayList<>();
        Integer counter = 1;
        for (int i =0; i < babydata.size(); i++) {
            Character g = babydata.get(i)[1].charAt(0);
            if (gender.equals(g)) {
                if (counter.equals(ranking)) {
                    namegenderpair.add(babydata.get(i)[0]);
                    namegenderpair.add(babydata.get(i)[1]);
                    break;
                }
                else {
                    counter++;
                }
            }
        }
        return namegenderpair;
    }

    /**
     * Given a name, gender, and year, the below method returns a name/gender pair from the most recent year in the data
     * set that has the same ranking as the ranking of the name, gender, and year given.
     *
     * For testing, simply create a directory in the data folder named foldername and pass this value into the function
     * and make data files with the format of "yobXXXX.txt" in a continuous orderly fashion of years and see the results.
     *
     */

    public ArrayList<String> findRecentRankedName(String name, Character gender, int year, String foldername) {
        ArrayList<String> namegenderpair = new ArrayList<>();
        ArrayList<String[]> babydataforoldyear = readFileIn(year, foldername);
        Integer oldranking = findRanking(babydataforoldyear, gender, name);
        String[] listoffiles = getFileNameList(foldername);
        String mostrecentyear = listoffiles[listoffiles.length-1];
        Integer recentyear = Integer.parseInt(mostrecentyear.substring(3, 7));
        ArrayList<String[]> babydatafornewyear = readFileIn(recentyear, foldername);

        namegenderpair = findNameAndGenderBasedOnRanking(babydatafornewyear, gender, oldranking);

        return namegenderpair;
    }

    /**
     * The below method is a helper function to generate the data for a specific range of years in order to provide easy
     * access to a specific years data within the range of years.
     *
     */

    public ArrayList<ArrayList<String[]>> generateSubsetOfDirectory(Integer start, Integer end, String foldername) {

        ArrayList<ArrayList<String[]>> subsetbabydata = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            subsetbabydata.add(readFileIn(i, foldername));
        }
        return subsetbabydata;
    }

    /**
     * Given a range of years and a gender, the below function will return the most popular names of that gender for the span of
     * years given. It will also return the number of times the name(s) was first overall ranked in the time span given.
     *
     * Makes use of the helper function generateSubsetOfDirectory, getFileNameList, and giveTopRanked.
     *
     * For testing, simply create a directory in the data folder named foldername and pass this value into the function
     * and make data files with the format of "yobXXXX.txt" in a continuous orderly fashion of years and see the results.
     */

    public ArrayList<String> findMostPopularNameOverRangeOfYears(Integer start, Integer end, Character gender, String foldername) {
        ArrayList<String> mostpopularnameandnumber = new ArrayList<>();
        ArrayList<ArrayList<String[]>> directorybabydata = generateSubsetOfDirectory(start, end, foldername);

        String[] yearlist = getFileNameList(foldername);
        ArrayList<Integer> updatedyearlist = new ArrayList<>();
        for (String s: yearlist) {
            Integer year = Integer.parseInt(s.substring(3, 7));
            updatedyearlist.add(year);
        }

        if (start < updatedyearlist.get(0)) {
            System.out.println("That is an invalid start. Setting start to the earliest date in the folder");
            start = updatedyearlist.get(0);
        }
        if (end > updatedyearlist.get(updatedyearlist.size()-1)) {
            System.out.println("That is an invalid end. Setting end to the latest date in the folder");
            end = updatedyearlist.get(updatedyearlist.size()-1);
        }


        ArrayList<String> toprankednames = new ArrayList<>();
        for (int j = 0; j < directorybabydata.size(); j++) {
            if (gender.equals('F')) {
                toprankednames.add(giveTopRanked(directorybabydata.get(j)).get(0));
            }
            else if (gender.equals('M')) {
                toprankednames.add(giveTopRanked(directorybabydata.get(j)).get(1));
            }
        }

        HashMap<String, Integer> frequencyofnames = new HashMap<>();
        for(String s: toprankednames) {
            Integer c = frequencyofnames.get(s);
            if (c == null) {
                c = 0;
            }
            c++;
            frequencyofnames.put(s,c);
        }

        Integer max = 0;
        for (Map.Entry<String, Integer> set : frequencyofnames.entrySet()) {
            if (set.getValue() > max) {
                max = set.getValue();
            }
        }

        for (Map.Entry<String, Integer> set : frequencyofnames.entrySet()) {
            if (set.getValue().equals(max)) {
                mostpopularnameandnumber.add(set.getKey());
            }
        }
        mostpopularnameandnumber.add(Integer.toString(max));
        return mostpopularnameandnumber;
    }


    /**
     * Given a start and end year, and the name of the folder you'd like to access data from, the below method will return
     * a list of non repeating alphabetized names of girls whose names start with the most occurring first letter in the entire
     * data set. A TreeSet was decided so as to not allow there to be duplicates but to also guarantee our final set of names
     * was also sorted.
     *
     * For testing, simply create a directory in the data folder named foldername and pass this value into the function
     * and make data files with the format of "yobXXXX.txt" in a continuous orderly fashion of years and see the results.
     */

    public Set<String> findMostPopularGirlNamesFirstLetter(Integer start, Integer end, String foldername) {
        ArrayList<String> girlnames = new ArrayList<>();
        ArrayList<ArrayList<String[]>> subsetdataofnames = generateSubsetOfDirectory(start, end, foldername);
        HashMap<Character, Integer> namefrequency = new HashMap<>();

        for (ArrayList<String[]> year: subsetdataofnames) {
            for (String[] s: year) {
                if (s[1].equals("F")) {
                    Integer c = namefrequency.get(s[0].charAt(0));
                    if (c == null) {
                        c = 0;
                    }
                    c++;
                    namefrequency.put(s[0].charAt(0),c);
                }
            }
        }
        Integer mostpopularletterfreq = 0;
        for (Map.Entry<Character, Integer> set : namefrequency.entrySet()) {
            if (set.getValue() > mostpopularletterfreq) {
                mostpopularletterfreq = set.getValue();
            }
        }
        ArrayList<Character> charlistofnames = new ArrayList<>();
        for (Map.Entry<Character, Integer> set : namefrequency.entrySet()) {
            if (set.getValue().equals(mostpopularletterfreq)) {
                charlistofnames.add(set.getKey());
            }
            if (charlistofnames.size() > 1) {
                charlistofnames.remove(1);
            }
        }

        Set<String> listofnames = new TreeSet<>();
        for (ArrayList<String[]> year: subsetdataofnames) {
            for (String[] s: year) {
                Character startchar = s[0].charAt(0);
                if (startchar.equals(charlistofnames.get(0)) && s[1].equals("F")) {
                    listofnames.add(s[0]);
                }
            }
        }

        return listofnames;
    }
}
