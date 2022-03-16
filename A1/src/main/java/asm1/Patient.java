package asm1;
enum SymptomLevel {
    Critical, Moderate, Mild
}

public class Patient extends Person{
    private SymptomLevel symptomLevel;
    private String HospitalID;

    /*
    * Class Constructors
    * Remember to initialize the attributes in the Person
    */
    public Patient(Person p, SymptomLevel p_symptomLevel) {
        //TODO
    	super(p.IDCardNo, p.getLoc(), p.getGender(), p.getAge(), p.getIsVac());
    	this.symptomLevel = p_symptomLevel;
    }

    public void setSymptomLevel(SymptomLevel p_symptomLevel) {
        this.symptomLevel = p_symptomLevel;
    }

    public void setHospitalID(String p_HospitalID) {
//    	System.out.println("Setting hos_ID");
        this.HospitalID = p_HospitalID;
    }

    public SymptomLevel getSymptomLevel() {
        return this.symptomLevel;
    }

    public String getHospitalID() {
        return this.HospitalID;
    }
}
