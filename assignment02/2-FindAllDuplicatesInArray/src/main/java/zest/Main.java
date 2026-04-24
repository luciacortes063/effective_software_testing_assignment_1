package zest;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FindAllDuplicatesInArray solution = new FindAllDuplicatesInArray();
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> duplicates = solution.findDuplicates(nums);
        System.out.println("Duplicates: " + duplicates);
    }
}
