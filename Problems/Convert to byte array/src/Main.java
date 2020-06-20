import java.io.CharArrayWriter;
import java.io.IOException;

class Converter {
    public static char[] convert(String[] words) throws IOException {
        char[] rezult = new char[0];
        try (CharArrayWriter writer = new CharArrayWriter()) {
            for (String word : words) {
                writer.write(word);
            }
            rezult = writer.toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rezult;
    }
}