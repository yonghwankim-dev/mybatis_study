package member.model.vo;

public enum LibRegiNumType2 {
	없음(0),
	충남대학교(1),
	목원대학교(2),
	우송대학교(3),
	배재대학교(4),
	한남대학교(5);

	private int value;
	LibRegiNumType2(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static LibRegiNumType2 lookup(int num) {
		for(LibRegiNumType2 element : LibRegiNumType2.values())
		{
			if(element.getValue()==num)
			{
				return element;
			}
		}
		return null;
	}	
}
