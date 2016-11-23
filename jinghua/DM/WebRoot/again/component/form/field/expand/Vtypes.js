/**
 * =========================================== 日期（开始日期--结束日期）校验类型以及方法重写
 * ===========================================
 * @private
 */
Ext.apply(Ext.form.VTypes, {
    /**
     * 针对起始日期和截至日期的值校验
     */
    'daterange' : function(val, field) {
        var date = field.parseDate(val);
        if (!date) {
            return;
        }
        if (field.startDateField
            && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
            .getTime()))) {
            var start = field.startDateField;
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } else if (field.endDateField
            && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
            .getTime()))) {
            var end = field.endDateField;
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    },
    'datetimerange' : function(val,field){
        var date = field.parseDate(val);
        //******************************校验日期
        if (!date) {
            return;
        }
        if (field.startDateField
            && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
            .getTime()))) {
            var start = field.startDateField;
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } else if (field.endDateField
            && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
            .getTime()))) {
            var end = field.endDateField;
            end.setMinValue(date);
            this.dateRangeMin = date;
            end.validate();
        }
        return true;
    }
});