package twg2.jbcm.opcode;

import twg2.jbcm.IoUtility;
import twg2.jbcm.OffsetOpcode;
import twg2.jbcm.modify.OpcodeChangeOffset;

/**
 * Stack (no change)<br/>
 * before: ...<br/>
 * after : ...
 * @author TeamworkGuy2
 * @since 2014-4-19
 */
public class Goto implements OffsetOpcode {
	private static final int OPCODE = 167; // 0xA7

	private static final OpcodeChangeOffset offsetAction = IoUtility.offsetModifier(OPCODE, 1, 2);


	@Override
	public final OpcodeChangeOffset getOffsetModifier() {
		return offsetAction;
	}


	public static final OpcodeChangeOffset getCodeOffsetModifier() {
		return offsetAction;
	}


	@Override
	public final int opcode() {
		return OPCODE;
	}


	public static final int getOpcode() {
		return OPCODE;
	}

}
