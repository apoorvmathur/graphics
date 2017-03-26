
public class InvalidTransformException extends Exception {

	private static final long serialVersionUID = 6830545106109359680L;

	String message = "Invalid transformation applied";

	@Override
	public String getMessage() {
		return message;
	}

}
