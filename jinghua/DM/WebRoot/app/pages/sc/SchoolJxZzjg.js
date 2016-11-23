NS.define('Pages.sc.SchoolJxZzjg',{
    extend : 'Template.Page',
    modelConfig : {
        serviceConfig : {
            queryChart:'schoolBasicSituationService?queryChart'	//查询教学组织结构
        }
    },
    cssRequires : ['app/pages/sc/template/css/jxzzjg.css'],
    init : function(){
        this.initComponent();
    },
    initComponent : function(){
        var component = new NS.Component({
                autoScroll : true
            }),
            init = false;
        this.setPageComponent(component);
        component.component.on('resize',function(){
            if(init){
                this.vis.attr('width',component.getWidth());
                this.vis.attr('height',component.getHeight());
                return;
            }
            init = true;
            this.w = component.getWidth();
            this.h = component.getHeight();
            this.callSingle({key : 'queryChart',params : {method : 'queryJxzzjg'}},function(data){
                this.draw(data,this.getLibComponent().el.dom);
            });
        },this);
    },
    draw : function(json,dom){
        var m = [20, 120, 20, 120],
            w = 1280 - m[1] - m[3],
            h = 800 - m[0] - m[2],
            i = 0,
            root;

        var tree = d3.layout.tree()
            .size([h, w]);

        var diagonal = d3.svg.diagonal()
            .projection(function(d) { return [d.y, d.x]; });

        var vis = this.vis = d3.select(dom).append("svg:svg")
            .attr("width", w + m[1] + m[3])
            .attr("height", h + m[0] + m[2])
            .append("svg:g")
            .attr("transform", "translate(" + (m[3]+10) + "," + m[0] + ")");

            root = json;
            root.x0 = h / 2;
            root.y0 = 0;

            function toggleAll(d) {
                if (d.children) {
                    d.children.forEach(toggleAll);
                    toggle(d);
                }
            }

            // Initialize the display to show a few nodes.
          root.children.forEach(toggleAll);
            toggle(root.children[0]);
            if(root.children[0].children)
                toggle(root.children[0].children[2]);
            toggle(root.children[1]);
            if(root.children[1].children)
                toggle(root.children[1].children[1]);
//            toggle(root.children[6].children[0].children[1]);

            update(root);

        function update(source) {
            var duration = d3.event && d3.event.altKey ? 5000 : 500;

            // Compute the new tree layout.
            var nodes = tree.nodes(root).reverse();

            // Normalize for fixed-depth.
            nodes.forEach(function(d) { d.y = d.depth * 180; });

            // Update the nodes…
            var node = vis.selectAll("g.node")
                .data(nodes, function(d) { return d.id || (d.id = ++i); });

            // Enter any new nodes at the parent's previous position.
            var nodeEnter = node.enter().append("svg:g")
                .attr("class", "node")
                .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
                .on("click", function(d) { toggle(d); update(d); });

            nodeEnter.append("svg:circle")
                .attr("r", 1e-6)
                .style("fill", function(d) { return d.children&& d.children.length>0 ? "lightsteelblue" : "#fff"; });

            nodeEnter.append("svg:text")
                .attr("x", function(d) {
                    return isChildren(d) ? -10 : 10;
                })
                .attr("dy", ".35em")
                .attr("text-anchor", function(d) { return isChildren(d) ? "end" : "start"; })
                .text(function(d) { return d.text; })
                .attr('fill-opacity',1)
                .attr('font-size',getFontSize)
                .attr("font-family",'Microsoft YaHei')
                .attr("fill",function(d){
                    return getTextColor(d);
                })
                .style("fill-opacity", 1e-6);

            // Transition nodes to their new position.
            var nodeUpdate = node.transition()
                .duration(duration)
                .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

            nodeUpdate.select("circle")
                .attr("r", 4.5)
                .style("fill", function(d) { return d._children ? "#CCEBFF" : "#fff"; });

            nodeUpdate.select("text")
                .style("fill-opacity", 1);

            // Transition exiting nodes to the parent's new position.
            var nodeExit = node.exit().transition()
                .duration(duration)
                .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
                .remove();

            nodeExit.select("circle")
                .attr("r", 1e-6);

            nodeExit.select("text")
                .style("fill-opacity", 1e-6);

            // Update the links…
            var link = vis.selectAll("path.link")
                .data(tree.links(nodes), function(d) { return d.target.id; });

            // Enter any new links at the parent's previous position.
            link.enter().insert("svg:path", "g")
                .attr("class", "link")
                .attr("d", function(d) {
                    var o = {x: source.x0, y: source.y0};
                    return diagonal({source: o, target: o});
                })
                .transition()
                .duration(duration)
                .attr("d", diagonal);

            // Transition links to their new position.
            link.transition()
                .duration(duration)
                .attr("d", diagonal);

            // Transition exiting nodes to the parent's new position.
            link.exit().transition()
                .duration(duration)
                .attr("d", function(d) {
                    var o = {x: source.x, y: source.y};
                    return diagonal({source: o, target: o});
                })
                .remove();

            // Stash the old positions for transition.
            nodes.forEach(function(d) {
                d.x0 = d.x;
                d.y0 = d.y;
            });
        }

// Toggle children.
        function toggle(d) {
            if(d.children && d.children.length == 0)d.children = null;
            if (d.children) {
                d._children = d.children;
                d.children = null;
            } else {
                d.children = d._children;
                d._children = null;
            }
        }
        function isChildren(d){
            if(d.children)   return d.children.length>0;
            if(d._children)  return d._children.length>0;
            return false;
        }
        function getTextColor(d){
            var cclx = d.cclx;
            switch(cclx){
                case  'XX':return '#463CFD';
                case  'YX':return '#A93CFD';
                case  'ZY':return '#FF3CC8';
                case  'BJ':return '#FF7A3C';
                default: return '#FF7A3C';
            }
        }
        function getFontSize(d){
            var cclx = d.cclx;
            switch(cclx){
                case  'XX':return 14;
                case  'YX':return 12;
                case  'ZY':return 12;
                case  'BJ':return 12;
            }
        }
    }
});