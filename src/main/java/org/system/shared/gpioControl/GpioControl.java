package org.system.shared.gpioControl;

import java.io.IOException;

public class GpioControl {

    public GpioControl() {
    }

   /** HOW TO USE IT gpio mode/write pin[2] out/in, 1/0
            Set the pin to output mode
               executeCommand("gpio mode 2 out");

               // Turn the pin on
               executeCommand("gpio write 2 1");

               // Wait for some time (1 second in this case)
               Thread.sleep(1000);

               // Turn the pin off
               executeCommand("gpio write 2 0");
    **/

    private static void executeCommand(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        Process process = processBuilder.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Command failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPinMode(int pin, String modeInOut) {
        try {
            executeCommand("gpio mode " + pin + " " + modeInOut.toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePin(int pin, boolean value) {
        try {
            executeCommand("gpio write " + pin + " " + (value ? "1" : "0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
