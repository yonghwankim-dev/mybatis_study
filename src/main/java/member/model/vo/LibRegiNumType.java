package member.model.vo;

public enum LibRegiNumType {
	충남대학교(1),
	목원대학교(2),
	우송대학교(3),
	배재대학교(4),
	한남대학교(5);	

	private int value;
	LibRegiNumType(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	
	public static LibRegiNumType lookup(int num) {
		for(LibRegiNumType element : LibRegiNumType.values())
		{
			if(element.getValue()==num)
			{
				return element;
			}
		}
		return null;
	}
	
}
