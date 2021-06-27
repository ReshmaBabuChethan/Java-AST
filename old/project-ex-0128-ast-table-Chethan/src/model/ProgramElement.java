//Modified for startPos
package model;

public class ProgramElement {
	private String		pkgName;
	private String		className;
	private String		methodName;
	private boolean	isReturnVoid;
	private Integer	parameterSize;
	private Integer startPosMethod;

	public ProgramElement() {
	}

	public ProgramElement(String pkgName, String className, String methodName, boolean isRetVoid, int parmSize, int startMethod) {
		this.pkgName = pkgName;
		this.className = className;
		this.methodName = methodName;
		this.isReturnVoid = isRetVoid;
		this.parameterSize = parmSize;
		this.startPosMethod = startMethod;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isReturnVoid() {
		return isReturnVoid;
	}

	public void setReturnVoid(boolean isReturnVoid) {
		this.isReturnVoid = isReturnVoid;
	}

	public Integer getParameterSize() {
		return parameterSize;
	}

	public void setParameterSize(Integer parameterSize) {
		this.parameterSize = parameterSize;
	}

	public Integer getStartPosMethod() {
		return startPosMethod;
	}

	public void setStartPosMethod(Integer startPMethod) {
		this.startPosMethod = startPMethod;
	}
	@Override
	public String toString() {
		return pkgName + "." + className + "." + methodName;
	}
}
