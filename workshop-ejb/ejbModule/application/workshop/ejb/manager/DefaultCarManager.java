package application.workshop.ejb.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import application.workshop.api.dao.CarDAO;
import application.workshop.api.exception.CarNotFoundException;
import application.workshop.api.manager.CarManager;
import application.workshop.model.Car;

/**
 * Session Bean implementation class DefaultCarManager
 */
@Stateless
public class DefaultCarManager implements CarManager {

	@EJB
	private CarDAO carDAO;

	@Override
	public void removeCar(Car car) {
		carDAO.removeCar(car);
	}

	@Override
	public Car persistCar(Car car) {
		return carDAO.persistCar(car);
	}

	@Override
	public Car mergeCar(Car car) {
		return carDAO.mergeCar(car);
	}

	@Override
	public Car findCar(Long id) throws CarNotFoundException {
		Car car = carDAO.findCar(id);
		if (car == null)
			throw new CarNotFoundException();
		return car;
	}

	@Override
	public List<Car> findAllCars() {
		return carDAO.findAllCars();
	}
}
