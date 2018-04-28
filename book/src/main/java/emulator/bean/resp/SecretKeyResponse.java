package emulator.bean.resp;

import emulator.bean.Response;

public class SecretKeyResponse extends Response {
	
	private static final long serialVersionUID = 1L;
	
	private String N_MACKEY;
	private String N_MACKEYVAL;
	private String N_PINKEY;
	private String N_PINKEYVAL;
	private String N_PKGKEY;
	private String N_PKGKEYVAL;
	private String N_MAINKEY;
	private String N_MAINKEYVAL;
	private String Reserve1;
	private String Reserve2;
	public String getN_MACKEY() {
		return N_MACKEY;
	}
	public void setN_MACKEY(String n_MACKEY) {
		N_MACKEY = n_MACKEY;
	}
	public String getN_MACKEYVAL() {
		return N_MACKEYVAL;
	}
	public void setN_MACKEYVAL(String n_MACKEYVAL) {
		N_MACKEYVAL = n_MACKEYVAL;
	}
	public String getN_PINKEY() {
		return N_PINKEY;
	}
	public void setN_PINKEY(String n_PINKEY) {
		N_PINKEY = n_PINKEY;
	}
	public String getN_PINKEYVAL() {
		return N_PINKEYVAL;
	}
	public void setN_PINKEYVAL(String n_PINKEYVAL) {
		N_PINKEYVAL = n_PINKEYVAL;
	}
	public String getN_PKGKEY() {
		return N_PKGKEY;
	}
	public void setN_PKGKEY(String n_PKGKEY) {
		N_PKGKEY = n_PKGKEY;
	}
	public String getN_PKGKEYVAL() {
		return N_PKGKEYVAL;
	}
	public void setN_PKGKEYVAL(String n_PKGKEYVAL) {
		N_PKGKEYVAL = n_PKGKEYVAL;
	}
	public String getN_MAINKEY() {
		return N_MAINKEY;
	}
	public void setN_MAINKEY(String n_MAINKEY) {
		N_MAINKEY = n_MAINKEY;
	}
	public String getN_MAINKEYVAL() {
		return N_MAINKEYVAL;
	}
	public void setN_MAINKEYVAL(String n_MAINKEYVAL) {
		N_MAINKEYVAL = n_MAINKEYVAL;
	}
	public String getReserve1() {
		return Reserve1;
	}
	public void setReserve1(String reserve1) {
		Reserve1 = reserve1;
	}
	public String getReserve2() {
		return Reserve2;
	}
	public void setReserve2(String reserve2) {
		Reserve2 = reserve2;
	}
	@Override
	public String toString() {
		return "SecretKeyResponse [N_MACKEY=" + N_MACKEY + ", N_MACKEYVAL="
				+ N_MACKEYVAL + ", N_PINKEY=" + N_PINKEY + ", N_PINKEYVAL="
				+ N_PINKEYVAL + ", N_PKGKEY=" + N_PKGKEY + ", N_PKGKEYVAL="
				+ N_PKGKEYVAL + ", N_MAINKEY=" + N_MAINKEY + ", N_MAINKEYVAL="
				+ N_MAINKEYVAL + ", Reserve1=" + Reserve1 + ", Reserve2="
				+ Reserve2 + "]";
	}
	

	
	
}
