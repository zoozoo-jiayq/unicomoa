package cn.com.qytx.platform.utils.pinyin;

public class FormatPinyinTo9Data {
	/**
	 * 将字母转换为手机键盘按键
	 * @param firstAlpha
	 * @return
	 */
	public static char getOneNumFromAlpha(char firstAlpha) {
		switch (firstAlpha) {
		case 'a':
		case 'b':
		case 'c':
			return '2';
		case 'd':
		case 'e':
		case 'f':
			return '3';
		case 'g':
		case 'h':
		case 'i':
			return '4';
		case 'j':
		case 'k':
		case 'l':
			return '5';
		case 'm':
		case 'n':
		case 'o':
			return '6';
		case 'p':
		case 'q':
		case 'r':
		case 's':
			return '7';
		case 't':
		case 'u':
		case 'v':
			return '8';
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			return '9';
		default:
			return '^';
		}
	}

	/**
	 * 获得中文拼音在9宫格上对应的数字
	 * @param pinyin
	 * @return
	 */
	public static String getFormattedNumber(String pinyin){
		if(pinyin == null || "".equals(pinyin)){
			return "";
		}
		String[] pinyins = pinyin.split(" ");
		StringBuffer[] typeNum = new StringBuffer[pinyins.length + 1];
		for (int i = 0; i < typeNum.length; i++) {
			typeNum[i] = new StringBuffer();
		}
		for (int i = 0; i < pinyins.length; i++) {
			String p = pinyins[i];
			p = p.toLowerCase();
			char[] nums = new char[p.length()];
			for (int j = 0; j < p.length(); j++) {
				nums[j] = getOneNumFromAlpha(p.charAt(j));
			}
			for (int k = 0; k < typeNum.length; k++) {
				if ((typeNum.length - 1 - i) == k
						|| (typeNum.length - 1 - i) < k) {
					typeNum[k].append(nums);
				} else {
					typeNum[k].append(nums[0]);
				}
			}
		}
		StringBuffer formattedNumber = new StringBuffer();
		for (int i = 0; i < typeNum.length; i++) {
			formattedNumber.append(typeNum[i]);
			formattedNumber.append("|");
		}
		return formattedNumber.toString();
	}
}
