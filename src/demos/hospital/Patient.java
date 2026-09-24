package demos.hospital;

/** Patient Class: represents a patient to operate.
 */
public class Patient implements Comparable<Patient> {
    
    private Citizen id;
    
    /** type of surgery
     */
    private String surgery; 
    
    /** indicator of the severity of the patient, 
     *  i.e., of the urgency of their surgery
     *  value range [0..9], higher value -> less urgent
     */
    private int severity; 
    
    /** time (in number of hours) of admission of the patient
     *  in Waitlist (attribute: waitlistAdmissionHour)
     *  and in OR (attribute: surgeryStartHour)
     */
    private int waitlistAdmissionHour; 
    private int surgeryStartHour; 
    
    /** Creates a Patient of surgery c, severity g
     *  and sent to "waitlist" at hour h. 
     *  @param c   String
     *  @param g   int
     *  @param h   int (hours)
     */
    public Patient(String c, int g, int h) {
        id = new Citizen();
        surgery = c;
        severity = g;
        waitlistAdmissionHour = h;
    }
    
    /** Returns the surgery of a Patient.
     *  @return   String
     */
    public String getSurgery() { return surgery; }
    
    /** Returns the severity of a Patient.
     *  @return  int
     */
    public int getSeverity() { return severity; }
    
    /** Returns the admission hours of a Patient.
     *  @return   int (hours)
     */
    public int getWaitlistAdmissionHour() { return waitlistAdmissionHour; }
    public int getSurgeryStartHour() { return surgeryStartHour; }
    
    /** Returns the time (in hours) that a Patient is in the Waitlist
     *  @return   int (hours)
     */
    public int getDelay() { return surgeryStartHour - waitlistAdmissionHour; }
    
    public void setSurgeryStartHour(int t) { surgeryStartHour = t; }
        
    /** Returns the int value that results from comparing the severity
     *  of a Patient (this) with another's. Said value will be...
     *  
     *  ** NEGATIVE if a patient (this) has MORE severity than the other,
     *     (MORE severity -> lower value of severity attribute)
     *     i.e. if their surgery is MORE priority than the other's
     *     
     *  ** POSITIVE if a patient (this) has LESS severity than another, 
     *     i.e. if their surgery is LESS priority than the other's
     *     
     *  ** ZERO if both patients have the same severity
     *  
     *  @param other   Patient  
     *  @return int   result of comparing a Patient (this) with another 
     */
    public int compareTo(Patient other) {
        return severity - other.severity;
    }
    
    /** Returns the String that represents a Patient in a certain text format.
     *  @return   String
     */
    public String toString() {
        return surgery + " (severity: " + severity + ") " 
            + ", \tWait: " + ((surgeryStartHour - waitlistAdmissionHour) / 24) 
			+ " DAYS. \t " + id;
    }
}
