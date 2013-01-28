package br.unip.shadowprot.serial;

import java.io.IOException;

/**
 * Serial port Connector <br/>
 * Conector para a porta serial.
 * 
 * @author chsegala
 * 
 */
public interface SerialConnector {
	public void Connect(String port) throws IOException;

	public void Connect(String port, int baudRate) throws IOException;

	public void send(byte[] data);
}
