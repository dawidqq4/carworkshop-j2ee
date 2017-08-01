package application.workshop.api.dao;

import java.util.List;

import javax.ejb.Local;

import application.workshop.model.Car;

@Local
public interface CarDAO {
	Car findCar(Long id);
	List<Car> findAllCars();
	Car mergeCar(Car car);
	Car persistCar(Car car);
	void removeCar(Car car);
}