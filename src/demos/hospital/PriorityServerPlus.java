package demos.hospital;

public class PriorityServerPlus extends PriorityServer implements ORServerPlus {
    private int size;

    public PriorityServerPlus() {
        size = 0;
        super();
    }

    public void addToWaitlist(Patient p) {
        super.addToWaitlist(p);
        ++this.size;
    }

    public Patient operatePatient(int h) {
        --this.size;
        return super.operatePatient(h);
    }

    public Patient transferPatient() {
        --this.size;
        return pq.removeMin();
    }

    public int patientCount() {
        return this.size;
    }

    public void distributePatients(ORServerPlus s) {
        Patient[] ap = new Patient[this.patientCount()];

        for(int i = 0; i < ap.length; ++i) {
            ap[i] = this.transferPatient();
        }

        for(int i = 0; i < ap.length; ++i) {
            if (i % 2 == 0) {
                this.addToWaitlist(ap[i]);
            } else {
                s.addToWaitlist(ap[i]);
            }
        }
    }
}
