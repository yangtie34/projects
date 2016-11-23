/*******************************************************************************
 * 重写panelHeader 属性方法 *
 */
Ext
    .override(
    Ext.panel.Panel,
    {
        getHeaderConfig : function(headerCfg, defaultCfg) {
            return Ext.applyIf(headerCfg, defaultCfg);
        },

        updateHeader : function(force) {
            var me = this, header = me.header, title = me.title, tools = me.tools, config = me.headerConfig;

            if (!me.preventHeader
                && (force || title || (tools && tools.length))) {
                if (header) {
                    header.show();
                } else {
                    var defaultConfig = {
                        title : title,
                        orientation : (me.headerPosition == 'left' || me.headerPosition == 'right') ? 'vertical'
                            : 'horizontal',
                        dock : me.headerPosition || 'top',
                        textCls : me.headerTextCls,
                        iconCls : me.iconCls,
                        icon : me.icon,
                        baseCls : me.baseCls + '-header',
                        tools : tools,
                        ui : me.ui,
                        id : me.id + '_header',
                        indicateDrag : me.draggable,
                        frame : me.frame && me.frameHeader,
                        ignoreParentFrame : me.frame
                            || me.overlapHeader,
                        ignoreBorderManagement : me.frame
                            || me.ignoreHeaderBorderManagement,
                        listeners : me.collapsible
                            && me.titleCollapse ? {
                            click : me.toggleCollapse,
                            scope : me
                        } : null
                    };
                    var econfig = Ext.apply(defaultConfig, config);
                    header = me.header = new Ext.panel.Header(
                        econfig);
                    me.addDocked(header, 0);

                    // Reference the Header's tool array.
                    // Header injects named references.
                    me.tools = header.tools;
                }
                me.initHeaderAria();
            } else if (header) {
                header.hide();
            }
        }
    });