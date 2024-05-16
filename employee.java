public abstract class employee {
	// Attributes
	protected String employeeID;
	protected String fullName;
	protected double salaryCoefficient;
	protected int workingDay;
	// allowance will be compute after load in employee's details. 
	// So we dont put it the constructor and initialize it as a attributes

	// Contrustor
	public employee(String employeeID, String fullName, double salaryCoefficient, int workingDay) {
		this.employeeID = employeeID;
		this.fullName = fullName;
		this.salaryCoefficient = salaryCoefficient;
		this.workingDay = workingDay;
	}

	// Getter
	public String getEmployeeID() {return this.employeeID;}
	public String getFullName() {return this.fullName;}
	public double getSalaryCoefficient() {return this.salaryCoefficient;}
	public int getWorkingDay() {return this.workingDay;}

	// Setter
	public void setFullName(String newName) {this.fullName = newName;}
	public void setEmployeeID(String newID) {this.employeeID = newID;}
	public void setSalaryCoefficient(double newSalaryCoefficient) {this.salaryCoefficient = newSalaryCoefficient;}
	public void setWorkingDay(int newWorkingDay) {this.workingDay = newWorkingDay;}

	//
	@Override
	public String toString() {return this.employeeID + "_" + this.fullName + "_" + this.salaryCoefficient + "_" + this.workingDay;}

	public abstract double getSalary();
	public abstract double getAllowance();
//
}

