package org.wg.core;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MemoryObject {
	protected Logger logger = LogManager.getLogger(this);

	protected int pid;

	protected int address;

	protected MemoryObject(int pid, int address) {
		this.pid = pid;
		this.address = address;
	}

	public int $(int adress) {
		return Kernel.readMemoryInt(pid, adress);
	}

	protected float $f(int adress) {
		return Kernel.readMemoryFloat(pid, adress);
	}

	protected String $s(int adress) {
		return Kernel.readMemoryStr(pid, adress);

	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
