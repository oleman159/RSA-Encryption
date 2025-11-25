import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NmapRunner {
    public static String runScan(String target) {
        StringBuilder output = new StringBuilder();

        try {
            // Add a sentence before the scan output
            output.append("This is to scan the following IP: ")
                  .append(target)
                  .append("\n\n");

            // Run Nmap on all ports
            ProcessBuilder pb = new ProcessBuilder(
                "nmap",
                "-Pn",
                "-T4",
                "-p-",
                target
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static void main(String[] args) {
        String result = runScan("192.168.1.1");
        System.out.println(result);
    }
}
