package demos.hospital;

import java.util.Random;

/**
 * Citizen: represents a registered person in a municipality
 */
public class Citizen implements Comparable<Citizen> {

    private String nationalId, firstName, surname1, surname2;
    private int postalCode; /** postalCode: postal code */
    
    /** 100 most frequent surnames, Spain, 2022, according to INE */
    private static String[] surnames = {"GARCIA", "RODRIGUEZ", "GONZALEZ", "FERNANDEZ", "LOPEZ",
        "MARTINEZ", "SANCHEZ", "PEREZ", "GOMEZ", "MARTIN", "JIMENEZ", "HERNANDEZ", "RUIZ", "DIAZ",
        "MORENO", "MUÑOZ", "ALVAREZ", "ROMERO", "GUTIERREZ", "ALONSO", "NAVARRO", "TORRES", "DOMINGUEZ",
        "RAMOS", "VAZQUEZ", "RAMIREZ", "GIL", "SERRANO", "MORALES", "MOLINA", "BLANCO", "SUAREZ",
        "CASTRO", "ORTEGA", "DELGADO", "ORTIZ", "MARIN", "RUBIO", "NUÑEZ", "MEDINA", "SANZ", "CASTILLO",
        "IGLESIAS", "CORTES", "GARRIDO", "SANTOS", "GUERRERO", "LOZANO", "CANO", "CRUZ", "MENDEZ",
        "FLORES", "PRIETO", "HERRERA", "PEÑA", "LEON", "MARQUEZ", "CABRERA", "GALLEGO", "CALVO", "VIDAL",
        "CAMPOS", "REYES", "VEGA", "FUENTES", "CARRASCO", "DIEZ", "AGUILAR", "CABALLERO", "NIETO", 
        "SANTANA", "VARGAS", "PASCUAL", "GIMENEZ", "HERRERO", "HIDALGO", "MONTERO", "LORENZO", "SANTIAGO",
        "BENITEZ", "DURAN", "IBAÑEZ", "ARIAS", "MORA", "FERRER", "CARMONA", "VICENTE", "ROJAS", "SOTO",
        "CRESPO", "ROMAN", "PASTOR", "VELASCO", "PARRA", "SAEZ", "MOYA", "BRAVO", "RIVERA", "GALLARDO",
        "SOLER"};
     
    /** 50 most frequent male names, Spain, 2022, according to INE */
    private static String[] maleNames = {"ANTONIO", "MANUEL", "JOSE", "FRANCISCO", "DAVID",
        "JUAN", "JAVIER", "DANIEL", "JOSE ANTONIO", "FRANCISCO JAVIER", "JOSE LUIS", "CARLOS", 
        "ALEJANDRO", "JESUS", "MIGUEL", "JOSE MANUEL", "MIGUEL ANGEL", "RAFAEL", "PABLO", "PEDRO",
        "ANGEL", "SERGIO", "FERNANDO", "JOSE MARIA", "JORGE", "LUIS", "ALBERTO", "ALVARO", 
        "JUAN CARLOS", "ADRIAN", "DIEGO", "JUAN JOSE", "RAUL", "IVAN", "RUBEN", "JUAN ANTONIO",
        "OSCAR", "ENRIQUE", "RAMON", "ANDRES", "JUAN MANUEL", "SANTIAGO", "VICENTE", "MARIO", 
        "VICTOR", "JOAQUIN", "EDUARDO", "ROBERTO", "MARCOS", "JAIME"};
      
    /** 50 most frequent female names, Spain, 2022, according to INE */
    private static String[] femaleNames = {"MARIA CARMEN", "MARIA", "CARMEN", "ANA MARIA",
        "LAURA", "MARIA PILAR", "MARIA DOLORES", "ISABEL", "JOSEFA", "MARIA TERESA", "ANA", "MARTA",
        "CRISTINA", "MARIA ANGELES", "LUCIA", "MARIA ISABEL", "MARIA JOSE", "FRANCISCA", "ANTONIA",
        "DOLORES", "PAULA", "SARA", "ELENA", "MARIA LUISA", "RAQUEL", "ROSA MARIA", "MANUELA", 
        "MARIA JESUS", "PILAR", "CONCEPCION", "MERCEDES", "JULIA", "BEATRIZ", "ALBA", "SILVIA",
        "NURIA", "IRENE", "PATRICIA", "ROSARIO", "JUANA", "ROCIO", "ANDREA", "TERESA", "ENCARNACION",
        "MONTSERRAT", "MONICA", "ALICIA", "MARIA MAR", "SANDRA", "SONIA"};
    
    /** array for calculating the id letter */
    private static char[] idLetter = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B',
        'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        
    /**
     * Creates a Citizen (with random values)
     */
    public Citizen() {
        Random r = new Random();         
        /** id in range [10000000 .. 99999999], random value */
        int n = r.nextInt(99999999) + 10000000; 
        /** calculating id letter */
        nationalId = n + "" + idLetter[n % 23];         
        /** first surname, random value */
        n = r.nextInt(surnames.length);
        surname1 = surnames[n];        
        /** second surname, random value */
        n = r.nextInt(surnames.length);
        surname2 = surnames[n];        
        /** name, random value, 50% female, 50% male probability */
        n = r.nextInt(100);
        if (n % 2 == 0) {
            n = r.nextInt(femaleNames.length);
            firstName = femaleNames[n];
        }
        else {
            n = r.nextInt(maleNames.length);
            firstName = maleNames[n];
        }
        /** postal code in range [46000 .. 46999], random value */
        postalCode = 46000 + r.nextInt(999); 
    }
    
    /**
     * Creates a Citizen (with values passed as arguments)
     */
    public Citizen(String id, String nom, String ap1, String ap2, int cp) {
        this.nationalId = id;
        firstName = nom;
        surname1 = ap1;
        surname2 = ap2;
        this.postalCode = cp;
    }
    
    /**
     * Attribute getter methods
     */
    public String getNationalId() { return nationalId; }
    public String getFirstName() { return firstName; }
    public String getSurname1() { return surname1; }
    public String getSurname2() { return surname2; }
    public int getPostalCode() { return postalCode; }
    
    /**
     * Returns the String that represents a (this) Citizen in text format
     */
    public String toString() {
        return surname1 + " " + surname2 + ", " + firstName 
               + " (id: " + nationalId + ". postal code: " + postalCode + ")";
    }
    
    /**
     * Checks if a (this) Citizen is equal to another, 
     * i.e. if both have the same value 
     * (compares nationalId attribute values)
     * 
     * @param other the other Citizen.
     * @return true if this and other have the same id
     *         false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof Citizen h) {
            return nationalId.equals(h.nationalId);
        }
        else {
            return false;
        }
    }
    
    /**
     * Compares a (this) Citizen with another.
     * 
     * @param other the Citizen to compare with this.
     * @return -1 if this is less than other, 
     *         1  if this is greater than other
     *         0  if this and other are equal
     */
    public int compareTo(Citizen other) {
        if(!surname1.equals(other.surname1)){
            return surname1.compareTo(other.surname1);
        }
        if(!surname2.equals(other.surname2)){
            return surname2.compareTo(other.surname2);
        }
        if(!firstName.equals(other.firstName)){
            return firstName.compareTo(other.firstName);
        }
        return nationalId.compareTo(other.nationalId);
    }
}
