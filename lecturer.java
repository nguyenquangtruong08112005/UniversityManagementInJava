public class lecturer extends employee {	
	// Attributes
	private String faculty;
	private String degree;

	// Constructor
	public lecturer(String employeeID, String fullName, double salaryCoefficient, int workingDay, String faculty, String degree) {
		super(employeeID, fullName, salaryCoefficient, workingDay);
		this.faculty = faculty;
		this.degree = degree;
	}

	// Getter
	public String getFaculty() {return this.faculty;}
	public String getDegree() {return this.degree;}

	// Setter
	public void setFaculty(String newFaculty) {this.faculty = newFaculty;}
	public void setDegree(String newDegree) {this.degree = newDegree;}

	@Override
	public double getSalary() {return super.salaryCoefficient*830000 + getAllowance() + workingDay*30;}

	@Override
	public String toString() {return super.toString() + "_" + this.faculty + "_" + this.degree + "_" + getAllowance();}

	@Override 
	public double getAllowance() {
		int allowance = 0;
		if(this.degree.equals("Bachelor"))
			allowance = 300000;
		else if(this.degree.equals("Master"))
			allowance = 500000;
		else if(this.degree.equals("Doctor"))
			allowance = 1000000;
		return allowance;
	}
}
