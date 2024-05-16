public class staff extends employee {
	// Attributes
	private String department;
	private String duty;

	// Constructor
	public staff(String employeeID, String fullName, double salaryCoefficient, int workingDay, String department, String duty) {
		super(employeeID, fullName, salaryCoefficient, workingDay);
		this.department = department;
		this.duty = duty;
	}

	// Getter
	public String getDepartment() {return this.department;}
	public String getDuty() {return this.duty;}

	// Setter
	public void setDepartment(String newDepartment) {this.department = newDepartment;}
	public void setDuty(String newDuty) {this.duty = newDuty;}

	@Override
	public double getSalary() {return super.salaryCoefficient*830000 + getAllowance() + workingDay*50;}

	@Override
	public String toString() {return super.toString() + "_" + this.department + "_" + this.duty + "_" + getAllowance();}

	@Override
	public double getAllowance() {
		int allowance = 0;
		if(this.duty.equals("Excutive Staff"))
			allowance = 500000;
		else if(this.duty.equals("Deputy head of department"))
			allowance = 100000;
		else if(this.duty.equals("Head of department"))
			allowance = 1300000;
		return allowance;
	}
}
