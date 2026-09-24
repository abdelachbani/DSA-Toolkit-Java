package demos.hospital;

import datastructures.interfaces.Queue;
import datastructures.linear.CircularArrayQueue;

/** FIFOServer Class: 
 *  implements an ORServer 
 *  that uses a FIFO model (Queue) 
 *  to manage waiting patients.
 */
public class FIFOServer implements ORServer {
    
    /** A FIFOServer HAS A Queue c of waiting patients */
    protected Queue<Patient> c; 
    
    /** Creates an empty server */
    public FIFOServer() { c = new CircularArrayQueue<Patient>(); }
    
    /** Includes a new Patient in wait, p, in an ORServer.
     *  @param p   Patient
     */
    public void addToWaitlist(Patient p) { c.enqueue(p); }
    
    
    /** Checks if there is any Patient waiting in an ORServer.
     *  @return   boolean
     */
    public boolean hasPatients() { return !c.isEmpty(); }
    
    /** PRECONDITION hasPatients(): 
     *  returns the Patient of an ORServer that is going to be operated.
     *  @return   Patient
     */
    public Patient getPatient() { return c.front(); }
    
    /** PRECONDITION hasPatients(): 
     *  removes from an ORServer the Patient that is going to be operated 
     *  and returns the Patient updating the value of surgeryStartHour  
     *  @param h   int (hours)
     *  @return    Patient 
     */
    public Patient operatePatient(int h) { 
        Patient p = c.dequeue();
        p.setSurgeryStartHour(h + SURGERY_DURATION);
        return p;
    }
}
