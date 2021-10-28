package com.self.parser;

import java.util.Calendar;

/**
 * Person model object
 * 
 * @author Jagadheeswar Reddy
 *
 */
public class Person implements Comparable<Person> {

	// person id should be String as need to show in 001 format
	private String personId;
	private Integer month;
	private Integer day;
	private Integer year;

	/**
	 * @return personId get personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId set personId
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return month get person DOB- month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * @param month set person DOB - month
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * @return day get person DOB - day
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * @param day set person DOB - day
	 */
	public void setDay(Integer day) {
		this.day = day;
	}

	/**
	 * @return year get person DOB- year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year set person DOB- year
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", month=" + month + ", day=" + day + ", year=" + year + "]";
	}

	/**
	 * used to sort the list with Collections.sort function
	 */
	@Override
	public int compareTo(Person person) {

		if (this.getYear() < person.getYear())
			return -1;
		else if (this.getYear() > person.getYear())
			return 1;
		else if (this.getMonth() < person.getMonth())
			return -1;
		else if (this.getMonth() > person.getMonth())
			return 1;
		else if (this.getDay() < person.getDay())
			return -1;
		else if (this.getDay() > person.getDay())
			return 1;
		else
			return 0;
	}

	/**
	 * Need to convert person DOB to milliSeconds to differentiate person age
	 * 
	 * @return
	 */
	public long customHashCode() {

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		return cal.getTimeInMillis();

	}

}
