package zest;

public class CompareVersionNumbers {

    /**
     * Compares two version strings.
     *
     * @param version1 first version string
     * @param version2 second version string
     * @return -1 if version1 < version2,
     *          1 if version1 > version2,
     *          0 if equal
     * @throws IllegalArgumentException if either input is null
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new IllegalArgumentException("Version strings cannot be null");
        }

        String[] v1Parts = version1.split("\\.");
        String[] v2Parts = version2.split("\\.");

        int maxLength = Math.max(v1Parts.length, v2Parts.length);

        for (int i = 0; i < maxLength; i++) {

            int num1 = (i < v1Parts.length) ? Integer.parseInt(v1Parts[i]) : 0;
            int num2 = (i < v2Parts.length) ? Integer.parseInt(v2Parts[i]) : 0;


            if (num1 > num2) {
                return 1; // Bug fix: the return values were swapped.
            } else if (num1 < num2) {
                return -1; // Bug fix: the return values were swapped.
            }
            // If num1 > num2, version1 is greater, so we have to return 1 (not -1).
            // If num1 < num2, version1 is smaller, so we have to return -1 (not 1).
        }

        return 0;
    }

}