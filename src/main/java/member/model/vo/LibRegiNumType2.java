package member.model.vo;

public enum LibRegiNumType2 {
	����(0),
	�泲���б�(1),
	������б�(2),
	��۴��б�(3),
	������б�(4),
	�ѳ����б�(5);

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
