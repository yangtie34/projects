NS.define('Pages.sc.SchoolBasicSituation',{
    extend : 'Template.Page',
    requires : ['Pages.sc.schoolbasicsituation.Config','Pages.sc.schoolbasicsituation.ChartBuilder'],
    cssRequires : ['app/pages/sc/template/css/schoolbasicPack.css'],
    modelConfig : {
        serviceConfig : {
            queryChart:'schoolBasicSituationService?queryChart'	//查询基本报表情况
        }
    },
    tplRequires : [{
        fieldname : 'windowtpl',path : 'app/pages/sc/schoolbasicsituation/tpl/window.html'
    }],
    w : 1000,
    h : 600,
    r : 600,
    init : function(config){
        var data = Pages.sc.schoolbasicsituation.Config.treeData(),init = false;
        this.initScale();
        this.preparedData(data);
        var component = new NS.Component({
            autoScroll : true
        });
        this.setPageComponent(component);
//        component.component.on('resize',function(){
            if(init){
                this.vis.attr('width',component.getWidth());
                this.vis.attr('height',component.getHeight());
                return;
            }
            init = true;
            this.w = component.getWidth();
            this.h = component.getHeight();
            this.draw(Pages.sc.schoolbasicsituation.Config.treeData());
//        },this);
        component.component.on('destroy',function(){
            this.closeAll();
        },this);
    },
    draw : function(root){
        var me = this,
            w = this.w,
            h = this.h,
            r = this.r,
            x = this.xScale,
            y = this.yScale;
        var color = d3.scale.category20();
        //初始化构图器
        var pack = d3.layout.pack()
            .size([r, r])
            .padding(20)
            .value(function(d) { return d.size; })
        //初始化画布
        var vis = this.vis = d3.select(this.getLibComponent().el.dom).insert("svg:svg", "h2")
            .attr("width", w)
            .attr("height", h)
            .append("svg:g")
            .attr("transform", "translate(" + (w - r) / 2 + "," + (h - r) / 2 + ")");
        //通过构图器生成数据
        var node = this.root = root;
        var nodes = pack.nodes(root);
        //生成创建阴影
        var filter = vis.append("defs")
            .append("filter")
            .attr("id", "drop-shadow")
            .attr("height", "130%")
            .append("feGaussianBlur")
            .attr("in", "SourceAlpha")
            .attr("stdDeviation", 5)
            .attr("result", "blur")
            .append("feOffset")
            .attr("in", "blur")
            .attr("dx", 5)
            .attr("dy", 5)
            .attr("result", "offsetBlur");

        var feMerge = filter.append("feMerge");

        feMerge.append("feMergeNode")
            .attr("in", "offsetBlur")
        feMerge.append("feMergeNode")
            .attr("in", "SourceGraphic");

        //生成圆
        vis.selectAll("circle")
            .data(nodes)
            .enter().append("svg:circle")
            //样式处理
            .style('fill',function(d){
                return d.color;
            })
            .style('fill-opacity',function(d){
                return 1;
            })
            .attr('fill-opacity',.1)
            .attr('class',function(d){
                if(d.children)return "parent";
            })
//            .style("filter", "url(#drop-shadow)")
            .attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; })
            .attr("r", function(d) {
                if(d == root)return d.r;
                else
                return me.showCircle(node,d)?d.r:0;
            })
//            attr('fill',function(d){return color(d3.random.normal(20))})
            .on("click", function(d) {
                if(me.isChildren(d)){
                    me.popChart(d,this);
                    return;
                }else{
                    return zoom(node == d ? root : d);
                }
            });
        //生成文本
        vis.selectAll("text")
            .data(nodes)
            .enter().append("svg:text")
            //.attr("class", function(d) { return d.children ? "parent" : "child"; })
            .attr("x", function(d) { return d.x; })
            .attr("y", function(d) { return d.y; })
            .attr("dy", ".35em")
            .attr("font-family",'Microsoft YaHei')
            .attr("font-size",20)
            .attr('fill-opacity',1)
            .attr("fill",function(d){
                if(d.children)return "#000000";
                else return "#E2E0F8";
            })
            .attr("text-anchor", "middle")
            .style("opacity", function(d) { return d.r > 20 ? 1 : 0; })
            .text(function(d) {
                return me.showText(node,d) ? d.name : '';
            })
            .on("click", function(d) {
                if(me.isChildren(d)){
                    me.popChart(d,this);
                    return;
                }else{
                    return zoom(node == d ? root : d);
                }
            });

        var zoom = this.zoom = function(d, i) {
            var k = r / d.r / 2;
            x.domain([d.x - d.r, d.x + d.r]);
            y.domain([d.y - d.r, d.y + d.r]);

            var t = vis.transition()
                .duration(d3.event.altKey ? 7500 : 750);

            t.selectAll("circle")
                .attr("cx", function(d) { return x(d.x); })
                .attr("cy", function(d) { return y(d.y); })
                .attr("r", function(n) {
                    return me.showCircle(d,n)?k * n.r:0;
                });

            t.selectAll("text")
                .attr("x", function(d) { return x(d.x); })
                .attr("y", function(d) { return y(d.y); }).
                text(function(n){
                    return me.showText(d,n) ? n.name : '';
                })
                .style("opacity", function(d) { return k * d.r > 20 ? 1 : 0; });

            node = d;
            d3.event.stopPropagation();
        }
    },
    /**
     * 初始化输出数据范围（针对坐标）
     */
    initScale : function(){
        var r = this.r;
        this.xScale = d3.scale.linear().range([0, r]);
        this.yScale = d3.scale.linear().range([0, r]);
    },
    /***********************数据处理逻辑**************************/
    showCircle : function(clicknode,node){
        var hash = this.hash;
        if(clicknode.id == node.id)return true;//当点击的节点和遍历的节点一致时，显示圆
        if(clicknode.id == node.pid)return true;//当遍历的节点的父节点是点击节点时候，显示圆
        if(node.children && node.children.length!=0)return true;
        return false;
    },
    showText : function(clicknode,node){
        var hash = this.hash,root = this.root;
        if(clicknode.id == node.id)return false;//当点击的节点和遍历的节点一致时，显示文字
        if(clicknode.id == node.pid)return true;//当遍历的节点的父节点是点击节点时候，显示文字
        if(root.id == node.pid)return true;
        return false;
    },
    isChildren : function(node){
        var children = node.children;
        if(!children){
            return true;
        }else return false;

    },
    /**
     * 准备数据
     * @param data
     */
    preparedData : function(data){
        this.chartsHash = {};//点击生成的图表的hash表
        this.leftCharts = [];//左边的图表集合
        this.rightCharts = [];//右边图表的集合
        var root = NS.clone(data);
        this.hash = this.toHash(root);//将节点数据生成Hash表
    },
    /**
     * 生成Hash表
     * @param root
     * @returns {{}}
     */
    toHash : function(root){
        var hash = {},
            i= 0,
            len,
            item,
            iterator,
            bakToIterator = [];
        iterator = root.children;
        while(iterator.length != 0){
            for(i =0,len = iterator.length;i++;){
                item = iterator[i];
                hash[item.id] = item;
                bakToIterator = bakToIterator.concat(item.children);
            }
            iterator = bakToIterator;
            bakToIterator = [];
        }
        return hash;
    },
    /******
     * 弹窗展示
     */
    popChart : function(node,svg){
        var xy = d3.mouse(svg),
            method = node.method,
            config = {
                title : node.name,
                isCommon : node.isCommon,
                type : node.type,
                x : xy[0] + 280,
                y : xy[1]-40,
                tpl : this.windowtpl,
                yAxis : node.yAxis,
                width : node.width,
                height : node.height
            };
        (function(method,config,node){
            if(this.chartsHash[node.id]){return;}else{this.chartsHash[node.id] = true;}
            this.callSingle({key : 'queryChart',params : {method : method}},function(data){
                config.data = data;
                if(node.data){config.data = node.data;}
                var chartB = new Pages.sc.schoolbasicsituation.ChartBuilder(config);
                chartB.on('close',function(){
                    this.leftCharts = NS.Array.remove(this.leftCharts,this.chartsHash[node.id]);
                    this.rightCharts = NS.Array.remove(this.rightCharts,this.chartsHash[node.id]);
                    delete this.chartsHash[node.id];//移除Chart
                    this.refresh(true);
                },this);
                chartB.on('toleft',function(chart,window){
                    if(NS.Array.contains(this.leftCharts,chart)){
                        this.toLeft(true);
                        return;
                    }
                    this.leftCharts.push(chart);
                    this.rightCharts = NS.Array.remove(this.rightCharts,chart);
                    this.refresh(true);
                },this);
                chartB.on('toright',function(chart,window){
                    if(NS.Array.contains(this.rightCharts,chart)){
                        this.toRight(true);
                        return;
                    }
                    this.rightCharts.push(chart);
                    this.leftCharts = NS.Array.remove(this.leftCharts,chart);
                    this.refresh(true);
                },this);
                chartB.on('rightleftto',function(chart,window){
                    this.leftRight(true);
                },this);
                chartB.on('relayout',function(chart,window){
                    this.refresh(false);
                },this);
                this.chartsHash[node.id] = chartB;
            });
        }).call(this,method,config,node);
    },
    toLeft : function(flag){
        var by = NS.getBody().getHeight(),
            x = 0,
            y = 0,
            array = this.leftCharts;
        for(var i=0;i<array.length;i++){
            var window = array[i].window;
            if(y+window.getHeight()>by)window.hide();
            else window.showAt(x,y,flag);
            y+=window.getHeight();

        }
    },
    refresh : function(flag){
        this.toLeft(flag);
        this.toRight(flag);
    },
    toRight : function(flag){
        var by = NS.getBody().getHeight(),
            bw = NS.getBody().getWidth(),
            y = 0,
            array = this.rightCharts;
        for(var i=0;i<array.length;i++){
            var window = array[i].window;
            if(y+window.getHeight()>by)window.hide();
            else window.showAt(bw-window.getWidth(),y,flag);
            y+=window.getHeight();
        }
    },
    leftRight : function(flag){
        var la = this.leftCharts,
            ra = this.rightCharts;
        this.leftCharts = ra;
        this.rightCharts = la;
        this.refresh(flag);
    },
    closeAll : function(){
        var hash = this.chartsHash;
        for(var i in hash){
            hash[i].window.close();
        }
    }
});