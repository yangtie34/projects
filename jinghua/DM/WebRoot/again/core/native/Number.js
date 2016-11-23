/**
 * @class NS.Number
 * @author haw_king
 *   Number扩展类
 */
NS.Number =(function(){
    var me = this,
        isToFixedBroken = (0.9).toFixed() !== '1',
        math = Math;
    return {
        /**
         *
         * 检查是否未传递的数字是在所期望的范围内。如果数目是已知
         ?????? * 范围内，它将被返回，否则返回最小或最大值取决于哪一方的范围是
         ???????* 超过。请注意，此方法返回的限制值，但不改变目前的数字。
         *
         * @param {Number} number 需检查的number
         * @param {Number} 范围内的最小数
         * @param {Number} 范围内的最大数
         * @return {Number} 如果超过范围返回较大值, 否则返回当前number本身
         */
        constrain: function(number, min, max) {
            number = parseFloat(number);

            if (!isNaN(min)) {
                number = math.max(number, min);
            }
            if (!isNaN(max)) {
                number = math.min(number, max);
            }
            return number;
        },
        /**
         * 根据传递的增量值,捕捉传递的停止点之间的数字。
         * when calculating snap points:
         *
         *     r = Ext.Number.snap(56, 2, 55, 65);        // Returns 56 - snap points are zero based
         *
         *     r = Ext.Number.snapInRange(56, 2, 55, 65); // Returns 57 - snap points are based from minValue
         *
         * @param {Number} value The unsnapped value.
         * @param {Number} increment The increment by which the value must move.
         * @param {Number} minValue The minimum value to which the returned value must be constrained. Overrides the increment.
         * @param {Number} maxValue The maximum value to which the returned value must be constrained. Overrides the increment.
         * @return {Number} The value of the nearest snap target.
         */
        snap : function(value, increment, minValue, maxValue) {
            var m;

            if (value === undefined || value < minValue) {
                return minValue || 0;
            }
            if (increment) {
                m = value % increment;
                if (m !== 0) {
                    value -= m;
                    if (m * 2 >= increment) {
                        value += increment;
                    } else if (m * 2 < -increment) {
                        value -= increment;
                    }
                }
            }
            return me.constrain(value, minValue,  maxValue);
        },
        /**
         * Snaps the passed number between stopping points based upon a passed increment value.
         *
         * The difference between this and {@link #snap} is that {@link #snap} does not use the minValue
         * when calculating snap points:
         *
         *     r = Ext.Number.snap(56, 2, 55, 65);        // Returns 56 - snap points are zero based
         *
         *     r = Ext.Number.snapInRange(56, 2, 55, 65); // Returns 57 - snap points are based from minValue
         *
         * @param {Number} value The unsnapped value.
         * @param {Number} increment The increment by which the value must move.
         * @param {Number} [minValue=0] The minimum value to which the returned value must be constrained.
         * @param {Number} [maxValue=Infinity] The maximum value to which the returned value must be constrained.
         * @return {Number} The value of the nearest snap target.
         */
        snapInRange : function(value, increment, minValue, maxValue) {
            var tween;
            // default minValue to zero
            minValue = (minValue || 0);
            // If value is undefined, or less than minValue, use minValue
            if (value === undefined || value < minValue) {
                return minValue;
            }
            // Calculate how many snap points from the minValue the passed value is.
            if (increment && (tween = ((value - minValue) % increment))) {
                value -= tween;
                tween *= 2;
                if (tween >= increment) {
                    value += increment;
                } else if (tween < -increment) {
                    value -= increment;
                }
            }
            // If constraining within a maximum, ensure the maximum is on a snap point
            if (maxValue !== undefined) {
                if (value > (maxValue = me.snapInRange(maxValue, increment, minValue))) {
                    value = maxValue;
                }
            }

            return value;
        },
        /**
         *
         * 设置数字格式使用格式化后的表示法
         *
         * @param {Number} value 需被格式化的值
         * @param {Number} precision 显示小数点后的位数
         */
        toFixed: function(value, precision) {
            if (isToFixedBroken) {
                precision = precision || 0;
                var pow = math.pow(10, precision);
                return (math.round(value * pow) / pow).toFixed(precision);
            }

            return value.toFixed(precision);
        },
        /**
         *
         * 验证一个值是数字，并将其转换成一个号码，如果必要的。如果不是，则返回指定的默认??值。
         *
         * 		NS.Number.from('1.23', 1); // returns 1.23
         * 		NS.Number.from('abc', 1); // returns 1
         * @param {Object} value value对象
         * @param {Number} defaultValue 作为当value不是number类型值是的默认返回值
         * @return {Number} 如果value是number类型，返回本身，否则将返回defaultValue
         */
        from: function(value, defaultValue) {
            if (isFinite(value)) {
                value = parseFloat(value);
            }
            return !isNaN(value) ? value : defaultValue;
        },
        /**
         *
         * 返回一个范围在[from,to]的随机整数
         *
         * @param {Number} from 随机返回的最小值
         * @param {Number} to 随机返回的最大值
         * @return {Number} 返回的随机整数范围在form to内。
         */
        randomInt: function (from, to) {
            return math.floor(math.random() * (to - from + 1) + from);
        }
    };
})();