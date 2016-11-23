/**
 * @class Ext.ux.form.MonthField
 * @private
 */
Ext.define('Ext.ux.form.MonthField', {
    extend: 'Ext.form.field.Picker',
    alias: 'widget.monthfield',
    format: "Y-m",
    altFormats: "m/y|m/Y|m-y|m-Y|my|mY|y/m|Y/m|y-m|Y-m|ym|Ym",
    triggerCls: Ext.baseCSSPrefix + 'form-date-trigger',
    matchFieldWidth: false,
    startDay: new Date(),
    initComponent: function () {
        var me = this;
        me.disabledDatesRE = null;
        me.callParent();
    },
    /**
     *
     */
    initValue: function () {
        var me = this,
            value = me.value;
        if (Ext.isString(value)) {
            me.value = Ext.Date.parse(value, this.format);
        }
        if (me.value)
            me.startDay = me.value;
        me.callParent();
    },
    rawToValue: function (rawValue) {
        return Ext.Date.parse(rawValue, this.format) || rawValue || null;
    },
    valueToRaw: function (value) {
        return this.formatDate(value);
    },
    /**
     *
     * @param date
     * @return
     */
    formatDate: function (date) {
        return Ext.isDate(date) ? Ext.Date.dateFormat(date, this.format) : date;
    },
    /**
     *
     * @return
     */
    createPicker: function () {
        var me = this,
            format = Ext.String.format;
        return Ext.create('Ext.picker.Month', {
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            shadow: false,
            focusOnShow: true,
            listeners: {
                scope: me,
                cancelclick: me.onCancelClick,
                okclick: me.onOkClick,
                yeardblclick: me.onOkClick,
                monthdblclick: me.onOkClick
            }
        });
    },
    onExpand: function () {
        this.picker.setValue(this.startDay);
    },
    /**
     *
     * @param picker
     * @param {Array} value
     */
    onOkClick: function (picker, value) {
        var me = this,
            month = value[0],
            year = value[1],
            date = new Date(year, month, 1);
        me.startDay = date;
        me.setValue(date);
        this.picker.hide();
    },
    /**
     *
     */
    onCancelClick: function () {
        this.picker.hide();
    },
    /**
     *
     */
    getSubmitValue:function(){
    	return this.formatDate(this.getValue());
    }
});