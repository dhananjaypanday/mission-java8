package org.java8.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Optional;

class Order{
	private int id;
	private List<String> item;
	Order(int id, List<String> item){
		this.id=id;
		this.item=item;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getItem() {
		return item;
	}
	public void setItem(List<String> item) {
		this.item = item;
	}
}

class Employee{
	private Integer id;
	private String name;
	private Long salary;
	
	public Employee() {
	}

	public Employee(Integer id, String name, Long salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}
	
}

public class IntermediateOperation {

	public static void main(String[] args) {

		// forEach demo
		
		List<Integer> intList = Arrays.asList(1,2,3,4,5,6,2,3);
		//intList.stream().forEach(System.out::println);
		
		//intList.stream().forEach(num-> System.out.println(num*2));
		
		// Collect method 
		
		List<String> strList = Arrays.asList("Apple", "Banana", "Mango", "Apple");
		
		
		List<String> collectedList = strList.stream().collect(Collectors.toList());
		System.out.println(collectedList);
		
		Set<String> collectedSet = strList.stream().collect(Collectors.toSet());
		System.out.println(collectedSet);
		
		Map<String, Integer> collectedMap = strList.stream().collect(Collectors
				.toMap(keyMapper->keyMapper, valueMapper -> valueMapper.length(),(oldValue, newValue)-> newValue ,TreeMap::new));
		System.out.println(collectedMap);
		
		//String joiningString = strList.stream().collect(Collectors.joining(","));
		String joiningString = strList.stream().collect(Collectors.joining(",", "@", "#"));
		System.out.println(joiningString);
		
		// toArray()
		String[] strArray =  strList.stream().toArray(String[]::new);
		System.out.println(strArray);
		Integer[] intArray = intList.stream().toArray(Integer[]::new);
		System.out.println(intArray);
		
		//map()
		List<Integer> sqareList = intList.stream().map(num -> num * num ).collect(Collectors.toList());
		System.out.println(sqareList);
		
		//flatMap()
		List<List<Integer>> listOfIntegerList = Arrays.asList(
					Arrays.asList(1,2,3),
					Arrays.asList(4,5,6),
					Arrays.asList(7,8,9)
				);
		List<Integer> list = listOfIntegerList.stream().flatMap(List::stream).collect(Collectors.toList());
		System.out.println(list);
		
		//flatMap() with order service
		List<Order> orderList = Arrays.asList(
					new Order(1, Arrays.asList("Sumsung", "Nokia", "Iphone")),
					new Order(2, Arrays.asList("Oppo", "Vivo", "MI")),
					new Order(3, Arrays.asList("RealMe","Nothing", "Moto"))
				);
		List<String> itemList = orderList.stream().flatMap(order -> order.getItem().stream()).collect(Collectors.toList());
		System.out.println(itemList);
		
		//distinct()
		List<Integer> distinctLsit = intList.stream().distinct().collect(Collectors.toList());
		System.out.println("Integer List "+ intList);
		System.out.println("Distinct Lsit "+distinctLsit);
		
		//Count()
		Long count = intList.stream().count();
		System.out.println("Count is : "+ count);
		
		//sorted()
		List<Integer> ascendingSortedList =intList.stream().sorted().collect(Collectors.toList()); //Ascending order by default
		System.out.println("Ascending Sorted List : "+ascendingSortedList);
		List<Integer> desceningSortedList = intList.stream().sorted((a,b)->b-a).collect(Collectors.toList()); //Decending order sort
		System.out.println("Descending sort"+desceningSortedList);
		
		List<String> sortedItemsInAsc = itemList.stream().sorted().collect(Collectors.toList());
		System.out.println("sortedItemsInAsc: "+sortedItemsInAsc);
		
		//List<String> descSortedItem = itemList.stream().sorted((str1,str2)-> str2.compareTo(str1)).collect(Collectors.toList());
		//List<String> descSortedItem = itemList.stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
		List<String> descSortedItem = itemList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		System.out.println("descSortedItem"+descSortedItem);
		
		//custom object sorting
		List<Employee> empList = Arrays.asList(
					new Employee(101, "Dhananjay", 150000l),
					new Employee(102, "Satyam", 200000l),
					new Employee(101, "Amit", 150000l),
					new Employee(101, "Raja", 100000l),
					new Employee(101, "Dhananjay", 170000l)
				);
		
		List<Employee> employeeSortedList = empList.stream()
				.sorted(Comparator
						.comparing(Employee::getSalary)
						.thenComparing(Employee::getName)).collect(Collectors.toList());
		
		System.out.println(employeeSortedList);
		
		//filter()
		
		List<Employee> filteredEmployeeList = empList.stream().filter(emp-> emp.getSalary()>150000l).collect(Collectors.toList());
		System.out.println(filteredEmployeeList);
		
		//reduce()
		
		Optional<Integer> result = intList.stream().reduce((first, second)->first+second);
		if(result.isPresent()) {
			System.out.println("Sum of List is :" + result.get());
		}else {
			System.out.println("No result present in List");
		}
		
		Optional<Integer> max = intList.stream().reduce(Integer::max);
		if(max.isPresent()) {
			System.out.println("Max value : "+ max.get());
		}else {
			System.out.println("No max value in list");
		}
		System.out.println("Int list : "+intList);
		result = intList.stream()
				.reduce((first, second)->{
						//System.out.println("first : "+first);
						//System.out.println("second : "+ second);
						return first+second;
						
				});
		
		//allMatch()
		boolean allMatch = intList.stream().allMatch(num -> num%2==0);
		System.out.println("all match : "+allMatch);
		
		//anyMatch()
		boolean anyMatch = intList.stream().anyMatch(num->num==6);
		System.out.println("anyMatch : "+anyMatch);
		
		//noneMatch()
		boolean nonMatch = intList.stream().noneMatch(num -> num%2 == 0);
		System.out.println("nonMatch : "+nonMatch);
		
		//findAny() 
		Optional<Integer> anyEvenNo = intList.stream().filter(num -> num %2 == 0).findAny();
		if(anyEvenNo.isPresent()) {
			System.out.println("any Even No : "+ anyEvenNo.get());
			
		}else {
			System.out.println("No result found...");
		}
		//findFirst()
		Optional<Integer> findFirstNo = intList.stream().filter(num -> num % 2 == 0).findFirst();
		findFirstNo.ifPresentOrElse(num -> System.out.println(num), ()-> System.out.println("No result find..."));
	}

}
