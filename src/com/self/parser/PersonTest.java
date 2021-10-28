package com.self.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * This Java program reads the input file, sort the records by the full date of
 * birth (i.e. sort by all three columns: month, day and year. Sort the records
 * with same year by month, and same month by day) in ascending order (older
 * person first), and print to standard out.
 * 
 * @author Jagadheeswar Reddy
 *
 */
public class PersonTest {

	/**
	 * Starting point of the Program execution
	 * 
	 * @param args input arguments
	 */
	public static void main(String[] args) {

		PersonTest personTest = new PersonTest();
		// input data file path
		String filePath = "resources/input.txt";
		List<Person> personList = personTest.readInputFile(filePath);
		// check personList has elements or not
		if (personList.size() > 0) {
			// ExecutorService for ThreadPool Feature
			ExecutorService executorService = Executors.newFixedThreadPool(2);

			// used to execute code on a concurrent thread.
			Runnable sortTask = () -> {
				personTest.sortPersonList(personList);
			};
			executorService.submit(sortTask); // Start thread execute task
			executorService.submit(sortTask);

			Runnable customSortTask = () -> {
				personTest.customSortPersonList(personList);
			};
			executorService.submit(customSortTask);
			executorService.submit(customSortTask);

			// Once done all threads execution shutdown executorService to stop waiting for
			// any more tasks
			// will just tell the executor service that it can't accept new tasks, but the
			// already submitted tasks continue to run
			executorService.shutdown();
			try {
				// for smooth shutdown - executorService - wait for 1 second before
				// kill/termination
				if (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				// shutdown executorService immediately with cancel the already submitted tasks
				// by interrupting the relevant threads.
				executorService.shutdownNow();
			}

		}

	}

	/**
	 * Takes input file path and read data line by line and convert it to Person
	 * object list
	 * 
	 * @param filePath input file path
	 * @return List<Person> return persons list
	 */
	public List<Person> readInputFile(String filePath) {
		List<Person> personList = new ArrayList<>();
		// read file into stream of lines
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {

			lines.forEach(line -> {
				// Separate the line with single space or multiple space using regex \s+
				// convert string to integer for day,month and year
				String columns[] = line.split("\\s+");
				try {
					Person person = new Person();
					person.setPersonId(columns[0]);
					person.setMonth(Integer.valueOf(columns[1]));
					person.setDay(Integer.valueOf(columns[2]));
					person.setYear(Integer.valueOf(columns[3]));

					personList.add(person);
				} catch (NumberFormatException e) {
					// Catch error about parsing columns
					System.out.println("Exception Occured during parse the columns : " + e);
				}

			});

		} catch (IOException e) {
			// Catch error about file path like invalid file path etc.
			System.out.println("Exception Occured during read the input file: " + e);
		}

		return personList;
	}

	/**
	 * @param personList Take personList as input and apply Collections - Stream and
	 *                   Sort feature
	 */
	synchronized public void sortPersonList(List<Person> personList) {

		List<Person> sortedList = personList.stream().sorted(Comparator.comparingInt(Person::getYear)
				.thenComparingInt(Person::getMonth).thenComparingInt(Person::getDay)).collect(Collectors.toList());
		System.out.format("%s\t%s\t%s\t%s\t%s\n", "Thread ID", "Person ID", "Month", "Day", "Year");
		sortedList.stream().forEach(person -> {
			System.out.format("%d\t\t%s\t\t%d\t%d\t%d\n", Thread.currentThread().getId(), person.getPersonId(),
					person.getMonth(), person.getDay(), person.getYear());
		});
		System.out.println();
	}

	/**
	 * @param personList Take personList as input and apply Custom Sorting algorithm
	 *                   as we are using QuickSort algorithm so SPACE COMPLEXITY -
	 *                   O(n), AVERAGE TIME COMPLEXITY - O(n*log(n)) , BEST TIME
	 *                   COMPLEXITY - O(n*log(n)), WORST TIME COMPLEXITY-O(n^2)
	 */
	synchronized public void customSortPersonList(List<Person> personList) {

		// Collections.sort(personList);

		Person[] sortPersons = personList.toArray(new Person[personList.size()]);
		sort(sortPersons, 0, personList.size() - 1);

		System.out.format("%s\t%s\t%s\t%s\t%s\n", "Thread ID", "Person ID", "Month", "Day", "Year");
		for (Person person : sortPersons)
			System.out.format("%d\t\t%s\t\t%d\t%d\t%d\n", Thread.currentThread().getId(), person.getPersonId(),
					person.getMonth(), person.getDay(), person.getYear());

		System.out.println();
	}

	/**
	 * @param personArray Array to be sorted
	 * @param low         Starting index
	 * @param high        Ending index
	 */
	public void sort(Person personArray[], int low, int high) {
		if (low < high) {
			/* personArray[partionIndex] is now at right place */
			int partionIndex = partition(personArray, low, high);

			/* Recursively sort elements before partition and after partition */
			sort(personArray, low, partionIndex - 1);
			sort(personArray, partionIndex + 1, high);
		}
	}

	/**
	 * @param personArray Array to be sorted
	 * @param low         Starting index
	 * @param high        Ending index
	 * @return
	 */
	private int partition(Person personArray[], int low, int high) {
		Person pivot = personArray[high];
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			// If current element is smaller than or equal to pivot
			if (personArray[j].customHashCode() <= pivot.customHashCode()) {
				i++;

				// swap personArray[i] and personArray[j]
				Person temp = personArray[i];
				personArray[i] = personArray[j];
				personArray[j] = temp;
			}
		}

		// swap personArray[i+1] and personArray[high] (or pivot)
		Person temp = personArray[i + 1];
		personArray[i + 1] = personArray[high];
		personArray[high] = temp;

		return i + 1;
	}

}
