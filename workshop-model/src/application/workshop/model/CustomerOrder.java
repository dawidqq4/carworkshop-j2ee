package application.workshop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NamedQueries({
	@NamedQuery(
			name = "CustomerOrder.findAllOrdered", 
			query = "SELECT order FROM CustomerOrder order ORDER BY order.date DESC"),
	@NamedQuery(
			name = "CustomerOrder.findAllNewOrdered", 
			query = "SELECT order FROM CustomerOrder order WHERE order.status = :status ORDER BY order.date DESC"),
	@NamedQuery(
			name = "CustomerOrder.findWorkerVerificationOrdersOrdered", 
			query = "SELECT order FROM CustomerOrder order WHERE order.status = :status "
					+ "AND order.worker.id = :ID_WORKER ORDER BY order.date DESC"),
	@NamedQuery(
			name = "CustomerOrder.findWorkerOrders", 
			query = "SELECT order FROM CustomerOrder order WHERE order.worker.id = :ID_WORKER ORDER BY order.date DESC")
})
@Entity
public class CustomerOrder implements Serializable {
	private static final long serialVersionUID = 3221078292659529543L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, length = 500)
	private String description;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = true)
	private Double price;
	
	@Column(nullable = false, length = 20)
	private String status;
	
    @ManyToOne
    @JoinColumn(name = "ID_WORKER")
	private Worker worker;
	
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_CAR")
	private Car car;

	public CustomerOrder() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", description=" + description + ", date=" + date.toString() + ", price=" + price 
				+ ", status=" + status + ", car=" + car.getName() + "]";
	}
}
