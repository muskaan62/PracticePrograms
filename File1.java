//import com.google.common.io.Resources;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
class File1
{
    File1()
    {
        System.out.println("First file");
        Path p= Path.of("File1");
        InputStream in = this.getClass().getResourceAsStream("File1.java");
        System.out.println(in);
    }
}