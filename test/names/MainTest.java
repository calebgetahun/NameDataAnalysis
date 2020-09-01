package names;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main myTestInstance;

    @BeforeEach
    void setUp() {
        myTestInstance = new Main();
    }

    /**
     * Tests for giveTopRanked
     */
    @Test
    void giveTopRankedForMaleName() {
        String expected = "Jacob";
        String actual = myTestInstance.giveTopRanked(Main.readFileIn(2008, "ssa_2000s")).get(1);
        assertEquals(expected, actual);
    }

    @Test
    void giveTopRankedForFemaleName() {
        String expected = "Emma";
        String actual = myTestInstance.giveTopRanked(Main.readFileIn(2008, "ssa_2000s")).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void giveTopRankedForSSAComplete() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mary");
        expected.add("John");
        ArrayList<String> actual = myTestInstance.giveTopRanked(Main.readFileIn(1909, "ssa_complete"));
        assertEquals(expected, actual);
    }


    /**
     * Tests for firstLetterAndBabies
     */

    @Test
    void firstLetterAndBabiesForMaleFromDecadeData() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1658);
        expected.add(338831);
        ArrayList<Integer> actual = myTestInstance.firstLetterAndBabies('M', 'J', Main.readFileIn(2007, "ssa_complete"));
        assertEquals(expected, actual);
    }

    @Test
    void firstLetterAndBabiesForFemaleFromDecadeData() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(464);
        expected.add(20152);
        ArrayList<Integer> actual = myTestInstance.firstLetterAndBabies('F', 'Z', Main.readFileIn(2007, "ssa_complete"));
        assertEquals(expected, actual);
    }

    @Test
    void firstLetterAndBabiesForNonSensicalInputs() {
        ArrayList<Integer> expected = myTestInstance.firstLetterAndBabies('M', 'S', Main.readFileIn(2009, "ssa_2000s"));
        ArrayList<Integer> actual = myTestInstance.firstLetterAndBabies('M', 'S', Main.readFileIn(2076, "ssa_2000s"));
        assertEquals(expected, actual);
    }


    /**
     * Tests for findAllRanks
     */

    @Test
    void findAllRanksForMaleNameAndGender() {
        List<Integer> expected = List.of(21, 23, 21, 22, 21, 19, 22, 23, 26, 29);
        ArrayList<Integer> actual = myTestInstance.findAllRanks("ssa_2000s", "Jonathan", 'M');
        assertEquals(expected, actual);
    }

    @Test
    void findAllRanksForFemaleNameAndGender() {
        List<Integer> expected = List.of(1, 1, 1, 1, 1, 1, 1, 1, 3, 6);
        ArrayList<Integer> actual = myTestInstance.findAllRanks("ssa_2000s", "Emily", 'F');
        assertEquals(expected, actual);
    }

    @Test
    void findAllRanksForNonSensicalNames() {
        List<Integer> expected = List.of(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        ArrayList<Integer> actual = myTestInstance.findAllRanks("ssa_2000s", "Doofenshmirtz", 'M');
        assertEquals(expected, actual);
    }


    /**
     * Tests for findRecentRankedName
     */

    @Test
    void findRecentRankedNameForFemaleName() {
        List<String> expected = List.of("Olivia", "F");
        List<String> actual = myTestInstance.findRecentRankedName("Mary", 'F', 1965, "ssa_complete");
        assertEquals(expected, actual);
    }

    @Test
    void findRecentRankedNameForMaleName() {
        List<String> expected = List.of("David", "M");
        List<String> actual = myTestInstance.findRecentRankedName("Jonathan", 'M', 1999, "ssa_complete");
        assertEquals(expected, actual);
    }

    @Test
    void findRecentRankedNameForInvalidNameAndYear() {
        List<String> expected = List.of();
        List<String> actual = myTestInstance.findRecentRankedName("SpongeBob SquarePants", 'M', 3608, "ssa_complete");
        assertEquals(expected, actual);
    }

    /**
     * Tests for findMostPopularNameOverRangeOfYears
     */

    @Test
    void findMostPopularNameOverRangeOfYearsForMaleNames() {
        List<String> expected = List.of("John", "21");
        List<String> actual = myTestInstance.findMostPopularNameOverRangeOfYears(1880, 1900, 'M', "ssa_complete");
        assertEquals(expected, actual);
    }

    @Test
    void findMostPopularNameOverRangeOfYearsWithMultipleNamesTied() {
        List<String> expected = List.of("Michael", "John", "44");
        ArrayList<String> actual = myTestInstance.findMostPopularNameOverRangeOfYears(1880, 2018, 'M', "ssa_complete");
        assertEquals(expected, actual);
    }

    @Test
    void findMostPopularNameOverRangeOfYearsForFemaleNames() {
        List<String> expected = List.of("Mary", "57");
        List<String> actual = myTestInstance.findMostPopularNameOverRangeOfYears(1890, 1950, 'F', "ssa_complete");
        assertEquals(expected, actual);
    }

    @Test
    void findMostPopularNameOverRangeOfYearsForInvalidYearRange() {
        List<String> expected = List.of("0");
        List<String> actual = myTestInstance.findMostPopularNameOverRangeOfYears(1990, 1880, 'F', "ssa_complete");
        assertEquals(expected, actual);
    }

    /**
     * Tests for findMostPopularGirlNamesFirstLetter
     */

    @Test
    void findMostPopularGirlNamesFirstLetterForOtherDataSet() {
        Set<String> expected = new TreeSet<String>(Arrays.asList("Margaret", "Mary", "Minnie"));
        Set<String> actual = myTestInstance.findMostPopularGirlNamesFirstLetter(2100, 2100, "ssa_testdata");
        assertEquals(expected, actual);
    }

    @Test
    void findMostPopularGirlNamesFirstLetterForValidYearRangeAndMultipleFiles() {
        Set<String> expected = new TreeSet<String>(Arrays.asList("Mabel", "Margaret", "Marie", "Mary", "Minnie"));
        Set<String> actual = myTestInstance.findMostPopularGirlNamesFirstLetter(2100, 2101, "ssa_testdata");
        assertEquals(expected, actual);
    }

    @Test
    void findMostPopularGirlNamesFirstLetterForInvalidYearRange() {
        Set<String> expected = new TreeSet<String>();
        Set<String> actual = myTestInstance.findMostPopularGirlNamesFirstLetter(2105, 2109, "ssa_testdata");
        assertEquals(expected, actual);
    }
}
