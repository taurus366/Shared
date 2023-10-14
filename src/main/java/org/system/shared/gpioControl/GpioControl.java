package org.system.shared.gpioControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GpioControl {

    final String GPIO_DOOR_OPEN = "15";
    final String GPIO_DOOR_CLOSE = "16";
    final String GPIO_LIMITE_SWITCH = "0";
    final String GPIO_LIMITE_SWITCH_PLUS = "1";

    final private String GPIO_COMMAND_READ = "gpio read";
    final private String GPIO_COMMAND_WRITE = "gpio write";
    final private String GPIO_COMMAND_MODE = "gpio mode";


    public GpioControl() {
    }

   /**
    * HOW TO USE IT gpio mode/write pin[2] out/in, 1/0
    * Set the pin to output mode
    * executeCommand("gpio mode 2 out");
    * <p>
    * // Turn the pin on
    * executeCommand("gpio write 2 1");
    * <p>
    * // Wait for some time (1 second in this case)
    * Thread.sleep(1000);
    * <p>
    * // Turn the pin off
    * executeCommand("gpio write 2 0");
    *
    * @return
    */

    private static String executeCommand(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        Process process = processBuilder.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Command failed with exit code " + exitCode);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();
            return output;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * setPinMode(2, "out"); // Set the pin to output mode
     * setPinMode(2, "in"); // Set the pin to input mode
     * @param pin
     * @param modeInOut
     * @return
     */
    public boolean setPinMode(int pin, String modeInOut) {
        try {
            executeCommand(GPIO_COMMAND_MODE + " " + pin + " " + modeInOut.toUpperCase());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * writePin(2, true); // Turn the pin  means mode out and value 1[HIGH] voltage
     * writePin(2, false); // Turn the pin off means mode out and value 0[LOW] voltage
     * @param pin
     * @param value
     * @return
     */
    public boolean writePin(int pin, boolean value) {
        try {
            executeCommand(GPIO_COMMAND_WRITE + " " + pin + " " + (value ? "1" : "0"));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * WHEN PIN V == 1 So it means the wiring is disconnected or the signal switch is open
     * WHEN PIN V == 0 So it means the wiring is connected or the signal switch is closed
     * **/
    public boolean checkPinStatus(int pin) {
        try {
            final String executedCommand = executeCommand(GPIO_COMMAND_READ + " " + pin);
            // Check if the output is "1" to indicate the pin is high
            return "1".equals(executedCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



}
