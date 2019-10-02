/**
 * @description 自定义序列化表单
 * @returns {{unique_id: number}}
 */
$.fn.serializeObject = function () {
    var o = {"unique_id": new Date().getTime()};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            var temp = o[this.name];
            if (this.value != '') {
                temp += (',' + this.value);
            }
            o[this.name] = temp;
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * @description 下载文件
 * @param filePath 文件路径
 * @param fileName 下载保存的文件名称
 */
function downloadFile(filePath, fileName) {
    if (typeof (filePath) == "undefined"
        || filePath == ''
        || filePath == null) {
        layer.msg('下载异常');
        return false;
    }
    // 检查文件是否存在
    $.ajax({
        url: URL + "checkFileIsExists",
        type: "POST",
        data: {
            filePath: filePath
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
                location.href = URL + "downloadFile?filePath=" + filePath + "&fileName=" + fileName;
            } else {
                layer.msg(data.msg);
            }
        }
    });
};

/**
 * 从对象数组中删除属性为objPropery，值为objValue元素的对象
 * @param Array arr 数组对象
 * @param String objPropery 对象的属性
 * @param String objValue 对象的值
 * @return Array 过滤后数组
 */
function remove(arr, objPropery, objValue) {
    return $.grep(arr, function (cur, i) {
        return cur[objPropery] != objValue;
    });
}

/**
 * 从对象数组中获取属性为objPropery，值为objValue元素的对象
 * @param Array arr 数组对象
 * @param String objPropery 对象的属性
 * @param String objValue 对象的值
 * @return Array 过滤后的数组
 */
function get(arr, objPropery, objValue) {
    return $.grep(arr, function (cur, i) {
        return cur[objPropery] == objValue;
    });
}

/**
 * POST提交
 * @param url  请求地址
 * @param params 参数集合（例：{'param1': xxx,'param2': xxx}）
 */
function postCommit(url, params) {
    var tempForm = document.createElement("form");
    tempForm.action = url;
    tempForm.method = "post";
    tempForm.style.display = "none";
    for (var x in params) {
        var opt = document.createElement("input");
        opt.name = x;
        opt.value = params[x];
        tempForm.appendChild(opt);
    }
    document.body.appendChild(tempForm);
    tempForm.submit();
    return tempForm;
};

/**
 *功能:js去空格
 */
function trimBlank(str) {
    if (str != "") {
        str = str.replace(/\s+/g, "");
    }
    return str;
}

/**
 *   功能:计算指定日期加上多少天、加多少月、加多少年的日期.
 *   参数:interval,字符串表达式，表示要添加的时间间隔.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   参数:date,时间对象.
 *   返回:新的时间对象.
 *   var now = new Date();
 *   var newDate = DateAdd( "d", 5, now);
 */
function DateAdd(interval, number, date) {
    switch (interval) {
        case "y": {
            date.setFullYear(date.getFullYear() + number);
            return date;
            break;
        }
        case "q": {
            date.setMonth(date.getMonth() + number * 3);
            return date;
            break;
        }
        case "m": {
            date.setMonth(date.getMonth() + number);
            return date;
            break;
        }
        case "w": {
            date.setDate(date.getDate() + number * 7);
            return date;
            break;
        }
        case "d": {
            date.setDate(date.getDate() + number);
            return date;
            break;
        }
        case "h": {
            date.setHours(date.getHours() + number);
            return date;
            break;
        }
        case "m": {
            date.setMinutes(date.getMinutes() + number);
            return date;
            break;
        }
        case "s": {
            date.setSeconds(date.getSeconds() + number);
            return date;
            break;
        }
        default: {
            date.setDate(d.getDate() + number);
            return date;
            break;
        }
    }
}

/*var now = new Date();
// 加五天.
var newDate = DateAdd("d", 5, now);
alert(newDate.toLocaleDateString())
// 加两个月.
newDate = DateAdd("m", 2, now);
alert(newDate.toLocaleDateString())
// 加一年
newDate = DateAdd("y", 1, now);
alert(newDate.toLocaleDateString())*/

/**
 *功能:字符串转日期
 */
function stringToDate(dateStr, separator) {
    if (!separator) separator = "-";
    var dateArr = dateStr.split(separator);
    var year = parseInt(dateArr[0]);
    var month;
    //处理月份为04这样的情况
    if (dateArr[1].indexOf("0") == 0) {
        month = parseInt(dateArr[1].substring(1));
    } else {
        month = parseInt(dateArr[1]);
    }
    var day = parseInt(dateArr[2]);
    var date = new Date(year, month - 1, day);
    return date;
}

/**
 *功能:日期转字符串
 */
function dateToString(date) {
    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString();
    var day = (date.getDate()).toString();
    if (month.length == 1) {
        month = "0" + month;
    }
    if (day.length == 1) {
        day = "0" + day;
    }
    var dateTime = year + "-" + month + "-" + day;
    return dateTime;
}

/**
 *功能:两个时间相差天数
 */
function getDifferDays(dataStr1, dataStr2) {
    var data1 = stringToDate(dataStr1);
    var data2 = stringToDate(dataStr2);
    // 相差日期包括当天，所以加1天再进行计算
    data1 = DateAdd("d", 1, data1);
    var days = data1.getTime() - data2.getTime();
    return Math.floor(days / (24 * 3600 * 1000));
};

/**
 * 判断两个时间是否有交集
 */
function isDateIntersection(start1, end1, start2, end2) {
    var startdate1 = new Date(start1.replace("-", "/").replace("-", "/"));
    var enddate1 = new Date(end1.replace("-", "/").replace("-", "/"));

    var startdate2 = new Date(start2.replace("-", "/").replace("-", "/"));
    var enddate2 = new Date(end2.replace("-", "/").replace("-", "/"));

    if (startdate1 >= startdate2 && startdate1 <= enddate2) {//startdate1介于另一个区间之间
        return true;
    }

    if (enddate1 >= startdate2 && enddate1 <= enddate2) {//enddate1介于另一个区间之间
        return true;
    }

    if (startdate1 <= startdate1 && enddate1 >= enddate2) {//startdate1-enddate1的区间大于另一个区间，且另一个区间在startdate1-enddate1之间
        return true;
    }

    return false;
}

/**
 * 判断是否是闰年
 */
function isLeapYear(year) {
    var cond1 = year % 4 == 0;  //条件1：年份必须要能被4整除
    var cond2 = year % 100 != 0;  //条件2：年份不能是整百数
    var cond3 = year % 400 == 0;  //条件3：年份是400的倍数
    //当条件1和条件2同时成立时，就肯定是闰年，所以条件1和条件2之间为“与”的关系。
    //如果条件1和条件2不能同时成立，但如果条件3能成立，则仍然是闰年。所以条件3与前2项为“或”的关系。
    //所以得出判断闰年的表达式：
    var cond = cond1 && cond2 || cond3;
    if (cond) { // 是润年
        return true;
    } else { // 不是润年
        return false;
    }
}

/**
 * 格式化日期
 * @param d  日期
 * @param format 格式化类型
 * @returns {*}
 */
formatDate = function (d, format) {
    var date = new Date(d || new Date())
        , ymd = [
        digit(date.getFullYear(), 4)
        , digit(date.getMonth() + 1)
        , digit(date.getDate())
    ]
        , hms = [
        digit(date.getHours())
        , digit(date.getMinutes())
        , digit(date.getSeconds())
    ];

    format = format || 'yyyy-MM-dd HH:mm:ss';

    return format.replace(/yyyy/g, ymd[0])
        .replace(/MM/g, ymd[1])
        .replace(/dd/g, ymd[2])
        .replace(/HH/g, hms[0])
        .replace(/mm/g, hms[1])
        .replace(/ss/g, hms[2]);
};
//数字前置补零
digit = function (num, length, end) {
    var str = '';
    num = String(num);
    length = length || 2;
    for (var i = num.length; i < length; i++) {
        str += '0';
    }
    return num < Math.pow(10, length) ? str + (num | 0) : num;
};

/*
* 金额格式化
* 参数说明：
* number：要格式化的数字
* decimals：保留几位小数
* dec_point：小数点符号
* thousands_sep：千分位符号
* */
function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 2 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.ceil(n * k) / k;
        };

    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    var re = /(-?\d+)(\d{3})/;
    while (re.test(s[0])) {
        s[0] = s[0].replace(re, "$1" + sep + "$2");
    }

    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

/*
    金额取消格式化
 */
function rmoney(val) {
    return parseFloat(val.replace(/[^\d\.-]/g, ""));
}

/*
* 只能是数字（允许两位小数点）
* */
function onlyNumber(obj) {
    //得到第一个字符是否为负号    
    var t = obj.value.charAt(0);
    //先把非数字的都替换掉，除了数字和.和-号    
    obj.value = obj.value.replace(/[^\d\.\-]/g, '');
    //前两位不能是0加数字      
    obj.value = obj.value.replace(/^0\d[0-9]*/g, '');
    //必须保证第一个为数字而不是.       
    obj.value = obj.value.replace(/^\./g, '');
    //保证只有出现一个.而没有多个.       
    obj.value = obj.value.replace(/\.{2,}/g, '.');
    //保证.只出现一次，而不能出现两次以上       
    obj.value = obj.value.replace('.', '$#$').replace(/\./g, '').replace('$#$', '.');
    //如果第一位是负号，则允许添加    
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
    if (t == '-') {
        return;
    }
}

/*
* 只能是数字
* */
function onlyInteger(obj) {
    obj.value = obj.value.replace(/[^\d]/g, '');
}