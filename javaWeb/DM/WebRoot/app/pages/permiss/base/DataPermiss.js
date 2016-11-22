NS.define('Pages.permiss.base.DataPermiss',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.LayoutComponent'],
    tplRequires : [
        {fieldname : 'dataPermissLayoutTpl',path : 'app/pages/permiss/base/tpl/data/dataPermissLayoutTpl.html'},
        {fieldname : 'dataPermissYxBjCheckTpl',path : 'app/pages/permiss/base/tpl/data/dataPermissYxBjCheck.html'},
        {fieldname : 'dataPermissYxQxOptionTpl',path : 'app/pages/permiss/base/tpl/data/dataPermissYxQxOption.html'}
    ],
    /***********************配置项************************/
    layoutData : {mainTitle :'',title : '',description : ''},
    /**
     * 取消操作
     */
    cancel : function(){},
    /**
     *
     * @param {Array(Number)[]} bjIds
     */
    submit : function(){},

    init : function(config){
        this.allDataPermiss = config.allDataPermiss;//所有的数据权限
        this.zzjg = config.zzjg;//组织结构数据，用以绑定选中属性
        this.zzjgHash = this.generateZzjgHash(NS.clone(config.zzjg));
        this.checkedWithAllDataPermiss();//选中所有数据结构

        this.initDataPermissSet();
    },
    /**
     * 初始化数据权限设置
     */
    initDataPermissSet : function(){
         var layout = new Pages.permiss.base.LayoutComponent({
                 layoutTpl : this.dataPermissLayoutTpl,
                 renderNumber : 2,
                 data : this.layoutData
             }),
            yxQxOptionCom  = this.yxQxOptionCom = this.getYxOrQxOptionCom(),
            yxBjCheckCom = this.yxBjCheckCom = this.getYxBjCheckCom();
        this.yxQxOptionCom.refreshTplData(this.zzjgHash[1].children);

        layout.register(yxQxOptionCom,0);
        layout.register(yxBjCheckCom,1);
        layout.bindItemsEvent({
            'cancel.click' : {scope : this,fn : this.cancel},
            'submit.click' : {scope : this,fn : function(){
                this.submit(this.getCheckedBjIds());
            }},
            'cancel1.click' : {scope : this,fn : this.cancel},
            'submit1.click' : {scope : this,fn : function(){
                this.submit(this.getCheckedBjIds());
            }}
        });
        this.setPageComponent(layout.component);
    },
    /**
     * 获取院系或者全校显示组件
     */
    getYxOrQxOptionCom : function(){
        var com = new NS.Component({
            tpl : this.dataPermissYxQxOptionTpl
        });
        com.on('click',function(event,el){
            this.filterNode('TD',el,function(el){
                var itemId = $(el).parent().attr('itemId');
                if(itemId){
                    var node  = this.zzjgHash[itemId];
                    var rowspan = this.computeRowSpan(node);//计算rowspan
                    this.yxbjData = this.translateBjCheckModal(this.group,node,rowspan);
                    this.yxBjCheckCom.refreshTplData(this.yxbjData);//刷新当前选中院系的班级数据

                    $(el.parentNode.parentNode).find('TD').each(function(){
                        $(this).removeClass('permiss_table_menu_select')
                    });
                    $(el).addClass('permiss_table_menu_select');
                }
            });
            this.filterNode('INPUT',el,function(el){
                var itemId = $(el).parent().parent().attr('itemId'),
                    dtype = $(el).attr('dtype'),
                    checked = el.checked;
                var node = dtype == "checkQx"?this.zzjgHash[1]:this.zzjgHash[itemId];
                //选中所有子节点、父节点
                this.checkAllChildren(node,checked);
                var rowspan = this.computeRowSpan(node);//计算rowspan
                this.yxbjData = this.translateBjCheckModal(this.group,node,rowspan);
                this.yxBjCheckCom.refreshTplData(this.yxbjData);//刷新当前选中院系的班级数据
            });
        },this);
        com.on('mouseover',function(event,td){
            var tr = td.parentNode;
            this.filterNode('TR',tr,function(tr){
                var tbody = tr.parentNode;
                $(tbody).find('TR').each(function(){
                    $(this).removeClass('permiss_table_menu_mouseover')
                });
                $(tr).addClass('permiss_table_menu_mouseover');
            });
        },this);
        return com;
    },
    /**
     * 获取院系班级选择组件
     */
    getYxBjCheckCom : function(){
        var me = this,
            com = new NS.Component({
                tpl : this.dataPermissYxBjCheckTpl
            });
        com.on('click',function(event,el){
            this.filterNode('INPUT',el,function(el){
                var ev = $(el),
                    checked = el.checked,
                    dtype = ev.attr('dtype'),
                    itemId = ev.attr('itemid');
                if(dtype == "checkAll"){
                    this.checkAllChildren(this.zzjgHash[itemId],checked);
                    this.checkParent(itemId,checked);
                }else{
                    this.zzjgHash[itemId].checked = checked;
                    this.checkParent(itemId,checked);
                }
                com.refreshTplData(this.yxbjData);
                this.yxQxOptionCom.refreshTplData(this.zzjgHash[1].children);
            });
        },this);
        return com;
    },
    /***********************************数据处理****************************************/
    /**
     * 根据传递的组织结构，生成Hash表
     * @param zzjg
     * @returns {{}}
     */
    generateZzjgHash : function(zzjg){
        var hash = {},
            len= zzjg.length,
            i = 0,
            node,
            pnode;
        //生成hash表
        for(;i<len;i++){
            node = zzjg[i];
            hash[node.id]  = node;
        }
        //生成树
        for( i in hash){
            node = hash[i];
            pnode = hash[node.pid];
            if(pnode)pnode.children.push(node);
        }
        return hash;
    },
    translateBjCheckModal : function(group,node,rowspan){
        var zys = this.getZys(node.children);
        node.children = zys;
        var modal = {
            yxmc : node.text,
            node : node,
            rowspan : rowspan
        };

        return modal;
    },
    getZys : function(children){
        var iterator = children,
            zys = [],
            retZys = [],
            bakToIter = [];
        //处理专业
        while(iterator.length!=0){
            for(var i=0;i<iterator.length;i++){
                var item = iterator[i];
                if(item.cclx == 2){
                    zys.push(item);
                }
                bakToIter = bakToIter.concat(item.children);
            }
            iterator = bakToIter;
            bakToIter = [];
        }
        //处理专业下班级
        for(var i=0;i<zys.length;i++){
            var zy = zys[i];
            zy.chidren = this.getBjs(zy.children);
            if(zy.children.length!=0)retZys.push(zy);
        }
        return retZys;
    },
    getBjs : function(children){
        var iterator = children,
            bjs = [],
            bakToIter = [];
        //处理班级
        while(iterator.length!=0){
            for(var i=0;i<iterator.length;i++){
                var item = iterator[i];
                if(item.cclx == 3){
                    bjs.push(item);
                }
                bakToIter = bakToIter.concat(item.children);
            }
            iterator = bakToIter;
            bakToIter = [];
        }
        return bjs;
    },
    /**
     * 获取要提交的选中的班级ID
     * @returns {Array}
     */
    getCheckedBjIds : function(){
        var hash = this.zzjgHash,
            bjs = [];
        for(var i in hash){
            var item = hash[i];
            if(item.cclx == 1 && item.checked == true){
                bjs.push(item.id);
            }
        }
        return bjs;
    },
    /**
     * 选中用户当前拥有的数据权限--班级已经被选中，目前需要选中的是院系，
     */
    checkedWithAllDataPermiss : function(){
        var hash = this.zzjgHash,
            adp = this.allDataPermiss,
            i = 0,
            pnode,
            bj,
            len = adp.length,
            item;
        for(;i<len;i++){
            var bj = adp[i];
            if(bj){
                item = hash[bj['BJ_ID']];
                if(item){
                    item.checked = true;
                    pnode = hash[item.pid];
                    while(pnode){
                        pnode.checked = true;
                        pnode = hash[pnode.pid];
                    }
                }
            }
        }
    },
    /**
     * 选中当前节点以及所有子节点
     * @param node
     */
    checkAllChildren : function(node,checked){
        var iterator = node.children,
            bakToIterator = [];
        node.checked = checked;//选中
        while(iterator.length != 0){
            for(var i=0;i<iterator.length;i++){
                var item = iterator[i];
                item.checked = checked;//选中
                bakToIterator = bakToIterator.concat(item.children);
            }
            iterator = bakToIterator;
            bakToIterator = [];
        }
    },
    checkParent : function(itemId,checked){
        //选中/不选中当前节点以及其父节点
        var hash = this.zzjgHash,
            node = hash[itemId],
            pnode = node;
        if(node){
            node.checked = checked;//当前节点直接复制checked
            pnode = hash[pnode.pid];
            while(pnode){
                var pchecked = checked;
                if(!checked){
                    for(var i=0;i<pnode.children.length;i++){
                        var cn = pnode.children[i];
                        if(cn.checked){
                            pchecked = true;
                            break;
                        }
                    }
                }
                pnode.checked = pchecked;
                if(hash[pnode.pid]){
                    pnode = hash[pnode.pid];
                }else{
                    pnode = undefined;
                }
            }
        }
    },
    /**
     * 计算左侧显示院系信息的rowspan
     * @param node
     */
    computeRowSpan : function(node){
        var children = node.children,
            i = 0,
            item,
            rowspan = children.length,
            len;
            for(len=children.length;i<len;i++){
                item = children[i];
                if(item.children.length>0)rowspan++;
            }
        return rowspan;
    }
});