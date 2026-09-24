package demos.hospital;

public interface ORServerPlus extends ORServer {
    int patientCount();
    Patient transferPatient();
    void distributePatients(ORServerPlus s);
}
