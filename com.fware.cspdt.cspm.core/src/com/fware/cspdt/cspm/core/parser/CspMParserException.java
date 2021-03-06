package com.fware.cspdt.cspm.core.parser;

/**
 * Exceção disparada quando a análise sintática falha.
 *
 * @author Joabe Jesus
 * @author Victor Vilmarques
 * @author ALVARO, EVERALDA, FELIPE, JONATHAN, JUVENAL
 */
public class CspMParserException extends Exception {
	
	private static final long serialVersionUID = 7526472295622776147L;  // unique id
	private int linha;
	private int pos;
	private String token;

	/**
	 * Construtor Padrão
	 * 
	 * @param message A mensagem de erro
	 * @param Token O token que gerou o erro
	 * @param linha A linha onde se encontra o token
	 * @param pos A coluna onde se encontra o token
	 */
	public CspMParserException(String message, String token, int linha, int pos) {
		super(message);
		this.linha = linha;
		this.pos = pos;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public int getLinha() {
		return linha;
	}

	public int getPos() {
		return pos;
	}
	
}
