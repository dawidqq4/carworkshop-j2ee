package application.workshop.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import application.workshop.api.dao.CarDAO;
import application.workshop.model.Car;

/**
 * Session Bean implementation class DefaultCarDAO
 */
@Stateless
public class DefaultCarDAO implements CarDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public Car findCar(Long id) {
		return entityManager.find(Car.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Car> findAllCars() {
		return entityManager.createNamedQuery("Car.findAllOrdered").getResultList();
	}

	@Override
	public Car mergeCar(Car car) {
		return entityManager.merge(car);
	}

	@Override
	public Car persistCar(Car car) {
		entityManager.persist(car);
		entityManager.flush();
		return car;
	}

	@Override
	public void removeCar(Car car) {
		entityManager.remove(entityManager.contains(car) ? car : entityManager.merge(car));
	}
}
