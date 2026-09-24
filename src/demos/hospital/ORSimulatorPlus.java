package demos.hospital;

import java.util.Scanner;

public class ORSimulatorPlus extends ORSimulator {
    public ORSimulatorPlus() {
    }

    public static void main(String[] args) {
        System.out.println("Specify the Number of patients (an integer > 250):");
        Scanner sc = new Scanner(System.in);
        patientCount = sc.nextInt();
        if (testPatient()) {
            if (testORServerPlus()) {
                Patient[] waitlist = generatePatients(patientCount);
                double avgWaitTime = simulateOR(waitlist, new FIFOServer());
                showResults("QUEUE", avgWaitTime);
                System.out.println();
                avgWaitTime = simulateOR(waitlist, new PriorityServer());
                showResults("PRIORITY QUEUE", avgWaitTime);
                System.out.println();
                avgWaitTime = simulateOR(waitlist, new FIFOServerPlus());
                showResults("QUEUE PLUS", avgWaitTime);
                System.out.println();
                avgWaitTime = simulateOR(waitlist, new PriorityServerPlus());
                showResults("PRIORITY QUEUE PLUS", avgWaitTime);
            }
        }
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
            if (s instanceof ORServerPlus s1) {
                if (s1.patientCount() > 200) {
                    System.out.println(s1.patientCount() + " patients, opening new OR");
                    return openNewORServer(s1, hour, waitlist.length);
                }
            }
        }

        return averageWaitTime / (double)waitlist.length;
    }

    private static double openNewORServer(ORServerPlus s1, int hour, int n) {
        ORServerPlus s2;
        if (s1 instanceof PriorityServerPlus) {
            s2 = new PriorityServerPlus();
        } else {
            s2 = new FIFOServerPlus();
        }

        s1.distributePatients(s2);

        while(s1.hasPatients() || s2.hasPatients()) {
            hour += operatePatient(s1, hour);
            hour -= 3;
            hour += operatePatient(s2, hour);
        }

        return averageWaitTime / (double)n;
    }

    protected static int operatePatient(ORServer s, int h) {
        return !s.hasPatients() ? 0 : ORSimulator.operatePatient(s, h);
    }

    protected static boolean testORServerPlus() {
        try {
            Patient[] ap = new Patient[]{new Patient("X", 5, 0), new Patient("X", 8, 1), new Patient("X", 2, 2), new Patient("X", 7, 1), new Patient("X", 3, 2), new Patient("X", 1, 0), new Patient("X", 9, 1), new Patient("X", 7, 2)};
            int[] pos = new int[]{5, 2, 4, 0, 3, 7, 1, 6};
            int[] temp = new int[]{3, 1, 1, 3, 2, 1, 2, 2};
            ORServerPlus s = new PriorityServerPlus();
            if (s.hasPatients()) {
                throw new Exception("Error in the hasPatients method");
            } else {
                for(int i = 0; i < ap.length; ++i) {
                    s.addToWaitlist(ap[pos[i]]);
                }

                if (!s.hasPatients()) {
                    throw new Exception("Error in the hasPatients method");
                } else if (s.patientCount() != 8) {
                    throw new Exception("Error in the patientCount method");
                } else {
                    for(int i = 0; i < ap.length; ++i) {
                        if (s.getPatient() != ap[pos[i]]) {
                            throw new Exception("Error in the getPatient method");
                        }

                        Patient p = s.operatePatient(0);
                        if (p.getDelay() != temp[i]) {
                            throw new Exception("Error in the operatePatient method");
                        }

                        if (i == 4 && s.patientCount() != 3) {
                            throw new Exception("Error in the patientCount method");
                        }
                    }

                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in the PriorityServerPlus class: " + e.getMessage());
            return false;
        }
    }
}
