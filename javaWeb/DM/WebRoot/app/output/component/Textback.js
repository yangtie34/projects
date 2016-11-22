/*******************************************************************************
 * 鍩烘湰缁勪欢锛氭枃鏈粍浠躲� 鏍规嵁浼犻�鐨勬枃鏈粍浠堕厤缃暟鎹垱寤虹粍浠躲�
 *
 * @argument cData = { componentType : 'popText', showMaxItems : 2, htmlstring :
 *           "涓婅堪鍙樻洿鏁版嵁锛屽凡鍚屾鏁版嵁鑺傜偣 {0} 澶勶紝琚姩鍚屾鏁版嵁鑺傜偣鏁�{1} 澶勶紝鏈畬鎴愬悓姝ユ暟鎹妭鐐规暟 {2} 澶勩�", items : [{
 *           text : '286', params : { name : 'zhangsan', age : 12 } }, { text :
 *           '224', params : { name : 'lisi', age : 12 } }] }
 * @return
 *           @example
 */
NS.define('Output.Text', {
    constructor: function (cData) {
        this.config = cData;//閰嶇疆鍙傛暟
        this.buildDom(cData);
        this.addEvent('pop');//娣诲姞寮圭獥浜嬩欢
    },
    /***
     鍒涘缓缁勪欢鐨刣om鑺傜偣
     */
    buildDom: function (cData) {
        var me = this;
        // 鍒涘缓涓�釜缁勪欢鍐呯殑鐖惰妭鐐筪iv锛屼互渚挎寕鎺ュ埌澶栫晫鐨凞OM鑺傜偣涓婏紝瀹屾垚缁勪欢鐨勭浉浜掔粍瑁呫�
        var interfaceDIV = document.createElement('DIV');
        interfaceDIV.className = 'opTextRoot';
        var itemsContent = cData['items'];
        var htmlString = cData['htmlstring'];
        // 鑾峰彇瑕佹樉绀虹殑鏈�ぇ杩炴帴鏁般�
        var showMaxItems = this.showMaxItems = cData['showMaxItems'];
        // 姝ｅ垯琛ㄨ揪寮忥紝鍒嗗壊htmlString瀛楃涓层�
        var strs = htmlString.split(/\{[0-9]*\}/);
        // 寰幆鏁版嵁鍜屽垎鍓插悗寰楀埌鐨勫瓧绗︿覆鏁版嵁锛屾潵鍒涘缓div瀛愯妭鐐瑰厓绱犮�
        for (var index in strs) {
            if (index > showMaxItems) {
                break;
            }
            interfaceDIV.appendChild(document
                .createTextNode(strs[index]));
            // 濡傛灉鏈夎繛鎺ユ暟鎹紝鍒欐坊鍔犺繛鎺ュ厓绱犮�
            if (index < itemsContent.length) {
                var a = document.createElement('A');
                a.href = "http://www.baidu.com";
                a.appendChild(document
                    .createTextNode(itemsContent[index]['text']));
                interfaceDIV.appendChild(a);
            }
        }
        // 鍒涘缓涓�釜鈥滅偣鍑绘煡鐪嬭缁嗕俊鎭�閾炬帴锛�
        var xxxx = document.createElement('A');
        xxxx.href = "http://www.baidu.com";
        xxxx.onclick = function (me) {
            //alert(me.parentElement);
            me.fireEvent('pop');//瑙﹀彂寮圭獥浜嬩欢
        }
        xxxx.appendChild(document.createTextNode('     >> 鐐瑰嚮鏌ョ湅璇︾粏淇℃伅'));
        interfaceDIV.appendChild(xxxx);
        this.myDom = interfaceDIV;
        this.createComponent(cData);
    },
    /***
     * 鍒涘缓鐢ㄤ互鍙嶅洖getLibComponent 鏂规硶鍙嶅洖鐨勫璞�
     **/
    createComponent: function (cData) {
        var me = this;
        var basic = {
            baseCls: '',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.myDom);
                }
            }
        };
        US.apply(basic, {});
        this.component = new Ext.container.Container(basic);
    },
    /*******************************************************************
     * 鑾峰彇骞惰繑鍥炲綋鍓嶇粍浠秈d鐨勭姸鎬佸睘鎬с�
     */
    getStatus: function () {
    },
    /*******************************************************************
     * 鍒锋柊缁勪欢銆�
     */
    loadData: function (cData) {

    },
    /**
     *鑾峰彇缁勪欢绫诲瀷
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    /*******************************************************************
     * 鑾峰彇鐢ㄤ簬鍜屽簳灞傜被搴撲氦浜掔殑缁勪欢瀵硅薄銆�
     */
    getLibComponent: function () {
        return this.component;
    },
    /*******************************************************************
     * 灞曠ず鏈�ぇitems椤圭洰銆�
     */
    showMaxItems: function () {
        alert('灞曠ず鏈�ぇ鐨刬tems椤圭洰');
    },
    /*******************************************************************
     * 灏嗗垱寤虹殑鍘熺敓dom鑺傜偣娓叉煋鍒板埗瀹氱殑dom鑺傜偣涓娿�
     *
     * @param {}
     *            id 璇ュ弬鏁板彲浠ユ槸dom鑺傜偣鐨刬d锛屼篃鍙互鏄痙om瀵硅薄銆�
     */
    render: function (id) {
        this.component.render(id);
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
//				C.setWidth(width);
//				C.setHeight(height);
    }
});