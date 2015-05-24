package me.alb_i986.selenium.tinafw.tests;

import me.alb_i986.selenium.tinafw.config.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class FileTestReportWriter implements TestReportWriter {

    protected static final Logger logger = Logger.getLogger(FileTestReportWriter.class);

    private static final String REPORTS_DIR = Config.getReportsDir();

    @Override
    public void write(TestReport report) {
        String fileName = report.getTestName().replaceAll(" ", "_") + "_" +
                new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(report.getCreatedDate()) +
                "." + report.getFormat().toLowerCase();
        Path reportPath = Paths.get(REPORTS_DIR, fileName);
        try(PrintWriter writer = createFile(reportPath)) {
            writer.println(report);
        } catch (IOException ioEx) {
            logger.error("Cannot generate the HTML report", ioEx);
            return;
        }
    }

    private PrintWriter createFile(Path file) throws IOException {
        Files.createDirectories(file.getParent());
        return new PrintWriter(Files.newBufferedWriter(file, Charset.forName("UTF-8")));
    }
}
