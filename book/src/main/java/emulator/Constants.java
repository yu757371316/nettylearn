package emulator;

public final class Constants {


	/**
	 * 发起方机构编号，临时编号，生产环境需申请
	 */
	public static final String ExBranchNo = "360001";



	/*******************************************************************************
	 * ======================= 			交易代码清单	 		=======================
	 *******************************************************************************/

	/**
	 * 电子账户
	 */
	public static final String TC_DZZH = "31001";

	/**
	 * 客户充值
	 */
	public static final String TC_KHCZ = "31021";
	/**
	 * 协议管理
	 */
	public static final String TC_XYGL = "31004";
	/**
	 * 项目管理
	 */
	public static final String TC_XMGL = "31005";

	/**
	 * 投资资金托管
	 */
	public static final String TC_TZZJTG = "31006";

	/**
	 * 融资资金放款
	 */
	public static final String TC_RZZJFK = "31007";

	/**
	 * 融资人到期还款
	 */
	public static final String TC_RZRDQHK = "31008";

	/**
	 * 投资到期收款
	 */
	public static final String TC_TZDQSK = "31009";

	/**
	 * 客户提现
	 */
	public static final String TC_KHTX = "31022";

	/**
	 * 交易结果查询
	 */
	public static final String TC_JYJGCX = "31011";
	/**
	 * 批量文件申请
	 */
	public static final String TC_PLWJSQ = "31012";
	/**
	 * 对账文件提取
	 */
	public static final String TC_DZWJTQ = "31013";
	/**
	 * 对账文件申请
	 */
	public static final String TC_DZWJSQ = "31014";
	/**
	 * 账户余额查询
	 */
	public static final String TC_ZHYECX = "31016";
	/**
	 * 项目收益划转
	 */
	public static final String TC_XMSYHZ = "31017";
	/**
	 * 获取账号验证码
	 */
	public static final String TC_HQZHYZM = "31019";
	/**
	 * 银行结算账户邦定管理
	 */
	public static final String TC_YHZHBD = "31020";

	/**
	 * 融资资金放款退回
	 */
	public static final String TC_RZZJFKTH = "41001";
	/**
	 * 批量文件处理结果通知
	 */
	public static final String TC_PLWJCLJGTZ = "41002";
	/**
	 * 对账文件生成完成通知
	 */
	public static final String TC_DZWJSCWCTZ = "41003";


	/*******************************************************************************
	 * ==================== 			响应结果代码清单	 		====================
	 *******************************************************************************/
	/**
	 * 交易成功
	 */
	public static final String RC_JYCG = "0000";

	/**
	 * 交易失败
	 */
	public static final String RC_JYSB = "0001";

	/**
	 * 部分成功
	 */
	public static final String RC_BFCG = "0002";



	/**
	 * 默认IP
	 */
	public static final String DEFAULT_IP = "192.168.0.64";
	/**
	 * 默认端口
	 */
	public static final int DEFAULT_PORT = 8089;

	/**
	 * 项目根路径
	 */
	public static final String ROOT_DIR = System.getProperty("user.dir");


}
