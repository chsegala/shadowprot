package br.unip.shadowprot.serial;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Envia as informações de ângulo pela porta serial ao Arduino
 * Sends the angle information to arduino throught serial port
 * 
 * @author chsegala
 *
 */
@Component
public class SerialConnectorImpl implements SerialConnector {
	private SerialPort serialPort;

	@Override
	public void Connect(String port) throws IOException {
		Connect(port, SerialPort.BAUDRATE_57600);
	}

	@Override
	public void Connect(String port, int baudRate) throws IOException {
		try {
			serialPort = new SerialPort(port);
			serialPort.openPort();
			serialPort.setParams(baudRate, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (SerialPortException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void send(byte[] data) {
		try {
			serialPort.writeBytes(data);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

}
