public final class Sept23 {
    /**
     * Rotates the subarray array[from..to] left by one position.
     *
     * <p>
     * Example:
     * array = [10, 20, 30, 40, 50], from = 1, to = 3
     * After call: [10, 30, 40, 20, 50]
     *
     * @param <T>   element type
     * @param array the array to modify
     * @param from  starting index (inclusive, 0-based)
     * @param to    ending index (inclusive, 0-based, must be >= from)
     * @throws IllegalArgumentException if {@code array} is null,
     *                                  if {@code from < 0}, if
     *                                  {@code to >= array.length},
     *                                  or if {@code from >= to}.
     */
    public static <T> void rotateLeft(T[] array, int from, int to) {
        if (array == null || from < 0 || to >= array.length || from >= to) {
            throw new IllegalArgumentException("Invalid array or range");
        }
        T temp = array[from];
        for (int index = from; index < to; index++) {
            array[index] = array[index + 1];

        }
        arrray[to] = temp;

    }

}