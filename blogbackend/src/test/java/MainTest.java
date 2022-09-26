import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MainTest {

    @Test
    public void test1() {
        String content = "";
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File("src/test/java/md/test.css");
        try {
            InputStreamReader streamReader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((content = bufferedReader.readLine()) != null)
                stringBuilder.append(content);

            System.out.println(stringBuilder.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
