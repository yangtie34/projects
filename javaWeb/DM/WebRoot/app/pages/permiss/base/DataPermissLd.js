NS.define('Pages.permiss.base.DataPermissLd',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.LayoutComponent'],
    tplRequires : [
        {fieldname : 'dataPermissLayoutTpl',path : 'app/pages/permiss/base/tpl/data/dataPermissLayoutLdTpl.html'}
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
        var cloneZzjg = this.cloneZzjg = NS.clone(config.zzjg);
        this.zzjgHash = this.generateZzjgHash(cloneZzjg);
        this.checkedUserDataPermiss();//设置当前用户拥有的数据权限

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
             ownerCom = this.ownerCom = this.getOwnerZzjgComponent(this.allDataPermiss),
             zzjgCheckTree  = this.zzjgCheckTree = this.getJxzzjgTree();

        layout.register(ownerCom,0);
        layout.register(zzjgCheckTree,1);
        layout.bindItemsEvent({
            'cancel.click' : {scope : this,fn : this.cancel},
            'submit.click' : {scope : this,fn : function(){
                this.submit(this.getPermissJxzzjgIds());
            }},
            'cancel1.click' : {scope : this,fn : this.cancel},
            'submit1.click' : {scope : this,fn : function(){
                this.submit(this.getPermissJxzzjgIds());
            }}
        });
        this.setPageComponent(layout.component);
    },
    getOwnerZzjgComponent : function(zzjgs){
        var me = this,
            component = new NS.Component({
                tpl : new NS.Template('<h2><span class="wbh-common-title ">用户拥有的组织结构权限</span> </h2>'+
                    '  <tpl for=".">' +
                    '<span>{text}</span><a href="javascript:void(0);" class="permiss-outname-delete-bg" zzjgId="{id}"></a>'+
                    ' </tpl>'),
                data : zzjgs
            });
        component.on('click',function(event,element){
            this.filterA(element,function(el){
                var zzjgId = $(el).attr('zzjgId');
                //移除我拥有的角色
                this.zzjgHash[zzjgId].checked = false;
                this.checkTreeNode(zzjgId,false);
                this.refreshOwnerCom();//刷新我拥有的权限组件
            });
        },this);
        return component;
    },
    /**
     * 获取院系或者全校显示组件
     */
    getJxzzjgTree : function(){
        var treeData = this.cloneZzjg,
            hash = this.zzjgHash;
        var tree = this.tree = new NS.container.Tree({
            treeData:treeData,
            width : '30%',
            region : 'west',
            height : 400,
            maxHeight : 700,
            checkable : true,// 是否是多选树
            rootVisible : false,
            frameHeader : false,
            header : false,
            border : true,
            margin : '0 0 0 0',
            initMyEvent: function (obj) {
                var me = this;
                var changeFun = function (node, checked) {
                    node.expand();
                    node.set('checked', checked);
                    node.eachChild(function (child) {
                        child.set('checked', checked);
                        changeFun(child, checked);
                    });
                }
                var checkedParent = function (node, checked) {
                    var parentNode = node.parentNode;
                    if (checked && parentNode) {
                        parentNode.set('checked', checked);
                        arguments.callee(parentNode, checked);
                    } else if (parentNode) {
                        var temp = 0;
                        parentNode.eachChild(function (child) {
                            if (child.get('checked')) {
                                temp++;
                            }
                        });
                        if (temp == 0) {
                            parentNode.set('checked', false);
                        }
                    }
                }
                if(obj.treeEditor){
                    this.component.addListener('itemdblclick', function (com, record, item, index, e) {
                        var div = Ext.fly(item).down('div');
                        me.editor.startEdit(div,record.getData().text);
                        me.editor.editorNode = record;
                    }, this.component);
                }
            }
        });
        tree.component.getStore().getRootNode().expandChildren(false);
        tree.component.on('checkchange',function(node, checked, opts){
            this.checkNotParent(node.data.id,checked);
            this.refreshOwnerCom();//刷新我拥有的权限组件
        },this);
        return tree;
    },
    /**
     * 选中树的节点
     * @param id
     * @param checked
     */
    checkTreeNode : function(id,checked){
        this.tree.component.getStore().getNodeById(id).set('checked',checked);
    },
    /**
     * 刷新拥有权限组件
     */
    refreshOwnerCom : function(){
        var checks = this.tree.getChecked();
        this.ownerCom.refreshTplData(checks);
    },
    /***********************************数据处理****************************************/
    /**
     * 数据提交
     * @param zzjg
     * @returns {{}}
     */
    getPermissJxzzjgIds : function(){
        var rows = this.tree.getChecked();
        return NS.Array.pluck(rows,'id');
    },
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
    /**
     * 获取要提交的选中的班级ID
     * @returns {Array}
     */
    getCheckedZzjgIds : function(){
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
     * 选中用户当前拥有的教学组织结构节点
     */
    checkedUserDataPermiss : function(){
        var hash = this.zzjgHash,
            adp = this.allDataPermiss,
            i = 0,
            node,
            bj,
            len = adp.length,
            item;
        for(;i<len;i++){
            var node = adp[i];
            if(node){
                item = hash[node.id];
                if(item){
                    item.checked = true;
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
                this.checkTreeNode(item.id,checked);
                bakToIterator = bakToIterator.concat(item.children);
            }
            iterator = bakToIterator;
            bakToIterator = [];
        }
    },
    checkNotParent : function(id,checked){
       var hash = this.zzjgHash,item;
       var node = hash[id],pnode;
       if(node && checked == true){
           pnode = hash[node.pid];
          while(pnode){
              pnode.checked = false;
              this.checkTreeNode(pnode.id,false);//所有父节点均为未选中状态
              pnode = hash[pnode.pid];
          }
          this.checkAllChildren(node,false);//反选所有子节点
       }
    }
});