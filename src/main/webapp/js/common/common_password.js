/**
 * 判断输入密码的类型    
 * @param iN
 * @returns {Number}
 */
function charMode(iN) {
	if (iN >= 48 && iN <= 57) // 数字
		return 1;
	if (iN >= 65 && iN <= 90) // 大写
		return 2;
	if (iN >= 97 && iN <= 122) // 小写
		return 4;
	else
		return 8;
}

/**
 * bitTotal函数,计算密码模式
 * @returns {Number}
 */
function bitTotal(num) {
	var modes = 0;
	for (i = 0; i < 4; i++) {
		if (num & 1)
			modes++;
		num >>>= 1;
	}
	return modes;
}

/**
 * 返回强度级别(0,1,2)
 */
function getPassLevel(pass) {
	if (pass.length < 6)
		return 0; // 密码太短，不检测级别
	var modes = 0;
	for (i = 0; i < pass.length; i++) {
		modes |= charMode(pass.charCodeAt(i));// 密码模式
	}
	return bitTotal(modes);
}

/**
 * 默认显示颜色方法
 * <input type=password size=20 onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value)>  
 */
function pwStrength(pass) {
	var level = 0;
	var level = getPassLevel(pass);
	if(!level || level > 2){
		level = 0;
	}
	$("#pass_level_0,#pass_level_1,#pass_level_2").removeClass("hover");
	$("#pass_level_"+level).addClass("hover");
	return;
}