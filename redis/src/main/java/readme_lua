--false 和nil 都是false
if type(flase) or type(nil) then
	print("false or nil is false")
else
	print("else is true")
end
--table 类似于json
tab1={key1="val1",key4="val4",key3="val3"}
for k,v in pairs(tab1) do
	print(k .. "_" ..v)
end
--nil可用来删除
tab1.key1=nil
for k,v in pairs(tab1) do
	print(k .. "_" ..v)
end
--index 从1开始
tab2={"val1","val4","val3"}
for k,v in pairs(tab2) do
	print("key:" .. k)
end
--..  字符串相加 +数字相加,#取长度
print("12" + 23)
print("12" .. 23)
print(#("12" .. 23))
print(#tab2)
print(#{})
--函数
function factorial(n)
	if n == 0 then
		return 1
	else
		return n*factorial(n-1)
	end
end
print(factorial(5))
local factoria2 = factorial
print(factoria2(4))
--运算符 +-*/%^  - 负号
--关系运算符 > < ~= == >= <=
--逻辑运算符 and or not
--其他运算符 .. 拼接字串 # 长度
--一个/多个参数、返回
function avgArgs(...)
	local result = 0
	local args = {...}
	for k,v in ipairs(args) do
		result = result + v
	end
	return result/#args, result
end
m,n = avgArgs(1,2,3,4,5,6,7,8,9,10)
print(m,n)
--字符串 大写、小写、替换、查找、反转、格式替换
print(string.upper("asdfghjkl"))
print(string.lower("ASDFGHJKL"))
print(string.gsub("aaaabbbb", "a", "b", 3))
print(string.find("hello lua user", "lua", 1))
print(string.reverse("1abcdefg"))
print(string.format("this values is:%d %s", 3, 4))
print(string.char(97,98,99,100))
print(string.byte('a','b','c','d'))