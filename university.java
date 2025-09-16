
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;



public class university {
    /*----------------------------------------------------------------------Attributes----------------------------------------------------------------------*/
	private ArrayList<employee> employeeList;    // employeeID, fullName,  salaryCoefficient, workingDay
	// private ArrayList<lecturer> lecturerList; // employeeID, fullName,  salaryCoefficient, workingDay, faculty,    degree
	// private ArrayList<staff> staffList;		 // employeeID, fullName,  salaryCoefficient, workingDay, department, duty

    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    /*----------------------------------------------------------------------Attributes----------------------------------------------------------------------*/


 /*----------------------------------------------------------------------Constructor--------------------------------------------------------------------*/
    public university(String employeePath) {
        this.employeeList = loadInEmployeeList(employeePath); // C:\\Users\\Nguyen Quang
        // Huy\\Desktop\\EmployeeManagementSystemInJva\\employeeDetails.txt

		// format of the input file: employeeID,fullName,salaryCoefficient,workingDay,faculty/department,degree/duty with no spaces
        // E.g: LT1001,Doan Xuan Thanh,5.6,100,IT,Doctor
		// allowance will be compute by getAllowance() method in class staff and lecturer after we know employee's salaryCoefficient, degree and workingDay  
			// Also allowance will not initialize as a variable and not available in input file

        // this.lecturerList = getLecturerList();
        // this.staffList = getStaffList();
    }

    /*----------------------------------------------------------------------Constructor--------------------------------------------------------------------*/


 /*----------------------------------------------------------------------Getters------------------------------------------------------------------------*/
	public ArrayList<employee> getEmployeeList() {return this.employeeList;}
    /*----------------------------------------------------------------------Getters------------------------------------------------------------------------*/


    /*----------------------------------------------------------------------Setters------------------------------------------------------------------------*/
	public void setEmployeeList(ArrayList<employee> newEmployeeList) {this.employeeList = newEmployeeList;}
	/*----------------------------------------------------------------------Setters------------------------------------------------------------------------*/


 /*--------------------------------------------------------------------Main Method----------------------------------------------------------------------*/
	// 1. Load in employee details by loadFile method. Store it in a dynamic array: ArrayList with data type is employee
    public ArrayList<employee> loadInEmployeeList(String employeePath) {
        ArrayList<employee> employeeList = new ArrayList<employee>();

		ArrayList<String> employeeList_String =  loadFile(employeePath);

		for(String employee_one : employeeList_String) {
            String[] details = employee_one.split(",");
			if(details[0].startsWith("LT")) {
				employeeList.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]), Integer.parseInt(details[3]), details[4], details[5]));
			}
			else if(details[0].startsWith("ST")) 
				employeeList.add(new staff(details[0], details[1], Double.parseDouble(details[2]), Integer.parseInt(details[3]), details[4], details[5]));
        }
        return employeeList;
    }

	// 2. Get lecturerList from employeeList
	public ArrayList<lecturer> getLecturerList(ArrayList<employee> employeeList) {
		ArrayList<lecturer> lecturerList = new ArrayList<lecturer>();		

		for(employee employee_one  : employeeList) {
			if(employee_one.getEmployeeID().startsWith("LT")) {
				String[] details = employee_one.toString().split("_"); 
				lecturerList.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]), Integer.parseInt(details[3]), details[4], details[5]));
			}
		}
		return lecturerList;
	}

	// 3. Get staffList from employeeList
	public ArrayList<staff> getStaffList(ArrayList<employee> employeeList) {
		ArrayList<staff> staffList = new ArrayList<staff>();

		for(employee employee_one  : employeeList) {
			if(employee_one.getEmployeeID().startsWith("ST")) {
				String[] details = employee_one.toString().split("_");
				staffList.add(new staff(details[0], details[1], Double.parseDouble(details[2]), Integer.parseInt(details[3]), details[4], details[5]));
			}
		}
		return staffList;
	}
    
    // 4. Get ITlecturerList from employeeList
    public ArrayList<lecturer> getLecturerOfITFacultyList() {
        ArrayList<lecturer> lecturerList = getLecturerList(this.employeeList);
        ArrayList<lecturer> lecturerOfITFacultyList = new ArrayList<lecturer>();

		for(lecturer lecturer_one : lecturerList) {
			if(lecturer_one.getFaculty().equals("IT")) {
                String[] details = lecturer_one.toString().split("_");
				lecturerOfITFacultyList.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]), Integer.parseInt(details[3]), details[4], details[5]));
            }
        }
        return lecturerOfITFacultyList;
    }

    // 5. Get specific lecturer by their name from ITLecturerList
    public ArrayList<lecturer> getSpecificLecturerOFITFacultyList_ByName(String name) {
        ArrayList<lecturer> lecturerOfITFacultyList = getLecturerOfITFacultyList();
        ArrayList<lecturer> specificLecturerOfITFacultyList = new ArrayList<lecturer>();

		for(lecturer lecturer_one : lecturerOfITFacultyList) {
			if(lecturer_one.getFullName().endsWith(name)) {
                String[] details = lecturer_one.toString().split("_");
				specificLecturerOfITFacultyList.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]), Integer.parseInt(details[3]), details[4], details[5]));
            }
        }
        return specificLecturerOfITFacultyList;
    }

    // 6. Get employeeID and workingDay of lecturer
    public LinkedHashMap<String, Integer> getEmployeeID_WorkingDayOfLecturer() {
        ArrayList<lecturer> lecturerList = getLecturerList(this.employeeList);
        LinkedHashMap<String, Integer> employeeID_WorkingDayOfLecturer = new LinkedHashMap<String, Integer>();

        for(lecturer lecturer_one : lecturerList) {
            String[] details = lecturer_one.toString().split("_");
            employeeID_WorkingDayOfLecturer.put(details[0], Integer.parseInt(details[3]));
        }

        return employeeID_WorkingDayOfLecturer;
    }

    // 7. Get employeeID and workingDay of staff
    public LinkedHashMap<String, Integer> getEmployeeID_WorkingDayOfStaff() {
        ArrayList<staff> staffList = getStaffList(this.employeeList);
        LinkedHashMap<String, Integer> employeeID_WorkingDayOfStaff = new LinkedHashMap<String, Integer>();

        for (staff staff_one : staffList) {
            String[] details = staff_one.toString().split("_");
            employeeID_WorkingDayOfStaff.put(details[0], Integer.parseInt(details[3]));
        }
        return employeeID_WorkingDayOfStaff;
    }

    // 8. Get the staff who work hardest
    public LinkedHashMap<String, Integer> getEmployeeID_WorkingDayOfHardestStaff() {
        LinkedHashMap<String, Integer> employeeID_WorkingDayOfStaff = getEmployeeID_WorkingDayOfStaff();
        LinkedHashMap<String, Integer> employeeID_WorkingDayOfHardestStaff = new LinkedHashMap<String, Integer>();

        String key_max = "";
        int values_max = Collections.max(employeeID_WorkingDayOfStaff.values());

        for (String key : employeeID_WorkingDayOfStaff.keySet()) {
            if (values_max == employeeID_WorkingDayOfStaff.get(key)) {
                employeeID_WorkingDayOfHardestStaff.put(key, employeeID_WorkingDayOfStaff.get(key));
            }
        }

        return employeeID_WorkingDayOfHardestStaff;
    }

    // 9. Get the lecturer who work hardest
    public LinkedHashMap<String, Integer> getEmployeeID_WorkingDayOfHardestLecturer() {
        LinkedHashMap<String, Integer> employeeID_WorkingDayOfLecturer = getEmployeeID_WorkingDayOfLecturer();
        LinkedHashMap<String, Integer> employeeID_WorkingDayOfHardestLecturer = new LinkedHashMap<String, Integer>();

        String key_max = "";
        int values_max = Collections.max(employeeID_WorkingDayOfLecturer.values());

        for (String key : employeeID_WorkingDayOfLecturer.keySet()) {
            if (values_max == employeeID_WorkingDayOfLecturer.get(key)) {
                employeeID_WorkingDayOfHardestLecturer.put(key, employeeID_WorkingDayOfLecturer.get(key));
            }
        }

        return employeeID_WorkingDayOfHardestLecturer;
    }

    // 10. Get employeeID and salary of lecturer
    public LinkedHashMap<String, Double> getEmployeeID_SalaryOfLecturer() {
        ArrayList<lecturer> lecturerList = getLecturerList(this.employeeList);
        LinkedHashMap<String, Double> employeeID_SalaryOfLecturer = new LinkedHashMap<String, Double>();

        for (lecturer lecturer_one : lecturerList) {
            employeeID_SalaryOfLecturer.put(lecturer_one.getEmployeeID(), lecturer_one.getSalary());
        }

        return employeeID_SalaryOfLecturer;
    }

    // 11. Get employeeID and salary of staff
    // return ID and Salary
    public LinkedHashMap<String, Double> getEmployeeID_SalaryOfStaff() {
        ArrayList<staff> staffList = getStaffList(this.employeeList);
        LinkedHashMap<String, Double> employeeID_SalaryOfStaff = new LinkedHashMap<String, Double>();

        for (staff staff_one : staffList) {
            employeeID_SalaryOfStaff.put(staff_one.getEmployeeID(), staff_one.getSalary());
        }

        return employeeID_SalaryOfStaff;
    }

    // 12. Get the lecturer who has the highest salary
    public LinkedHashMap<String, Double> getEmployeeID_HighestSalaryOfLecturer() {
        LinkedHashMap<String, Double> employeeID_SalaryOfLecturer = getEmployeeID_SalaryOfLecturer();
        LinkedHashMap<String, Double> employeeID_HighestSalaryOfLecturer = new LinkedHashMap<String, Double>();

        String key_max = "";
        double values_max = Collections.max(employeeID_SalaryOfLecturer.values());

        for (String key : employeeID_SalaryOfLecturer.keySet()) {
            if (values_max == employeeID_SalaryOfLecturer.get(key)) {
                employeeID_HighestSalaryOfLecturer.put(key, employeeID_SalaryOfLecturer.get(key));
            }
        }

        return employeeID_HighestSalaryOfLecturer;
    }

    // 13. Get the staff who has the highest salary
    public LinkedHashMap<String, Double> getEmployeeID_HighestSalaryOfStaff() {
        // Get LinkedHashMap containing employee ID and salary of staff
        LinkedHashMap<String, Double> employeeID_SalaryOfStaff = getEmployeeID_SalaryOfStaff();
        // LinkedHashMap to store employee ID(s) of staff with the highest salary
        LinkedHashMap<String, Double> employeeID_HighestSalaryOfStaff = new LinkedHashMap<String, Double>();

        // Find the maximum salary among staff
        double values_max = Collections.max(employeeID_SalaryOfStaff.values());

        // Iterate through the staff salaries to find those matching the maximum salary
        for (String key : employeeID_SalaryOfStaff.keySet()) {
            if (values_max == employeeID_SalaryOfStaff.get(key)) {
                // Add the staff with the highest salary to the result LinkedHashMap
                employeeID_HighestSalaryOfStaff.put(key, employeeID_SalaryOfStaff.get(key));
            }
        }

        return employeeID_HighestSalaryOfStaff;
    }

    // 14. Get lecturer details by employee ID
    public ArrayList<lecturer> getLecturerList_ByID(LinkedHashMap<String, Double> employeeID) {
        ArrayList<lecturer> lecturerListFromID = new ArrayList<lecturer>();

        // Iterate through the provided employee ID(s)
        for (String key : employeeID.keySet()) {
            // Search for matching lecturer details in the employee list
            for (lecturer lecturer_one : getLecturerList(this.employeeList)) {
                // If a match is found, add the lecturer details to the result list
                if (key.equals(lecturer_one.getEmployeeID())) {
                    String[] details = lecturer_one.toString().split("_");
                    lecturerListFromID.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]),
                            Integer.parseInt(details[3]), details[4], details[5]));
                }
            }
        }

        return lecturerListFromID;
    }

    // 15. Get staff details by employee ID
    public ArrayList<staff> getStaffList_ByID(LinkedHashMap<String, Double> employeeID) {
        ArrayList<staff> staffListFromID = new ArrayList<staff>();

        // Iterate through the provided employee ID(s)
        for (String key : employeeID.keySet()) {
            // Search for matching staff details in the employee list
            for (staff staff_one : getStaffList(this.employeeList)) {
                // If a match is found, add the staff details to the result list
                if (key.equals(staff_one.getEmployeeID())) {
                    String[] details = staff_one.toString().split("_");
                    staffListFromID.add(new staff(details[0], details[1], Double.parseDouble(details[2]),
                            Integer.parseInt(details[3]), details[4], details[5]));
                }
            }
        }

        return staffListFromID;
    }

    // 16. Increase salary coefficient of all employees
    public ArrayList<employee> increaseSalaryCoefficientOfAllEmployee(double increasingRate) {	
            ArrayList<employee> newEmployeeList = new ArrayList<employee>();
    
            for(employee employee_one  : this.employeeList) {
                if(employee_one.getEmployeeID().startsWith("LT")) {
                    String[] details = employee_one.toString().split("_");
                    newEmployeeList.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]) * (1 + increasingRate), Integer.parseInt(details[3]), details[4], details[5]));
                }
                else if(employee_one.getEmployeeID().startsWith("ST")) {
                    String[] details = employee_one.toString().split("_");
                    newEmployeeList.add(new staff(details[0], details[1], Double.parseDouble(details[2]) * (1 + increasingRate), Integer.parseInt(details[3]), details[4], details[5]));
                }
            }
            this.employeeList = newEmployeeList;
            return newEmployeeList;
        }
    
    // 17. Decrease salary coefficient of all employees
    public ArrayList<employee> decreaseSalaryCoefficientOfAllEmployee(double decreasingRate) {	
            ArrayList<employee> newEmployeeList = new ArrayList<employee>();
    
            for(employee employee_one  : this.employeeList) {
                if(employee_one.getEmployeeID().startsWith("LT")) {
                    String[] details = employee_one.toString().split("_");
                    newEmployeeList.add(new lecturer(details[0], details[1], Double.parseDouble(details[2]) * (1 - decreasingRate), Integer.parseInt(details[3]), details[4], details[5]));
                }
                else if(employee_one.getEmployeeID().startsWith("ST")) {
                    String[] details = employee_one.toString().split("_");
                    newEmployeeList.add(new staff(details[0], details[1], Double.parseDouble(details[2]) * (1 - decreasingRate), Integer.parseInt(details[3]), details[4], details[5]));
                }
            }
            this.employeeList = newEmployeeList;
            return newEmployeeList;
        }    
    
    // 18. Get employee ID and salary of lecturers with salary higher than a
    // specified value
    public LinkedHashMap<String, Double> getEmployeeID_LecturerHaveSalaryHigherThan(double lowerBound) {
        LinkedHashMap<String, Double> employeeID_SalaryOfLecturer = getEmployeeID_SalaryOfLecturer();
        LinkedHashMap<String, Double> lecturerHaveSalaryHigherThan = new LinkedHashMap<String, Double>();

        // Iterate through the salaries of lecturers
        for (String key : employeeID_SalaryOfLecturer.keySet()) {
            // If salary is higher than the specified value, add it to the result
            // LinkedHashMap
            if (lowerBound <= employeeID_SalaryOfLecturer.get(key)) {
                lecturerHaveSalaryHigherThan.put(key, employeeID_SalaryOfLecturer.get(key));
            }
        }

        return lecturerHaveSalaryHigherThan;
    }

// 19. Get employee ID and salary of staff with salary higher than a specified value
    public LinkedHashMap<String, Double> getEmployeeID_StaffHaveSalaryHigherThan(double lowerBound) {
        LinkedHashMap<String, Double> employeeID_StaffOfStaff = getEmployeeID_SalaryOfStaff();
        LinkedHashMap<String, Double> employeeID_staffHaveSalaryHigherThan = new LinkedHashMap<String, Double>();

        // Iterate through the salaries of staff
        for (String key : employeeID_StaffOfStaff.keySet()) {
            // If salary is higher than the specified value, add it to the result LinkedHashMap
            if (lowerBound <= employeeID_StaffOfStaff.get(key)) {
                employeeID_staffHaveSalaryHigherThan.put(key, employeeID_StaffOfStaff.get(key));
            }
        }

        return employeeID_staffHaveSalaryHigherThan;
    }
    // 20. Sort staff/lecturer ascending/descending
    public LinkedHashMap<String, Double> getEmployee_SalarySorted(boolean ascending, boolean isLecturer) {
        // Get LinkedHashMap containing employee ID and salary
        LinkedHashMap<String, Double> employeeID_Salary = isLecturer ? getEmployeeID_SalaryOfLecturer() : getEmployeeID_SalaryOfStaff();
    
        // LinkedHashMap to store result sorted by salary
        LinkedHashMap<String, Double> res = new LinkedHashMap<>();
    
        // Convert employeeID_Salary to a List of Map.Entry for sorting
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(employeeID_Salary.entrySet());
    
        // Sort the entryList by values (salaries)
        Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (ascending) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return -o1.getValue().compareTo(o2.getValue());
                }
            }
        });
    
        // Iterate over sorted entryList and put entries into the result LinkedHashMap
        for (Map.Entry<String, Double> entry : entryList) {
            res.put(entry.getKey(), entry.getValue());
        }
    
        // Return the result LinkedHashMap sorted by salary
        return res;
    }
    
    // 25. Write employeeList to file
    public void writeEmployeeListToFile(String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (employee employee : employeeList) {
                StringBuilder line = new StringBuilder();
                line.append(employee.getEmployeeID()).append(",");
                line.append(employee.getFullName()).append(",");
                line.append(employee.getSalaryCoefficient()).append(",");
                line.append(employee.getWorkingDay()).append(",");
    
                if (employee instanceof staff) {
                    staff staff = (staff) employee;
                    line.append(staff.getDepartment()).append(",");
                    line.append(staff.getDuty());
                } else if (employee instanceof lecturer) {
                    lecturer lecturer = (lecturer) employee;
                    line.append(lecturer.getFaculty()).append(",");
                    line.append(lecturer.getDegree());
                }
    
                writer.write(line.toString());
                writer.newLine(); // Write a new line after each employee
            }
            writer.close();
            System.out.println("Employee list has been successfully written to file: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////
    public void printOutEmployeeList() {
        int i = 1, j = 1;
        printOutNumberOfEmployee();
        System.out.println("--------------------------List of lecturer--------------------------");
        for (lecturer lecturer_one : getLecturerList(this.employeeList)) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary Coefficient: " + decimalFormat.format(Double.parseDouble(details[2])));
            System.out.println("Working day: " + decimalFormat.format(Integer.parseInt(details[3])));
            System.out.println("Faculty: " + details[4]);
            System.out.println("Degree: " + details[5]);
            System.out.println("Allowance: " + decimalFormat.format(lecturer_one.getAllowance()));
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }

        System.out.println("--------------------------List of staff--------------------------");
        for (staff staff_one : getStaffList(this.employeeList)) {
            String[] details = staff_one.toString().split("_");
            System.out.println("Staff " + j);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary Coefficient: " + decimalFormat.format(Double.parseDouble(details[2])));
            System.out.println("Working day: " + decimalFormat.format(Integer.parseInt(details[3])));
            System.out.println("Department: " + details[4]);
            System.out.println("Duty: " + details[5]);
            System.out.println("Allowance: " + decimalFormat.format(staff_one.getAllowance()));
            System.out.println("Salary: " + decimalFormat.format(staff_one.getSalary()) + "\n");
            j++;
        }
    }

    public void printOutLecturer() {
        int i = 1;
        System.out.println("--------------------------List of lecturer--------------------------");
        for (lecturer lecturer_one : getLecturerList(this.employeeList)) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary Coefficient: " + decimalFormat.format(Double.parseDouble(details[2])));
            System.out.println("Working day: " + decimalFormat.format(Integer.parseInt(details[3])));
            System.out.println("Faculty: " + details[4]);
            System.out.println("Degree: " + details[5]);
            System.out.println("Allowance: " + decimalFormat.format(lecturer_one.getAllowance()));
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }
    }

    public void printOutStaff() {
        int j = 1;
        System.out.println("--------------------------List of staff--------------------------");
        for (staff staff_one : getStaffList(this.employeeList)) {
            String[] details = staff_one.toString().split("_");
            System.out.println("Staff " + j);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary Coefficient: " + decimalFormat.format(Double.parseDouble(details[2])));
            System.out.println("Working day: " + decimalFormat.format(Integer.parseInt(details[3])));
            System.out.println("Department: " + details[4]);
            System.out.println("Duty: " + details[5]);
            System.out.println("Allowance: " + decimalFormat.format(staff_one.getAllowance()));
            System.out.println("Salary: " + decimalFormat.format(staff_one.getSalary()) + "\n");
            j++;
        }
    }

    public void printOutITLecturer() {
        int i = 1;
        // printOutNumberOfLecturer();
        System.out.println("--------------------------List of IT lecturer--------------------------");

        for (lecturer lecturer_one : getLecturerOfITFacultyList()) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary Coefficient: " + decimalFormat.format(Double.parseDouble(details[2])));
            System.out.println("Working day: " + decimalFormat.format(Integer.parseInt(details[3])));
            System.out.println("Faculty: " + details[4]);
            System.out.println("Degree: " + details[5]);
            System.out.println("Allowance: " + decimalFormat.format(lecturer_one.getAllowance()));
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }
    }

    public void printOutSalaryOFITLecturerList_ByName(String name) {
        int i = 1;
   		System.out.println("--------------------------Salary of IT lecturer whose name is Khai--------------------------");

        for (lecturer lecturer_one : getSpecificLecturerOFITFacultyList_ByName(name)) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }
    }

    public void printOutEmployeeWhoseHasTheHighestSalary() {
        int i = 1, j = 1;
        System.out.println(
                "--------------------------List of lecturer who has the highest salary--------------------------");
        for (lecturer lecturer_one : getLecturerList_ByID(getEmployeeID_HighestSalaryOfLecturer())) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }

        System.out.println(
                "--------------------------List of staff who has the highest salary--------------------------");
        for (staff staff_one : getStaffList_ByID(getEmployeeID_HighestSalaryOfStaff())) {
            String[] details = staff_one.toString().split("_");
            System.out.println("Staff " + j);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary Coefficient: " + decimalFormat.format(Double.parseDouble(details[2])));
            System.out.println("Working day: " + decimalFormat.format(Integer.parseInt(details[3])));
            System.out.println("Duty: " + details[4]);
            System.out.println("Department: " + details[5]);
            System.out.println("Allowance: " + decimalFormat.format(staff_one.getAllowance()));
            System.out.println("Salary: " + decimalFormat.format(staff_one.getSalary()) + "\n");
            j++;
        }
    }

    public void printOutSalaryOfEmployeeAfterIncreaseSalaryCoefficient(double increasingRate) {
        ArrayList<lecturer> newLecturerList = getLecturerList(increaseSalaryCoefficientOfAllEmployee(increasingRate));
        ArrayList<staff> newStaffList = getStaffList(increaseSalaryCoefficientOfAllEmployee(increasingRate));

        int i = 1, j = 1;
        System.out.println(
                "--------------------------List of lecturer after increasing salary coefficient--------------------------");
        for (lecturer lecturer_one : newLecturerList) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }
        System.out.println(
                "--------------------------List of staff after increasing salary coefficient--------------------------");
        for (staff staff_one : newStaffList) {
            String[] details = staff_one.toString().split("_");
            System.out.println("Staff " + j);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary: " + decimalFormat.format(staff_one.getSalary()) + "\n");
            j++;
        }
    }

    public void printOutSalaryOfEmployeeAfterDecreaseSalaryCoefficient(double decreasingRate) {
        ArrayList<lecturer> newLecturerList = getLecturerList(increaseSalaryCoefficientOfAllEmployee(decreasingRate));
        ArrayList<staff> newStaffList = getStaffList(increaseSalaryCoefficientOfAllEmployee(decreasingRate));

        int i = 1, j = 1;
        System.out.println(
                "--------------------------List of lecturer after decreasing salary coefficient--------------------------");
        for (lecturer lecturer_one : newLecturerList) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer " + i);
            System.out.println("Full Name: " + details[1]);
            System.out.println("Salary: " + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }
        System.out.println(
                "--------------------------List of staff after decreasing salary coefficient--------------------------");
        for (staff staff_one : newStaffList) {
            String[] details = staff_one.toString().split("_");
            System.out.println("Staff \t" + j);
            System.out.println("Full Name: \t" + details[1]);
            System.out.println("Salary: \t" + decimalFormat.format(staff_one.getSalary()) + "\n");
            j++;
        }
    }

    public void printOutTotalSalaryOfEmployee() {
        double totalSalary = 0;

        for (lecturer lecturer_one : getLecturerList(this.employeeList)) {
            totalSalary += lecturer_one.getSalary();
        }

        for (staff staff_one : getStaffList(this.employeeList)) {
            totalSalary += staff_one.getSalary();
        }

        System.out.println(
                "Total of Salary university have to pay for its employee: " + decimalFormat.format(totalSalary) + "\n");
    }

    public void printOutEmployeeHaveSalaryHigherThan(double lowerBound) {

        ArrayList<staff> employeeID_StaffHaveSalaryHigherThan = getStaffList_ByID(
                getEmployeeID_StaffHaveSalaryHigherThan(lowerBound));
        ArrayList<lecturer> employeeID_LecturerHaveSalaryHigherThan = getLecturerList_ByID(
                getEmployeeID_LecturerHaveSalaryHigherThan(lowerBound));

        int i = 1, j = 1;
        System.out.printf("--------------------------List of lecturer have salary higher than "
                + decimalFormat.format(lowerBound) + "--------------------------\n");
        for (lecturer lecturer_one : employeeID_LecturerHaveSalaryHigherThan) {
            String[] details = lecturer_one.toString().split("_");
            System.out.println("Lecturer:\t" + i);
            System.out.println("Full Name: \t" + details[1]);
            System.out.println("Salary: \t" + decimalFormat.format(lecturer_one.getSalary()) + "\n");
            i++;
        }

        System.out.printf("--------------------------List of staff have salary higher than "
                + decimalFormat.format(lowerBound) + "--------------------------\n");
        for (staff staff_one : employeeID_StaffHaveSalaryHigherThan) {
            String[] details = staff_one.toString().split("_");
            System.out.println("Staff \t" + j);
            System.out.println("Full Name: \t" + details[1]);
            System.out.println("Salary: \t" + decimalFormat.format(staff_one.getSalary()) + "\n");
            j++;
        }
    }
    public void printOutNumberOfEmployee(){
        int numberOfEmployee = 0;
        for (employee employee : employeeList) {
            numberOfEmployee++;
        }
        System.out.println("\tNumber of employee: " + numberOfEmployee);
    }
    public void printOutNumberOfLecturer() {
        int numberOfLecturer = 0;
        for (lecturer lecturer_one : getLecturerList(this.employeeList)) {
            numberOfLecturer++;
        }
        System.out.println("\tNumber of lecturer: " + numberOfLecturer);
    }

    public void printOutNumberOfStaff() {
        int numberOfStaff = 0;
        for (staff staff_one : getStaffList(this.employeeList)) {
            numberOfStaff++;
        }
        System.out.println("\tNumber of staff: " + numberOfStaff);
    }

    // Method to print staff/lecturer list in ascending order
    public void printAscending(boolean isLecturer) {
        LinkedHashMap<String, Double> sortedEmployeeSalary = getEmployee_SalarySorted(true, isLecturer);
        String employeeType = isLecturer ? "Lecturer" : "Staff";
        int i = 1;

        System.out.println("--------------------------List of " + employeeType + " in ascending order--------------------------");
        for (Map.Entry<String, Double> entry : sortedEmployeeSalary.entrySet()) {
            String employeeID = entry.getKey();
            double salary = entry.getValue();

            System.out.println(employeeType + " " + i);
            System.out.println("Employee ID: " + employeeID);
            System.out.println("Salary: " + salary);
            System.out.println();
            i++;
        }
    }

    // Method to print staff/lecturer list in descending order
    public void printDescending(boolean isLecturer) {
        LinkedHashMap<String, Double> sortedEmployeeSalary = getEmployee_SalarySorted(false, isLecturer);
        String employeeType = isLecturer ? "Lecturer" : "Staff";
        int i = 1;

        System.out.println("--------------------------List of " + employeeType + " in descending order--------------------------");
        for (Map.Entry<String, Double> entry : sortedEmployeeSalary.entrySet()) {
            String employeeID = entry.getKey();
            double salary = entry.getValue();

            System.out.println(employeeType + " " + i);
            System.out.println("Employee ID: " + employeeID);
            System.out.println("Salary: " + salary);
            System.out.println();
            i++;
        }
    }
    public static ArrayList<String> loadFile(String filePath) {
        String inputData = "";
        ArrayList<String> outputData = new ArrayList<String>();

        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader fReader = new BufferedReader(reader);

            while ((inputData = fReader.readLine()) != null) {
                outputData.add(inputData);
            }

            fReader.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("ERROR CANNOT LOAD FILE");
        }

        return outputData;
    }

}