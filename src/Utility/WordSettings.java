package Utility;

/**
 * This record is used to store and access the filters for words like,
 * the minimum length, maximum length of the word used when
 * searching for words.
 */
public record WordSettings(int minLength, int maxLength, String wordFilter) {

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getWordFilter() {
        return wordFilter;
    }
}
