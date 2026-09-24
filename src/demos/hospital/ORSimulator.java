package demos.hospital;

import java.text.DecimalFormat;
import java.util.Random;

public class ORSimulator {
    protected static double averageWaitTime;
    protected static int SEVERITY_SCALE = 10;
    protected static double[] waitTimeArray;
    protected static double[] averageWaitTimeArray;
    protected static int[] patientArray;
    protected static int patientCount;
    protected static String verbose;

    public ORSimulator() {
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            patientCount = Integer.parseInt(args[0]);
            verbose = args[1];
        } else if (args.length != 0) {
            System.out.println("Specify the values of 2 arguments:");
            System.out.println("- Number of patients (a positive integer)");
            System.out.println("- Enable verbose mode (values: SI, NO)");
            return;
        }

        if (testPatient()) {
            if (testORServer()) {
                Patient[] waitlist = generatePatients(patientCount);
                double avgWaitTime = simulateOR(waitlist, new FIFOServer());
                showResults("QUEUE", avgWaitTime);
                System.out.println();
                avgWaitTime = simulateOR(waitlist, new PriorityServer());
                showResults("PRIORITY QUEUE", avgWaitTime);
            }
        }
    }

    protected static void showResults(String type, double avgWaitTime) {
        System.out.println("\n*** SIMULATION WITH " + type + ": Number of patients = " + patientCount + " ; Average wait time = " + doubleToString(avgWaitTime / 24.0) + " DAYS.");

        for(int g = 0; g < SEVERITY_SCALE; ++g) {
            averageWaitTimeArray[g] = waitTimeArray[g] / (double)patientArray[g];
            System.out.println("Average wait time, patients severity " + g + " = " + doubleToString(averageWaitTimeArray[g] / 24.0) + " DAYS.");
        }
    }

    protected static Patient[] generatePatients(int n) {
        Patient[] res = new Patient[n];
        Random rnd = new Random();
        int time = 0;

        for(int i = 0; i < res.length; ++i) {
            int severity = rnd.nextInt(10);
            time += rnd.nextInt(2);
            res[i] = new Patient("surgery", severity, time);
        }

        return res;
    }

    private static double simulateOR(Patient[] waitlist, ORServer s) {
        int hour = 0;
        int i = 0;
        averageWaitTime = 0.0;

        for(int g = 0; g < SEVERITY_SCALE; ++g) {
            waitTimeArray[g] = 0.0;
            patientArray[g] = 0;
        }

        while(i < waitlist.length) {
            if (s.hasPatients()) {
                hour += operatePatient(s, hour);
            } else {
                hour = waitlist[i].getWaitlistAdmissionHour();
            }

            while(i < waitlist.length && waitlist[i].getWaitlistAdmissionHour() <= hour) {
                s.addToWaitlist(waitlist[i]);
                ++i;
            }
        }

        while(s.hasPatients()) {
            hour += operatePatient(s, hour);
        }

        return averageWaitTime / (double)waitlist.length;
    }

    protected static int operatePatient(ORServer s, int h) {
        Patient p = s.getPatient();
        int g = p.getSeverity();
        s.operatePatient(h);
        int endHour = h + 3;
        int wait = h - p.getWaitlistAdmissionHour();
        if (verbose.equals("SI")) {
            System.out.println("[" + endHour / 24 + "] " + p.toString());
        }

        waitTimeArray[g] += (double)wait;
        patientArray[g]++;
        averageWaitTime += (double)wait;
        return 3;
    }

    protected static String doubleToString(double n) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(n);
    }

    protected static boolean testPatient() {
        try {
            Patient d1 = new Patient("X", 1, 0);
            Patient d2 = new Patient("X", 5, 1);
            Patient d3 = new Patient("X", 1, 2);
            if (d1.compareTo(d2) <= 0 && d1.compareTo(d3) == 0 && d2.compareTo(d3) >= 0) {
                return true;
            } else {
                throw new Exception("Error in the compareTo method");
            }
        } catch (Exception e) {
            System.out.println("Error in the Patient class: " + e.getMessage());
            return false;
        }
    }

    protected static boolean testORServer() {
        try {
            Patient[] ap = new Patient[]{new Patient("X", 5, 0), new Patient("X", 8, 1), new Patient("X", 2, 2), new Patient("X", 7, 1), new Patient("X", 3, 2), new Patient("X", 1, 0), new Patient("X", 9, 1), new Patient("X", 7, 2)};
            int[] pos = new int[]{5, 2, 4, 0, 3, 7, 1, 6};
            int[] temp = new int[]{3, 1, 1, 3, 2, 1, 2, 2};
            ORServer s = new PriorityServer();
            if (s.hasPatients()) {
                throw new Exception("Error in the hasPatients method");
            } else {
                for(int i = 0; i < ap.length; ++i) {
                    s.addToWaitlist(ap[pos[i]]);
                }

                if (!s.hasPatients()) {
                    throw new Exception("Error in the hasPatients method");
                } else {
                    for(int i = 0; i < ap.length; ++i) {
                        if (s.getPatient() != ap[pos[i]]) {
                            throw new Exception("Error in the getPatient method");
                        }

                        Patient p = s.operatePatient(0);
                        if (p.getDelay() != temp[i]) {
                            throw new Exception("Error in the operatePatient method");
                        }
                    }

                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in the PriorityServer class: " + e.getMessage());
            return false;
        }
    }

    static {
        waitTimeArray = new double[SEVERITY_SCALE];
        averageWaitTimeArray = new double[SEVERITY_SCALE];
        patientArray = new int[SEVERITY_SCALE];
        patientCount = 100;
        verbose = "NO";
    }
}
