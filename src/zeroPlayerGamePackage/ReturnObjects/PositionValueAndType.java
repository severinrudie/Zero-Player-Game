package zeroPlayerGamePackage.ReturnObjects;

public class PositionValueAndType {
	
	public PositionValueAndType(PositionObject position, int value, String type) {
		super();
		this.position = position;
		this.value = value;
		this.type = type;  // type == "empty" "ally" or "enemy"
	}  // end constructor
	
	private PositionObject position;
	private int value;
	private String type;
	
	public PositionObject getPosition() {
		return position;
	}  // end getPosition
	
	public int getValue() {
		return value;
	}  // end getValue
	
	public String getType() {
		return type;
	}  // end getType
	
	public void setValue(int value) {
		this.value = value;
	}  // end setValue
	
	
	
}  // end PositionValueAndType