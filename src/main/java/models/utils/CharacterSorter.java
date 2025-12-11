package models.utils;

import models.people.Character;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for sorting characters using various algorithms.
 * Implements custom sorting algorithms as required by the project specifications.
 * @author Project Team
 */
public class CharacterSorter {

    /**
     * Sort characters by strength using QuickSort algorithm.
     * @param characters the list of characters to sort
     * @return a new sorted list
     */
    public static List<Character> sortByStrength(List<Character> characters) {
        List<Character> sorted = new ArrayList<>(characters);
        quickSortByStrength(sorted, 0, sorted.size() - 1);
        return sorted;
    }

    /**
     * QuickSort implementation for sorting by strength.
     * @param list the list to sort
     * @param low starting index
     * @param high ending index
     */
    private static void quickSortByStrength(List<Character> list, int low, int high) {
        if (low < high) {
            int pi = partitionByStrength(list, low, high);
            quickSortByStrength(list, low, pi - 1);
            quickSortByStrength(list, pi + 1, high);
        }
    }

    /**
     * Partition method for QuickSort.
     * @param list the list to partition
     * @param low starting index
     * @param high ending index
     * @return the partition index
     */
    private static int partitionByStrength(List<Character> list, int low, int high) {
        double pivot = list.get(high).getStrength();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getStrength() >= pivot) { // Descending order
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * Swap two elements in a list.
     * @param list the list
     * @param i first index
     * @param j second index
     */
    private static void swap(List<Character> list, int i, int j) {
        Character temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /**
     * Sort characters by age using Bubble Sort algorithm.
     * @param characters the list of characters to sort
     * @return a new sorted list
     */
    public static List<Character> sortByAge(List<Character> characters) {
        List<Character> sorted = new ArrayList<>(characters);
        bubbleSortByAge(sorted);
        return sorted;
    }

    /**
     * Bubble Sort implementation for sorting by age.
     * @param list the list to sort
     */
    private static void bubbleSortByAge(List<Character> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getAge() > list.get(j + 1).getAge()) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    /**
     * Sort characters by health using Java's built-in sort with custom comparator.
     * @param characters the list of characters to sort
     * @return a new sorted list
     */
    public static List<Character> sortByHealth(List<Character> characters) {
        List<Character> sorted = new ArrayList<>(characters);
        sorted.sort(Comparator.comparingDouble(Character::getHealth).reversed());
        return sorted;
    }

    /**
     * Sort characters by name alphabetically using Insertion Sort.
     * @param characters the list of characters to sort
     * @return a new sorted list
     */
    public static List<Character> sortByName(List<Character> characters) {
        List<Character> sorted = new ArrayList<>(characters);
        insertionSortByName(sorted);
        return sorted;
    }

    /**
     * Insertion Sort implementation for sorting by name.
     * @param list the list to sort
     */
    private static void insertionSortByName(List<Character> list) {
        for (int i = 1; i < list.size(); i++) {
            Character key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).getName().compareTo(key.getName()) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}
