/**
 * 功能:人事档案模块人员选择公用脚本 版本:1.0
 */

/**
 * 清除所有
 */
function cleanSelector(forName, forId) {
	$("#" + forName).val("");
	$("#" + forId).val("");
}

/**
 * 检查一个收件人ID是否存在
 * 
 * @param forId
 *            接收者ID的隐藏域的input的ID
 * @param userId
 *            接收者userID
 */
function isExistRevId(forId, userId) {
	var forInput = $("#" + forId);
	var forVal = forInput.val();
	if (forVal == "") {
		return false;
	}
	var forValArray = forVal.split(",");
	for (var i = 0; i < forValArray.length; i++) {
		if (forValArray[i] == userId) {
			return true;
		}
	}
	return false;
}

/**
 * 增加一个收件人ID
 */
function addRevId(forId, userId) {
	var forInput = $("#" + forId);
	var forVal = forInput.val();
	if (forVal == "") {
		forInput.val(userId);
	} else {
		forInput.val(forVal + "," + userId);
	}
}

/**
 * 增加一个收件人姓名
 */
function addRevName(forName, userName) {
	var forInput = $("#" + forName);
	var forVal = forInput.val();
	if (forVal == "") {
		forInput.val(userName);
	} else {
		forInput.val(forVal + "," + userName);
	}
}

/**
 * 删除一个收件人ID（如果存在）
 */
function removeRevId(forId, userId) {
	var forInput = $("#" + forId);
	var forVal = forInput.val();
	if (forVal != "") {
		var newForVal = "";
		var forValArray = forVal.split(",");
		for (var i = 0; i < forValArray.length; i++) {
			if (forValArray[i] != userId) {
				newForVal += forValArray[i];
				newForVal += ",";
			}
		}
		if (newForVal.indexOf(",") != -1) {
			newForVal = newForVal.substr(0, newForVal.length - 1);
		}
		forInput.val(newForVal);
	}
}

/**
 * 删除一个收件人姓名（如果存在）
 */
function removeRevName(forName, userName) {
	var forInput = $("#" + forName);
	var forVal = forInput.val();
	if (forVal != "") {
		var newForVal = "";
		var forValArray = forVal.split(",");
		for (var i = 0; i < forValArray.length; i++) {
			if (forValArray[i] != userName) {
				newForVal += forValArray[i];
				newForVal += ",";
			}
		}
		if (newForVal.indexOf(",") != -1) {
			newForVal = newForVal.substr(0, newForVal.length - 1);
		}
		forInput.val(newForVal);
	}
}

/**
 * 打开人员选择树
 */
function selectUser(forName, forId) {
	var param = new HashMap();
	param.set("forName", forName);
	param.set("forId", forId);
	openSelectUser(3, selectCallBack, param, $("#" + forId).val());
}

/**
 * 打开部门选择树
 */
function selectGroup(forName, forId) {
	
	var param = new HashMap();
	param.set("forName", forName);
	param.set("forId", forId);
	openSelectUser(1, selectCallBack, param, $("#" + forId).val());
}

/**
 * 打开角色选择树
 */
function selectRole(forName, forId) {
	var param = new HashMap();
	param.set("forName", forName);
	param.set("forId", forId);
	openSelectUser(2, selectCallBack, param, $("#" + forId).val());
}

/**
 * 人员选择树回调方法
 */
function selectCallBack(data, param) {
	var forName = param.get("forName");
	var forId = param.get("forId");
	data.forEach(function(value, key) {
				// alert("key=" + key + ",name=" + value.Name + ",id=" +
				// value.Id + ",data=" + value.Data + ",type=" + value.Type);
				// 具体参数查看 treeNode.js
				var userId = value.Id;
				var userName = value.Name;

				// 如果ID不存在则添加，不检查name，因为name可能会相同
				if (!isExistRevId(forId, userId)) {
					addRevId(forId, userId);
					addRevName(forName, userName);
				}
			});
}
