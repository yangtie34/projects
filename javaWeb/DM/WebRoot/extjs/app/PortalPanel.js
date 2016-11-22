/**
 * @class Ext.app.PortalPanel
 * @extends Ext.panel.Panel
 * A {@link Ext.panel.Panel Panel} class used for providing drag-drop-enabled portal layouts.
 */
Ext.define('Ext.app.PortalPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.portalpanel',
    requires: [
        'Ext.layout.component.Body'
    ],

    cls: 'x-portal',
    bodyCls: 'x-portal-body',
    defaultType: 'portalcolumn',
    componentLayout: 'body',
    autoScroll: true,

    initComponent : function() {
        var me = this;

        // Implement a Container beforeLayout call from the layout to this Container
        this.layout = me.layout || {type : 'column'};
        
        this.callParent();

        this.addEvents({
            validatedrop: true,
            beforedragover: true,
            dragover: true,
            beforedrop: true,
            drop: true
        });
        this.on('drop', this.doLayout, this);
    },
	doForTableLayout : function() {
		var items = this.layout.getLayoutItems(), len = items.length, i = 0, item,parentWidth = this.getWidth();
		
		for (; i < len; i++) {
			item = items[i];
			// 获取父容器的宽度，除len然后乘以item.colspan
			var colspan = item.colspan||1;// 列的合并
			var rowspan = item.rowspan||1;
			item.width = 300*colspan;
			item.height = 300*rowspan;
			item.removeCls(['x-portal-column-first', 'x-portal-column-last']);
		}
		if (items.length > 0) {
			items[0].addCls('x-portal-column-first');
			items[len - 1].addCls('x-portal-column-last');
		}
	},
	doForColumnLayout : function() {
		var items = this.layout.getLayoutItems(), len = items.length, i = 0, item;

		for (; i < len; i++) {
			item = items[i];
			item.columnWidth = item.columnWidth||1 / len;
			item.removeCls(['x-portal-column-first', 'x-portal-column-last']);
		}
		if (items.length > 0) {
			items[0].addCls('x-portal-column-first');
			items[len - 1].addCls('x-portal-column-last');
		}
	},
    // Set columnWidth, and set first and last column classes to allow exact CSS
	// targeting.
    beforeLayout: function() {
    	if(this.layout instanceof Ext.layout.container.Table){
    		this.doForTableLayout();
    	}else{
    		this.doForColumnLayout();
    	}
        return this.callParent(arguments);
    },

    // private
    initEvents : function(){
        this.callParent();
        this.dd = Ext.create('Ext.app.PortalDropZone', this, this.dropConfig);
    },

    // private
    beforeDestroy : function() {
        if (this.dd) {
            this.dd.unreg();
        }
        this.callParent();
    },
    getDockedItems: function (selector, beforeBody) {
        var layout = this.getComponentLayout();
        if (layout.getDockedItems) {
            var dockedItems = layout.getDockedItems('render', beforeBody);
            if (selector && dockedItems.length) {
                dockedItems = Ext.ComponentQuery.query(selector, dockedItems);
            }
            return dockedItems;
        }
        else {
            return [];
        }
    } 
});
