package application.workshop.api.manager;

import java.util.List;

import javax.ejb.Remote;

import application.workshop.api.exception.CarNotFoundException;
import application.workshop.model.Car;

@Remote
public interface CarManager {
	List<Car> findAllCars();
	Car findCar(Long id) throws CarNotFoundException;
	Car mergeCar(Car car);
	Car persistCar(Car car);
	void removeCar(Car car);
}
