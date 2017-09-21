package com.refacto.restyserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@SpringBootApplication
@ComponentScan
public class RestyServerApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(RestyServerApplication.class, args);
		app.getBean(App.class).initApp();
	}
}

@RestController
@RequestMapping("/app")
class App {

	HashSet restaurantApp = new HashSet();

	public void initApp() {
		restaurantApp.add(new F_Restaurant("Chez Patrick",20,true));
		restaurantApp.add(new Restaurant("Oola Petite",40));
		restaurantApp.add(new C_Restaurant("Noddle",10,'E'));
		restaurantApp.add(new C_Restaurant("Fairwood",80,'C'));
	}

	public Integer getReservationCapacity() {
		int sum = 0;

		for (int i = 0; i < restaurantApp.size(); i++) {
			Object restoObject = ((Object[]) restaurantApp.toArray())[i];
			IRestaurant restaurant = (IRestaurant) restoObject;
			sum = sum + restaurant.getCapacity();
		}

		return sum;
	}

	@RequestMapping(value = "/getAllRestaurants",method = RequestMethod.POST)
	public String getAllRestaurants()
	{
		String allNames = "";
		for (int i=0;i<restaurantApp.size();i++) {
			Object restoObject = ((Object[]) restaurantApp.toArray())[i];
			IRestaurant restaurant = (IRestaurant)restoObject;
			allNames = allNames + restaurant.getName();
			allNames = allNames + " ,";
		}
		return allNames;
	}

	@RequestMapping(value = "/getFRestaurants",method = RequestMethod.POST)
	public String getAllFrenchRestaurants()
	{
		String allNames = "";
		for (int i=0;i<restaurantApp.size();i++) {
			Object restoObject = ((Object[]) restaurantApp.toArray())[i];
			if (!(restoObject instanceof F_Restaurant)) {
				continue;
			}
			IRestaurant restaurant = (IRestaurant)restoObject;
			allNames = allNames + restaurant.getName();
			allNames = allNames + " ,";
		}
		return allNames;
	}

	@RequestMapping(value = "/getCRestaurants",method = RequestMethod.POST)
	public String getAllChineseRestaurants()
	{
		String allNames = "";
		for (int i=0;i<restaurantApp.size();i++) {
			Object restoObject = ((Object[]) restaurantApp.toArray())[i];
			if (!(restoObject instanceof C_Restaurant)) {
				continue;
			}
			IRestaurant restaurant = (IRestaurant)restoObject;
			allNames = allNames + restaurant.getName();
			allNames = allNames + " ,";
		}
		return allNames;
	}



	@RequestMapping(value = "/getTotalCapacity",method = RequestMethod.POST)
	public int getTotalCapacityWrapper() {
		return getReservationCapacity();
	}

	@RequestMapping(value = "/bookTable",method = RequestMethod.POST)
	public void bookTable() {

	}

	@RequestMapping(value = "/cancelBooking",method = RequestMethod.POST)
	public void cancelTable() {

	}

	@RequestMapping(value = "/notifyIfSomeoneCancel",method = RequestMethod.POST)
	public void notifyIfSomeoneCancel() {

	}
}

interface IRestaurant {
	int getCapacity();
	String getName();
}


class Restaurant implements IRestaurant {

	private String name;
	private Integer capacity;

	boolean bottle;

	char language;

	Restaurant(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public String getName() {
		return name;
	}
}

class C_Restaurant extends Restaurant {

	C_Restaurant(String name,int capacity, char language) {
		super(name,capacity);
		this.language = language;
	}
}

class F_Restaurant extends Restaurant {

	F_Restaurant(String name, int capacity,boolean bottle) {
		super(name,capacity);
		this.bottle = bottle;
	}
}
