package demos.hospital;

/** ORServer Interface: 
 *  specifies the service capacity of an OR server 
 *  and the methods that it has to implement **independently**   
 *  of whether it uses a Queue or a PriorityQueue 
 *  to manage waiting patients.
 */
public interface ORServer {
    public static final int SURGERY_DURATION = 3; //  3 hours, time of any operation
    
    /** Includes a new Patient in wait, p, in an ORServer.
     *  @param p   Patient
     */
    void addToWaitlist(Patient p);
    
    /** Checks if there is any Patient waiting in an ORServer.
     *  @return   boolean
     */
    boolean hasPatients();
    
    /** PRECONDITION hasPatients(): 
     *  returns the Patient of an ORServer that is going to be operated.
     *  @return   Patient
     */
    Patient getPatient();
    
    /** PRECONDITION hasPatients(): 
     *  removes from an ORServer the Patient that is going to be operated 
     *  and returns the Patient updating the value of surgeryStartHour  
     *  @param h   int (hours)
     *  @return    Patient 
     */
    Patient operatePatient(int h);
}
