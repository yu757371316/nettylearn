package emulator.util;

/**
 * 用于产生Mac 算法方式:ANSI X9.9 MAC算法<br/>


 * @author zhangll  2015-02-25
 * 
 */
public class MACGenerator {
	/**
	 * 用于产生Mac <br/>
	 * 算法方式:ANSI X9.9 MAC算法<br/>
	 * 分组异或,DES迭代算法
	 * 
	 * @param input
	 *            待签名数据
	 * @param key
	 *            MAC密钥
	 * @return
	 */
	public String getANSIX99MAC(String input, String key) {
		byte[] mac = new byte[8];
		byte[] temp = new byte[8];
		int z = 0;
		try {
			byte[] bt = input.getBytes();
			int len = bt.length;
			int other = len % 8;
			// 如果密文最后不足8个字节，则以0补足
			if (other != 0) {
				byte[] tt = bt;
				bt = new byte[tt.length + (8 - other)];
				System.arraycopy(tt, 0, bt, 0, len);
			}

			// 初始化mac数组，初始数据为0x00，用来进行循环异或 迭代des
			for (int i = 0; i < 8; i++) {
				mac[i] = 0x00;
			}
			// 循环异或 迭代des
			for (int i = 0; i <= bt.length; i++, z++) {
				if (i != 0 && i % 8 == 0) {
					for (int j = 0; j < 8; j++) {
						mac[j] = (byte) (mac[j] ^ temp[j]);
					}

					mac = DesEncrypt.desEncrypt(mac, ByteUtils.HexString2ByteArr(key));
					
					z = 0;
					temp = new byte[8];
				}

				if (i != bt.length) {
					temp[z] = bt[i];
				}
			}
		
			return ByteUtils.ByteArr2HexString(mac);			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args){
		String ss = "12345678";
		String key = "3808869770582A79";
		MACGenerator mac = new MACGenerator();
		String sign = mac.getANSIX99MAC(ss, key);
		System.out.println("sign=[" + sign + "]");
	}
}
