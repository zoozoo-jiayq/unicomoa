/**
 * 用户信息类
 */

function User() {

}

User.prototype.setUserId = function(userId) {
	this.userId = userId;
};
User.prototype.getUserId = function() {
	return this.userId;
};

User.prototype.setPhone = function(phone) {
	this.phone = phone;
};
User.prototype.getPhone = function() {
	return this.phone;
};

User.prototype.setUserName = function(userName) {
	this.userName = userName;
};
User.prototype.getUserName = function() {
	return this.userName;
};
