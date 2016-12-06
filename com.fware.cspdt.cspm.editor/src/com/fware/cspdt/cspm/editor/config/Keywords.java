package com.fware.cspdt.cspm.editor.config;

public enum Keywords {

	INCLUDE("include"), TRANSPARENT("transparent"), EXTERNAL("external"), DATATYPE("datatype"), SUBTYPE("subtype"), 
	NAMETYPE("nametype"), MODULE("module"), EXPORTS("exports"), ENDMODULE("endmodule"), INSTANCE("instance"), 
	CHANNEL("channel"), IF("if"), THEN("then"), ELSE("else"), LET("left"), WITHIN("within"), TRUE("true"), 
	FALSE("false"), ASSERT("assert"), PRINT("print");

	String value;

	Keywords(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

}