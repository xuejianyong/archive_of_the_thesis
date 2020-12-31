package level2;

public class Environment{

	public boolean phenomenonLeft = false;
	public boolean phenomenonRight = false;
	
	public int getResult(int action) {
		int result = 0;
		switch(action){
		case 0://feel left
			if( phenomenonLeft)result = 1;
			break;
		case 1://swap left
			phenomenonLeft = !phenomenonLeft;
			if( phenomenonLeft)result = 1;
			break;
		case 2://feel both
			if(phenomenonLeft != phenomenonRight)result = 1;
			if(phenomenonLeft && phenomenonRight)result = 2;
			break;
		case 3://feel right
			if(phenomenonRight)result = 1;
			break;
		case 4://swap right
			phenomenonRight = !phenomenonRight;
			if(phenomenonRight)result = 1;
			break;
		default:
			break;
		}
		return result;
	}
}
