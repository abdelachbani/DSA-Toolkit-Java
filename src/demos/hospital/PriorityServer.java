package demos.hospital;

import datastructures.trees.BinaryHeap;
import datastructures.interfaces.PriorityQueue;

/** PriorityServer Class:
 *  implements an ORServer
 *  that uses a maximum priority model (PriorityQueue)
 *  to manage waiting patients.
 */
public class PriorityServer implements ORServer {

    /** A PriorityServer HAS A PriorityQueue pq of waiting patients */
    protected PriorityQueue<Patient> pq;

    /** Creates an empty server */
    public PriorityServer() {
        pq = new BinaryHeap<>();
    }

    /** Includes a new Patient in wait, p, in an ORServer.
     *  @param p   Patient
     */
    public void addToWaitlist(Patient p) {
        pq.insert(p);
    }

    /** Checks if there is any Patient waiting in an ORServer.
     *  @return boolean
     */
    public boolean hasPatients() {
		return !pq.isEmpty();
    }

    /** PRECONDITION hasPatients():
     *  returns the Patient of an ORServer that is going to be operated.
     *  @return Patient
     */
    public Patient getPatient() {
		return pq.getMin();
    }

    /** PRECONDITION hasPatients():
     *  removes from an ORServer the Patient that is going to be operated
     *  and returns the Patient updating the value of surgeryStartHour,
     *  result of adding the time h, received as argument,
     *  and the duration of the operation (constant defined in the interface)
     *  @param h   int (hours)
     *  @return    Patient
     */
    public Patient operatePatient(int h) {
        Patient p = pq.removeMin();
        p.setSurgeryStartHour(h + SURGERY_DURATION);
		return p;
    }

}
