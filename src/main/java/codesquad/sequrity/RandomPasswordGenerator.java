package codesquad.sequrity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPasswordGenerator {
    private static final int PASSWORD_LENGTH = 8;
    private static char[] defaultList = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String generatePassword() {
        List<Object> list = new ArrayList<>();

        for (char character:defaultList) {
            list.add(character);
        }

        Collections.shuffle(list);

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(list.get(i));
        }

        return password.toString();
    }
}
