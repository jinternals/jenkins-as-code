import javaposse.jobdsl.dsl.DslScriptLoader;
import javaposse.jobdsl.dsl.MemoryJobManagement;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

public class SeedJobTest {


    public static final Path PATH = new File("src/main/jenkins/seed-job.groovy").toPath();

    @Test
    public void shouldCompileScript() throws IOException {

        MemoryJobManagement memoryJobManagement = new MemoryJobManagement();

        DslScriptLoader scriptLoader = new DslScriptLoader(memoryJobManagement);

        String script = new String(readAllBytes(PATH));

        scriptLoader.runScript(script);
    }
}