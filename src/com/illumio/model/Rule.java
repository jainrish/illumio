package com.illumio.model;

public class Rule implements Comparable<Rule> {

	private Integer portStart;
	private Integer portEnd;
	private Long ipStart;
	private Long ipEnd;

	public Rule(int portStart, int portEnd, long ipStart, long ipEnd) {
		this.portStart = portStart;
		this.portEnd = portEnd;
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
	}

	public Integer getPortStart() {
		return portStart;
	}

	public Integer getPortEnd() {
		return portEnd;
	}

	public Long getIpStart() {
		return ipStart;
	}

	public Long getIpEnd() {
		return ipEnd;
	}

	@Override
	public int compareTo(Rule obj) {
		return this.portStart == obj.portStart ? this.portEnd - obj.portEnd : this.portStart - obj.portStart;
	}

}
