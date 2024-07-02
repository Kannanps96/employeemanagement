package com.example.employeemanagement.entities;

import java.util.Date;
import java.util.StringJoiner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(schema = "employeemanagement", catalog = "employeemanagement", name = "employee_details")
@Data
public class EmployeeDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	@Column(name = "title_name")
	private String titleName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "dob")
    private Date dateOfBirth;
	@Column(name = "salary")
    private Double salary;
    @Column(name = "address")
    private String address;
    @Column(name = "role")
    private String role;
    @Column(name = "joining_date")
    private Date joiningDate;
    @Column(name = "yearly_bonus_percentage")
    private Double yearlyBonusPercentage;
    @ManyToOne
    @JoinColumn(name = "reporting_manager_id")
    private EmployeeDetails reportingManager;
	
	
	public String getEmployeeFullName() {
        StringJoiner fullName = new StringJoiner(" ");
        if (this.getTitleName() != null && !this.getTitleName().trim().isEmpty()) {
            fullName.add(this.getTitleName().trim());
        }
        if (this.getFirstName() != null && !this.getFirstName().trim().isEmpty()) {
            fullName.add(this.getFirstName().trim());
        }
        if (this.getLastName() != null && !this.getLastName().trim().isEmpty()) {
            fullName.add(this.getLastName().trim());
        }
        return fullName.toString();
    }
	
	

}
