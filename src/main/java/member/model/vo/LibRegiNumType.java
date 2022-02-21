package member.model.vo;

public enum LibRegiNumType {
	�泲���б�(1),
	������б�(2),
	��۴��б�(3),
	������б�(4),
	�ѳ����б�(5);	

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
