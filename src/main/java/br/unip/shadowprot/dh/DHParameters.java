package br.unip.shadowprot.dh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DHParameters {
	private double offset;
	private double length;
	private double twist;
	private double theta;
	
	public DHParameters(double offset, double length, double twist){
		this.offset = offset;
		this.length = length;
		this.twist = twist;
	}
}
