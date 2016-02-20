package unicodefontfixer;

public class CallerClassTracer extends SecurityManager {
	
	public Class[] getCallerStack() {
		return getClassContext();
	}
	
	public Class getCaller(int depth) {
		return getClassContext()[depth];
	}
	
};
