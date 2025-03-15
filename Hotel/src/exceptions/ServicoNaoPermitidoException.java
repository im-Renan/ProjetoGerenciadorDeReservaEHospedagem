package exceptions;

public class ServicoNaoPermitidoException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicoNaoPermitidoException(String message) {
        super(message);
    }
}