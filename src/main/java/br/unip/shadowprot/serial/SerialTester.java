package br.unip.shadowprot.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SerialTester {

	/**
	 * Serial port utility tester<br/>
	 * Utilitário de testes da porta serial
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		SerialConnector connector = new SerialConnectorImpl();
		connector.Connect("/dev/ttyACM0");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String command;
		
/*		for(Integer i = 0; i < 180; i++){
			byte[] data = new byte[]{i.byteValue()};
			connector.send(data);
			Thread.sleep(15);
		}
		
		for(Integer i = 180; i > 0; i--){
			byte[] data = new byte[]{i.byteValue()};
			connector.send(data);
			Thread.sleep(15);
		}*/
		
		while((command = reader.readLine()) != "exit"){
			Integer degrees = Integer.valueOf(command);
			byte[] data = new byte[]{degrees.byteValue()};
			
			connector.send(data);
		}
		
	}

}
