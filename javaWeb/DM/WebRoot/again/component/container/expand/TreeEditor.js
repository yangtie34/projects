/**
 * Internal utility class that provides default configuration for cell editing.
 * @private
 */
Ext.define('Ext.tree.TreeEditor', {
    extend:'Ext.Editor',
    constructor:function (config) {
        config = Ext.apply({}, config);

        if (config.field) {
            config.field.monitorTab = false;
        }
        this.callParent([config]);
    },

    /**
     * @private
     * Hide the grid cell text when editor is shown.
     *
     * There are 2 reasons this needs to happen:
     *
     * 1. checkbox editor does not take up enough space to hide the underlying text.
     *
     * 2. When columnLines are turned off in browsers that don't support text-overflow:
     *    ellipsis (firefox 6 and below and IE quirks), the text extends to the last pixel
     *    in the cell, however the right border of the cell editor is always positioned 1px
     *    offset from the edge of the cell (to give it the appearance of being "inside" the
     *    cell.  This results in 1px of the underlying cell text being visible to the right
     *    of the cell editor if the text is not hidden.
     *
     * We can't just hide the entire cell, because then treecolumn's icons would be hidden
     * as well.  We also can't just set "color: transparent" to hide the text because it is
     * not supported by IE8 and below.  The only remaining solution is to remove the text
     * from the text node and then add it back when the editor is hidden.
     */
    onShow:function () {
        var me = this,
            innerNode = me.boundEl.first(),
            lastChild,
            textNode;

        if (innerNode) {
            lastChild = innerNode.dom.lastChild;
            if (lastChild && lastChild.nodeType === 3) {
                // if the cell has a text node, save a reference to it
                textNode = me.nodeTextNode = innerNode.dom.lastChild;
                // save the cell text so we can add it back when we're done editing
                me.nodeTextValue = textNode.nodeValue;
                // The text node has to have at least one character in it, or the cell borders
                // in IE quirks mode will not show correctly, so let's use a non-breaking space.
                textNode.nodeValue = '\u00a0';
            }
        }
        me.callParent(arguments);
    },
    getTextNode : function(innerNode){
        var me = this,
            textNode,
            lastChild;
        if (innerNode) {
            lastChild = innerNode.dom.lastChild;
            if (lastChild && lastChild.nodeType === 3) {
                // if the cell has a text node, save a reference to it
                textNode = me.nodeTextNode = innerNode.dom.lastChild;
                // save the cell text so we can add it back when we're done editing
                me.nodeTextValue = textNode.nodeValue;
                // The text node has to have at least one character in it, or the cell borders
                // in IE quirks mode will not show correctly, so let's use a non-breaking space.
                textNode.nodeValue = '\u00a0';
            }
        }
        return textNode;
    },
    /**
     * @private
     * Show the node  text when the editor is hidden by adding the text back to the text node
     */
    onHide:function () {
        var me = this,
            innerNode = me.boundEl,
            textNode = this.getTextNode(innerNode);

        if (innerNode && me.nodeTextNode) {
            textNode.nodeValue = me.nodeTextValue;
//            delete me.nodeTextNode;
//            delete me.nodeTextValue;
        }
        me.callParent(arguments);
    },

    /**
     * @private
     * Fix checkbox blur when it is clicked.
     */
    afterRender:function () {
        var me = this,
            field = me.field;

        me.callParent(arguments);
        if (field.isXType('checkboxfield')) {
            field.mon(field.inputEl, {
                mousedown:me.onCheckBoxMouseDown,
                click:me.onCheckBoxClick,
                scope:me
            });
        }
    },

    /**
     * @private
     * Because when checkbox is clicked it loses focus  completeEdit is bypassed.
     */
    onCheckBoxMouseDown:function () {
        this.completeEdit = Ext.emptyFn;
    },
    /**
     * Ends the editing process, persists the changed value to the underlying field, and hides the editor.
     * @param {Boolean} [remainVisible=false] Override the default behavior and keep the editor visible after edit
     */
    completeEdit:function (remainVisible) {
        var me = this,
            field = me.field,
            value;

        if (!me.editing) {
            return;
        }

        // Assert combo values first
        if (field.assertValue) {
            field.assertValue();
        }

        value = me.getValue();
        if (!field.isValid()) {
            if (me.revertInvalid !== false) {
                me.cancelEdit(remainVisible);
            }
            return;
        }

        if (String(value) === String(me.startValue) && me.ignoreNoChange) {
            me.hideEdit(remainVisible);
            return;
        }

        if (me.fireEvent('beforecomplete', me, value, me.startValue) !== false) {
            // Grab the value again, may have changed in beforecomplete
            value = me.getValue();
            if (me.updateEl && me.boundEl) {
                var textNode = me.getTextNode(me.boundEl);
                textNode.nodeValue = value;
            }
            me.hideEdit(remainVisible);
            me.fireEvent('complete', me, value, me.startValue);
        }
    },

    // private
    onShow:function () {
        var me = this;

        me.callParent(arguments);
        if (me.hideEl !== false) {
            me.boundEl.hide();
        }
        me.fireEvent("startedit", me.boundEl, me.startValue);
    },

    /**
     * @private
     * Restore checkbox focus and completeEdit method.
     */
    onCheckBoxClick:function () {
        delete this.completeEdit;
        this.field.focus(false, 10);
    },
    /**
     * @cfg {Number[]} offsets
     * The offsets to use when aligning (see {@link Ext.Element#alignTo} for more details.
     */
    offsets: [15, 0],
    alignment:"tl-tl",
    hideEl:false,
    cls:Ext.baseCSSPrefix + "small-editor " + Ext.baseCSSPrefix + "grid-editor",
    shim:false,
    shadow:false
});

