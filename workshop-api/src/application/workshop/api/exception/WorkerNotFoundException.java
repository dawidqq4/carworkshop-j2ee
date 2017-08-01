package application.workshop.api.exception;

public class WorkerNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public WorkerNotFoundException() {
	}      

	public WorkerNotFoundException(String arg0) {
		super(arg0);
	}

	public WorkerNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public WorkerNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public WorkerNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
